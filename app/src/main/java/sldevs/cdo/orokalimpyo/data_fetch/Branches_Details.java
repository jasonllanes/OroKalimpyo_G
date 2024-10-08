package sldevs.cdo.orokalimpyo.data_fetch;

public class Branches_Details {

    public String id;
    public String locationName;
    public double longitude;

    public double latitude;



    public String url;

    public Branches_Details() {
    }


    public Branches_Details(String id,String locationName, double longitude, double latitude, String description, String url) {
        this.id = id;
        this.locationName = locationName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.locationName = description;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String name) {
        this.locationName = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
