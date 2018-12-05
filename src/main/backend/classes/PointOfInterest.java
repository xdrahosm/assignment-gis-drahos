package main.backend.classes;

public class PointOfInterest {

    private String name;
    private float distance;
    private String geoData;
    private String type;

    public PointOfInterest(String name, float distance, String geoData, String type) {
        this.name = name;
        this.distance = distance;
        this.geoData = geoData;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getGeoData() {
        return geoData;
    }

    public void setGeoData(String geoData) {
        this.geoData = geoData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
