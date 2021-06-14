package net.smallacademy.fragmentexample;

public class BookAdminInDataModel {
    String Person,Seats,Where, User_name;

    public BookAdminInDataModel() {
    }

    public BookAdminInDataModel(String person, String seats, String where, String name_user) {
        Person = person;
        Seats = seats;
        Where = where;
        User_name = name_user;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getPerson() {
        return Person;
    }

    public void setPerson(String person) {
        Person = person;
    }

    public String getSeats() {
        return Seats;
    }

    public void setSeats(String seats) {
        Seats = seats;
    }

    public String getWhere() {
        return Where;
    }

    public void setWhere(String where) {
        Where = where;
    }
}
