package net.smallacademy.fragmentexample;

public class BookUserDataModel {
    String Person,Seats,Where,Name_res;

    public BookUserDataModel() {
    }

    public BookUserDataModel(String person, String seats, String where, String name_res) {
        Person = person;
        Seats = seats;
        Where = where;
        Name_res = name_res;
    }

    public String getName_res() {
        return Name_res;
    }

    public void setName_res(String name_res) {
        Name_res = name_res;
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
