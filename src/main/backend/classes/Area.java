package main.backend.classes;

public class Area {

    private String name;
    private String geoData;
    private int crimeCount;

    public Area(String name, String geoData, int crimeCount) {
        this.name = name;
        this.geoData = geoData;
        this.crimeCount = crimeCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeoData() {
        return geoData;
    }

    public void setGeoData(String geoData) {
        this.geoData = geoData;
    }

    public int getCrimeCount() {
        return crimeCount;
    }

    public void setCrimeCount(int crimeCount) {
        this.crimeCount = crimeCount;
    }
}
