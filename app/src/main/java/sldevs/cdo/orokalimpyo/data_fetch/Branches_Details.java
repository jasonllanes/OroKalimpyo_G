package sldevs.cdo.orokalimpyo.data_fetch;

public class Branches_Details {
    public String name;
    public String longitude;

    public String latitude;
    public String description;

    public Branches_Details() {
    }


    public Branches_Details(String name, String longitude, String latitude, String description) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
