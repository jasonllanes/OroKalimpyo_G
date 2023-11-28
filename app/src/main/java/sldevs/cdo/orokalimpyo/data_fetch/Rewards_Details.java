package sldevs.cdo.orokalimpyo.data_fetch;

public class Rewards_Details {

    public String rewardTitle;
    public long points;
    public String description;

    public String imageName;
    public String imageUrl;
    public String rewardCode;

    public Rewards_Details(){

    }

    public Rewards_Details(String rewardTitle, long points, String description, String imageName, String rewardCode, String imageUrl) {
        this.rewardTitle = rewardTitle;
        this.points = points;
        this.description = description;
        this.imageName = imageName;
        this.rewardCode = rewardCode;
        this.imageUrl = imageUrl;
    }

    public String getRewardTitle() {
        return rewardTitle;
    }

    public void setRewardTitle(String rewardTitle) {
        this.rewardTitle = rewardTitle;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getRewardCode() {
        return rewardCode;
    }

    public void setRewardCode(String rewardCode) {
        this.rewardCode = rewardCode;
    }

    public String getImage_url() {
        return imageUrl;
    }

    public void setImage_url(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
