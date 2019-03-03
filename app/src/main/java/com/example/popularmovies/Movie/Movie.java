package com.example.popularmovies.Movie;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {
    private final int id;
      private final String name;
      private final String imageSource;
      private final String synopsis;
      private final float rating;
      private final String releaseDate;

    public Movie(int id,String name,String imageSource,String synopsis,float rating,String releaseDate ) {
        this.id=id;
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

    public int getId(){return id;}

    private Movie(Parcel in) {
        this.id=in.readInt();
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
        dest.writeInt(id);
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

