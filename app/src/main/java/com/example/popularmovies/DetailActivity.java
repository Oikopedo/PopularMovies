package com.example.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.Movie.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private Movie mMovie;
    private TextView mMovieName;
    private ImageView mMovieImage;
    private TextView mMovieSynopsis;
    private TextView mMovieRating;
    private TextView mMovieReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieName=(TextView) findViewById(R.id.tv_movie_name);
        mMovieImage=(ImageView) findViewById(R.id.tv_movie_image);
        mMovieSynopsis=(TextView) findViewById(R.id.tv_movie_synopsis);
        mMovieRating=(TextView) findViewById(R.id.tv_movie_rating);
        mMovieReleaseDate=(TextView) findViewById(R.id.tv_movie_release_date);

        Intent intent=getIntent();
        if(intent!=null){
            if(intent.hasExtra("Movie")){
                mMovie=(Movie)intent.getSerializableExtra("Movie");

                mMovieName.setText(mMovie.getName());

                Uri uri=Uri.parse("http://image.tmdb.org/t/p/w500/"+mMovie.getImageSource())
                        .buildUpon().build();
                Picasso.get().load(uri).into(mMovieImage);

                mMovieSynopsis.setText(mMovie.getSynopsis());
                mMovieRating.setText(Float.toString(mMovie.getRating()));
                mMovieReleaseDate.setText(mMovie.getReleaseDate());
            }
        }

    }
}
