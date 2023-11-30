package sldevs.cdo.orokalimpyo.data_fetch;

public class Scanned_Contributions {

    public String contribution_id;
    public String status;

    public String user_id;
    public String name;
    public String barangay;
    public String waste_type;
    public String kilo;

    public String user_type;

    public String date;

    public String time;

    public Scanned_Contributions(){

    }

    public Scanned_Contributions(String contribution_id, String status, String user_id, String name, String barangay, String waste_type, String kilo, String user_type, String date, String time) {
        this.contribution_id = contribution_id;
        this.status = status;
        this.user_id = user_id;
        this.name = name;
        this.barangay = barangay;
        this.waste_type = waste_type;
        this.kilo = kilo;
        this.user_type = user_type;
        this.date = date;
        this.time = time;
    }

    public String getContribution_id() {
        return contribution_id;
    }

    public void setContribution_id(String contribution_id) {
        this.contribution_id = contribution_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getWaste_type() {
        return waste_type;
    }

    public void setWaste_type(String waste_type) {
        this.waste_type = waste_type;
    }

    public String getKilo() {
        return kilo;
    }

    public void setKilo(String kilo) {
        this.kilo = kilo;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
