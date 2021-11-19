package com.kanucreator.azagomovies;

public class AllMoviesModel {

    String image,imdbid,name,movielink;

    public AllMoviesModel(String image, String imdbid, String name, String movielink) {
        this.image = image;
        this.imdbid = imdbid;
        this.name = name;
        this.movielink = movielink;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovielink() {
        return movielink;
    }

    public void setMovielink(String movielink) {
        this.movielink = movielink;
    }
}
