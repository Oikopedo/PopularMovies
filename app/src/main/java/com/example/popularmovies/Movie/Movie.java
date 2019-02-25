package com.example.popularmovies.Movie;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {
      private String name;
      private String imageSource;
      private String synopsis;
      private float rating;
      private String releaseDate;

    public Movie(String name,String imageSource,String synopsis,float rating,String releaseDate ) {
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

    private Movie(Parcel in) {
        this.name = in.readString();
        this.imageSource = in.readString();
        this.synopsis = in.readString();
        this.rating=in.readFloat();
        this.releaseDate=in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageSource);
        dest.writeString(synopsis);
        dest.writeFloat(rating);
        dest.writeString(releaseDate);
    }

    static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

