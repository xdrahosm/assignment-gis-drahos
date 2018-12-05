package main.backend.classes;

import org.json.JSONObject;

import javax.persistence.ColumnResult;
import javax.persistence.Entity;

//@Entity
public class Hotel {

    private String name;
    private String geoData;

    public Hotel(String name, String geoData){
        this.setName(name);
        this.setGeoData(geoData);
    }

    public String getGeoData() {
        return geoData;
    }

    public void setGeoData(String geoData) {
        this.geoData = geoData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
