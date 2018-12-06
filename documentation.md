
# Overview
Application is showing hotels in London. Wneh user clicks on some of them, then 10 nearest interisting points are shown. There is also option to areas of London, which are distinguished by color based on crimes commited in them. 

The application has 2 separate parts, the client which is a web application created using mapbox API and mapbox.js and the backend Java application, backed by PostGIS. The frontend application communicates with backend using Rest.

# Frontend

The frontend is static HTML page, which shows data using Mapbox JS. All functionality is implemented in script.js.

# Backend

The backend application is implemented in Java with the use of Spring Boot framework. It is responsible for querying database and formating geojson files.

## Data

Main portion of data is from Open Street Maps. I downloaded the section which covered the whole area of London. For import to database, I used `osm2pgsql` tool. Other part of data is from https://data.police.uk/data/ where I downloaded whole dataset from Metropolitan service area, but ended up with using only 3 latest months. There was also added column with id of area in which the crime was commited, to table with crimes. The main reason was to speedup query exexution, because ST_Contain function was taking too long.
**query for getting crime count using ST_Contains**
`select areas.osm_id, count(crimes.id) from (select p.osm_id , p.geom from planet_osm_polygon p
                        where p.boundary='administrative' and p.name is not null and p.name!='London' and p.name!='Greater London') as areas, (select * from crimes) as crimes
					where st_contains(areas.geom,crimes.way)
	  				group by areas.osm_id`

 GeoJSON data are generate directly with ST_AsGeoJSON function, but postprocessing is needed.

## Api

**Find all hotels in London**

`GET /hotel`

**Find nearest points of interest**

`GET /point?long=0.15"&lat=51.5&name=Hotel`

**Get area with crime count**
`GET /area`
