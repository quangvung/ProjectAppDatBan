package net.smallacademy.fragmentexample;

public class model
{
    String name,address,purl, title, purl1 ;
    model()
    {
    }

    public model(String name, String address, String purl, String title, String purl1) {
        this.name = name;
        this.address = address;
        this.purl = purl;
        this.title = title;
        this.purl1 = purl1;
    }

    public String getPurl1() {
        return purl1;
    }

    public void setPurl1(String purl1) {
        this.purl1 = purl1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
