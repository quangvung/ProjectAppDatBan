package net.smallacademy.fragmentexample;

public class BookAdminDataModel {

    String Name_res,Booking_Count,postkey;

    public BookAdminDataModel(String name_res, String booking_Count, String postkey) {
        Name_res = name_res;
        Booking_Count = booking_Count;
        this.postkey = postkey;
    }

    public String getPostkey() {
        return postkey;
    }

    public void setPostkey(String postkey) {
        this.postkey = postkey;
    }

    public String getName_res() {
        return Name_res;
    }

    public void setName_res(String name_res) {
        Name_res = name_res;
    }

    public String getBooking_Count() {
        return Booking_Count;
    }

    public void setBooking_Count(String booking_Count) {
        Booking_Count = booking_Count;
    }
}
