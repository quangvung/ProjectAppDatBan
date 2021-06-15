package net.smallacademy.fragmentexample;

public class Restaurant_model
{
    String name,address,purl ;
    private Float averageRating;
    Restaurant_model()
    {
    }

    public Restaurant_model(String name, String address, String purl, Float averageRating) {
        this.name = name;
        this.address = address;
        this.purl = purl;
        this.averageRating = averageRating;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
