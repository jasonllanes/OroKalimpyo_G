package sldevs.cdo.orokalimpyo.data_fetch;

public class UserDetails {

    public String id,user_type,name,barangay,address,number,email,password,date_created,points;

    public UserDetails() { }

    public UserDetails(String id, String user_type, String name, String barangay,
                       String address, String number, String email, String password, String date_created, String points) {
        this.id = id;
        this.user_type = user_type;
        this.name = name;
        this.barangay = barangay;
        this.address = address;
        this.number = number;
        this.email = email;
        this.password = password;
        this.date_created = date_created;
        this.points = points;
    }


}
