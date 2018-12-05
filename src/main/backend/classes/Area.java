package main.backend.classes;

public class Area {

    private String name;
    private String geoData;
    private long crimeCount;

    public Area(String name, String geoData, long crimeCount) {
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

    public long getCrimeCount() {
        return crimeCount;
    }

    public void setCrimeCount(Integer crimeCount) {
        this.crimeCount = crimeCount;
    }
}
