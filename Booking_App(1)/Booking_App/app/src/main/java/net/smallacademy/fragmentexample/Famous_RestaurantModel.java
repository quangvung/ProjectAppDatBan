package net.smallacademy.fragmentexample;

public class Famous_RestaurantModel
{
    String purl;
    Famous_RestaurantModel()
    {
    }

    public Famous_RestaurantModel(String purl) {
        this.purl = purl;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
