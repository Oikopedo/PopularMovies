package com.example.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.popularmovies.Movie.Movie;
import com.example.popularmovies.NetworkUtils.NetworkUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL="http://api.themoviedb.org/3/movie";
    private static final String POPULAR_PATH="/popular";
    private static final String TOP_RATED_PATH="/top_rated";
    private static final String QUERY="?api_key=1be795252d059d8804d4c2ff0386b872";
    private TextView mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMovies=(TextView) findViewById(R.id.tv_movies_display);
        new FetchMovies().execute(BASE_URL+POPULAR_PATH+QUERY);
    }

    public class FetchMovies extends AsyncTask<String,Void, Movie[]>{
        @Override
        protected Movie[] doInBackground(String... strings) {
            if (strings.length==0){
                return null;
            }
            try{
                return NetworkUtils.getMovies(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            if (movies!=null){
                mMovies.setText("ola kala");
            }
        }

    }

}

