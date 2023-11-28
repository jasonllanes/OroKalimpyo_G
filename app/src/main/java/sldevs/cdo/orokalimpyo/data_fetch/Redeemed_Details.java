package sldevs.cdo.orokalimpyo.data_fetch;

import com.google.firebase.Timestamp;

public class Redeemed_Details  {
    public String redeem_id;
    public String user_id;
    public String reward_title;
    public String reward_code;
    public Timestamp redeemed_date;
    public String imageName;



    public Redeemed_Details(){

    }

    public Redeemed_Details(String redeem_id, String user_id, String reward_title, String reward_code, Timestamp redeemed_date, String imageName) {
        this.redeem_id = redeem_id;
        this.user_id = user_id;
        this.reward_title = reward_title;
        this.reward_code = reward_code;
        this.redeemed_date = redeemed_date;
        this.imageName = imageName;
    }

    public String getRedeem_id() {
        return redeem_id;
    }

    public void setRedeem_id(String redeem_id) {
        this.redeem_id = redeem_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReward_title() {
        return reward_title;
    }

    public void setReward_title(String reward_title) {
        this.reward_title = reward_title;
    }

    public String getReward_code() {
        return reward_code;
    }

    public void setReward_code(String reward_code) {
        this.reward_code = reward_code;
    }

    public Timestamp getRedeemed_date() {
        return redeemed_date;
    }

    public void setRedeemed_date(Timestamp redeemed_date) {
        this.redeemed_date = redeemed_date;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
