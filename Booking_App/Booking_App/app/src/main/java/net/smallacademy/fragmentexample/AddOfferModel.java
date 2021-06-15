package net.smallacademy.fragmentexample;


import android.graphics.Bitmap;

public class AddOfferModel {
    private String add_recipe_text;
    private Bitmap add_recipe_img;

    public AddOfferModel(String add_recipe_text, Bitmap add_recipe_img) {
        this.add_recipe_text = add_recipe_text;
        this.add_recipe_img = add_recipe_img;
    }

    public Bitmap getAdd_recipe_img() {
        return add_recipe_img;
    }

    public void setAdd_recipe_img(Bitmap add_recipe_img) {
        this.add_recipe_img = add_recipe_img;
    }

    public String getAdd_recipe_text() {
        return add_recipe_text;
    }

    public void setAdd_recipe_text(String add_recipe_text) {
        this.add_recipe_text = add_recipe_text;
    }

}
