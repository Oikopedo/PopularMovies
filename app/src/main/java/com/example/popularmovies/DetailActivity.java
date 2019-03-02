package com.example.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.Movie.Movie;
import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    public final static String EXTRA="Movie";
    public final static String IMAGE_PATH="http://image.tmdb.org/t/p/w500/";

    //private Movie mMovie;
    //private TextView mMovieName;
    //private ImageView mMovieImage;
    //private TextView mMovieSynopsis;
    //private TextView mMovieRating;
    //private TextView mMovieReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView mMovieName=(TextView) findViewById(R.id.tv_movie_name);
        ImageView mMovieImage=(ImageView) findViewById(R.id.tv_movie_image);
        TextView mMovieSynopsis=(TextView) findViewById(R.id.tv_movie_synopsis);
        TextView mMovieRating=(TextView) findViewById(R.id.tv_movie_rating);
        TextView mMovieReleaseDate=(TextView) findViewById(R.id.tv_movie_release_date);

        Intent intent=getIntent();
        if(intent!=null){
            if(intent.hasExtra(EXTRA)){
                Movie mMovie=(Movie)intent.getParcelableExtra(EXTRA);

                mMovieName.setText(mMovie.getName());
                Uri uri=Uri.parse(IMAGE_PATH+mMovie.getImageSource())
                        .buildUpon().build();
                Picasso.get().load(uri).into(mMovieImage);

                mMovieSynopsis.setText(mMovie.getSynopsis());
                mMovieRating.setText(Float.toString(mMovie.getRating()));
                mMovieReleaseDate.setText(mMovie.getReleaseDate());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId=item.getItemId();
        if (itemId==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
