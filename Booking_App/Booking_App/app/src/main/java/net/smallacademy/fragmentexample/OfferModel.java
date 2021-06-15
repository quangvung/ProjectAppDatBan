package net.smallacademy.fragmentexample;

public class OfferModel {

    String offer_title;
    String offer_img;

    public OfferModel(String offer_title, String offer_img) {
        this.offer_title = offer_title;
        this.offer_img = offer_img;
    }

    public String getOffer_title() {
        return offer_title;
    }

    public void setOffer_title(String offer_title) {
        this.offer_title = offer_title;
    }

    public String getOffer_img() {
        return offer_img;
    }

    public void setOffer_img(String offer_img) {
        this.offer_img = offer_img;
    }
}
