package com.kanucreator.azagomovies;

import android.graphics.drawable.Drawable;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HotPicksModel {

    String image;
    String nameofmovie,description;
    float ratingnum;
    String imdbid,movielink;

    public HotPicksModel(String image, String nameofmovie, String description, float ratingnum, String imdbid, String movielink) {
        this.image = image;
        this.nameofmovie = nameofmovie;
        this.description = description;
        this.ratingnum = ratingnum;
        this.imdbid = imdbid;
        this.movielink = movielink;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameofmovie() {
        return nameofmovie;
    }

    public void setNameofmovie(String nameofmovie) {
        this.nameofmovie = nameofmovie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRatingnum() {
        return ratingnum;
    }

    public void setRatingnum(float ratingnum) {
        this.ratingnum = ratingnum;
    }

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

    public String getMovielink() {
        return movielink;
    }

    public void setMovielink(String movielink) {
        this.movielink = movielink;
    }
}
