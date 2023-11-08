package sldevs.cdo.orokalimpyo.data_fetch;

import com.google.firebase.Timestamp;

public class News_Details {
    public String title;
    public String date;
    public String description;
    public Timestamp timeStamp;
    public String imageName;

    public News_Details(){

    }


    public News_Details(String title, String date, String description, Timestamp timeStamp, String imageName) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.timeStamp = timeStamp;
        this.imageName = imageName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
