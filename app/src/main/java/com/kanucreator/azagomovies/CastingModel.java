package com.kanucreator.azagomovies;

public class CastingModel {

    String imageurl,name;

    public CastingModel(String imageurl, String name) {
        this.imageurl = imageurl;
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}