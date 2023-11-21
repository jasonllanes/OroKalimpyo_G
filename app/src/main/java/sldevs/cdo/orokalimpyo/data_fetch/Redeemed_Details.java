package sldevs.cdo.orokalimpyo.data_fetch;

import com.google.firebase.Timestamp;

public class Redeemed_Details  {

    public String reward_title;
    public String rewardCode;
    public Timestamp redeemed_date;


    public Redeemed_Details(){

    }

    public Redeemed_Details(String reward_title, String rewardCode, Timestamp redeemed_date) {
        this.reward_title = reward_title;
        this.rewardCode = rewardCode;
        this.redeemed_date = redeemed_date;
    }

    public String getRewardTitle() {
        return reward_title;
    }

    public void setRewardTitle(String rewardTitle) {
        this.reward_title = reward_title;
    }

    public String getRewardCode() {
        return rewardCode;
    }

    public void setRewardCode(String rewardCode) {
        this.rewardCode = rewardCode;
    }

    public Timestamp getDate() {
        return redeemed_date;
    }

    public void setDate(Timestamp redeemed_date) {
        this.redeemed_date = redeemed_date;
    }
}
