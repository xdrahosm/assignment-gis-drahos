package main.backend.classes;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import static java.lang.Math.toIntExact;


@RestController
public class ElementController {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<PointOfInterest> getPointsOfInterest(double longitude, double latitude){


        return jdbcTemplate.query(
                "select ST_AsGeojson(ST_Transform(way,4326)::geometry) as geo ,name, historic,ST_Distance_Sphere(ST_Transform(way,4326)::geometry,ST_MakePoint(?, ?,4326)::geometry)\n" +
                        "        as distance from planet_osm_point  where historic is not null and name is not null\n" +
                        "      \torder by distance limit 10",
                (rs, rowNum) -> {
                    return new PointOfInterest(rs.getString("name"), rs.getFloat("distance"),rs.getString("geo"),rs.getString("historic"));
                },longitude,latitude);
    }


    public List<Hotel> getHotels() {

        return jdbcTemplate.query(
                "select name , ST_AsGeoJSON(ST_Transform(way,4326)::geometry) as geo from planet_osm_point " +
                        "where tourism='hotel' and name is not null",
                (rs, rowNum) -> new Hotel(rs.getString("name"), rs.getString("geo")));
    }

    public List<Area> getAreas(){
        return jdbcTemplate.query(
                "select name , ST_AsGeoJSON(ST_Transform(geom,4326)::geometry) as geo, crime_count from planet_osm_polygon " +
                        "where boundary='administrative' and name is not null and name!='London' and name!='Greater London'",
                (rs, rowNum) -> new Area(rs.getString("name"), rs.getString("geo"), rs.getInt("crime_count")));
    }

    public List<CrimeTypeCount> getCrimeTypeCount(String area){
        return jdbcTemplate.query(
                "select c.crime_type,count(c.id) from crimes c " +
                        "join planet_osm_polygon p on p.osm_id=c.area_id " +
                        "where p.boundary='administrative' and p.name is not null and p.name!='London' and p.name!='Greater London' and p.name=? " +
                        "group by c.crime_type",
                (rs, rowNum) -> new CrimeTypeCount(rs.getString("crime_type"), rs.getLong("count")),area);
    }


    @CrossOrigin("*")
    @RequestMapping(value="/hotel",method= RequestMethod.GET)
    public String hotelApi(){

        List<Hotel> hotels=getHotels();

        //parse List to geoJSON:
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\":\"FeatureCollection\",");
        sb.append("\"features\": [");

        for(Hotel hotel : hotels){
            sb.append(" {\"type\": \"Feature\",\"geometry\":");
            sb.append(hotel.getGeoData());
            sb.append(",\"properties\": {\"title\":\"");
            sb.append(hotel.getName());
            sb.append("\",\"icon\":\"lodging-15\",\"type\":\"hotel");
            sb.append("\"}},");
        }

        sb.deleteCharAt(sb.length()-1);
        sb.append("]}");

        return sb.toString();

    }

    @CrossOrigin("*")
    @RequestMapping(value="/point",method= RequestMethod.GET)
    public String pointOfInterestApi(@RequestParam("long") double longitude, @RequestParam("lat") double latitude, @RequestParam("name") String name){
        //public String pointOfInterestApi(){

         List<PointOfInterest> points=getPointsOfInterest(longitude,latitude);

        //parse List to geoJSON:
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\":\"FeatureCollection\",");
        sb.append("\"features\": [");

        sb.append(" {\"type\": \"Feature\",\"geometry\":{\"type\": \"Point\","+
                "\"coordinates\": ["+ Double.toString(longitude)+", "+Double.toString(latitude)+"]},"+
                "\"properties\": {\"icon\":\"lodging-15\",\"type\":\"hotel\",\"title\":\""+name+"\"}},");


        for(PointOfInterest point : points){
            sb.append(" {\"type\": \"Feature\",\"geometry\":");
            sb.append(point.getGeoData());
            sb.append(",\"properties\": {\"title\":\"");
            sb.append(point.getName());
            sb.append("\",\"historic\":\"");
            sb.append(point.getType());
            sb.append("\",\"icon\":\"monument-15\",\"type\":\"point");
            sb.append("\",\"distance\":\"");
            sb.append(point.getDistance());
            sb.append("\"}},");
        }

        sb.deleteCharAt(sb.length()-1);
        sb.append("]}");

        return sb.toString();

    }

    @CrossOrigin("*")
    @RequestMapping(value="/area",method= RequestMethod.GET)
    public String areaApi(){

        List<Area> areas=getAreas();

        //parse List to geoJSON:
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\":\"FeatureCollection\",");
        sb.append("\"features\": [");

        areas.sort(new Comparator<Area>() {
            @Override
            public int compare(Area o1, Area o2) {
                return o2.getCrimeCount()-o1.getCrimeCount();
            }
        });

        int i=0,j=1;
        for(Area area : areas){
            System.out.println(Integer.toString(area.getCrimeCount())+" "+Integer.toString(j));
            sb.append(" {\"type\": \"Feature\",\"geometry\":");
            sb.append(area.getGeoData());
            sb.append(",\"properties\": {\"title\":\"");
            sb.append(area.getName());
            sb.append("\",\"color\":\""+ Integer.toString(j));
            sb.append("\",\"crimeCount\":\""+Integer.toString(area.getCrimeCount()));
            sb.append("\"}},");
            i++;
            if(i>=areas.size()/5) {j++;i=0;}

        }

        sb.deleteCharAt(sb.length()-1);
        sb.append("]}");
        return sb.toString();

    }

    @CrossOrigin("*")
    @RequestMapping(value="/area_detail",method= RequestMethod.GET)
    public String areaDetailApi(@RequestParam("name") String name){
        StringBuilder sb = new StringBuilder();
        List<CrimeTypeCount> crimes=getCrimeTypeCount(name);
        sb.append("{");
        for(CrimeTypeCount obj: crimes){
            sb.append("\""+obj.getType()+"\":\""+Long.toString(obj.getCount())+"\",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        return sb.toString();


    }


}
