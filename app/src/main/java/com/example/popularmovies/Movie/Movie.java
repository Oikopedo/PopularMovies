package com.example.popularmovies.Movie;

import android.widget.ImageView;

public class Movie {
    private String name;
    private String imageSource;
    private String synopsis;
    private float rating;
    private String releaseDate;

    public Movie(String name,String imageSource,String synopsis,float rating,String releaseDate ){
        this.name=name;
        this.imageSource=imageSource;
        this.synopsis=synopsis;
        this.rating=rating;
        this.releaseDate=releaseDate;
    }

    public String toString(){
        return "name: "+name+" imageSource: "+imageSource+" synopsis: "+synopsis+" rating: "
                +rating+" releaseDate: "+releaseDate;
    }


    public String getName(){
        return name;
    }

    public String getImageSource(){
        return imageSource;
    }

    public String getSynopsis(){
        return synopsis;
    }

    public float getRating(){
        return rating;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

}

