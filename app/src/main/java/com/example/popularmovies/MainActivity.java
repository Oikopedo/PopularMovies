package com.example.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.Movie.Movie;
import com.example.popularmovies.NetworkUtils.NetworkUtils;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private static final String BASE_URL="http://api.themoviedb.org/3/movie";
    private static final String POPULAR_PATH="/popular";
    private static final String TOP_RATED_PATH="/top_rated";
    private static final String QUERY="?api_key=1be795252d059d8804d4c2ff0386b872";

    private RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // TODO (7) Override onOptionsItemSelected to handle clicks on the refresh button
        if (id == R.id.sort_popular) {
            //mRecyclerView.setAdapter(new MovieAdapter(this));
            loadMoviesData(BASE_URL+POPULAR_PATH+QUERY);
            return true;
        }
        if (id==R.id.sort_top_rated){
            //mRecyclerView.setAdapter(new MovieAdapter(this));
            loadMoviesData(BASE_URL+TOP_RATED_PATH+QUERY);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        GridLayoutManager layoutManager =new GridLayoutManager(this,2);
        if (mRecyclerView==null){
            Log.v("Log","recycler null");
        }
        if (layoutManager==null){
            Log.v("Log","layoutmanager null");
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        loadMoviesData(BASE_URL+POPULAR_PATH+QUERY);

    }

    @Override
    public void onClick(Movie movie) {
        Intent intent=new Intent(this,DetailActivity.class);
        intent.putExtra("Movie",movie);
        startActivity(intent);
    }

    public class FetchMovies extends AsyncTask<String,Void, Movie[]>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
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
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMoviesDataView();
                // COMPLETED (45) Instead of iterating through every string, use mForecastAdapter.setWeatherData and pass in the weather data
                mMovieAdapter.setmMoviedData(movies);
            } else {
                showErrorMessage();
            }
        }

    }

    private void loadMoviesData(String url) {
        showMoviesDataView();

        new FetchMovies().execute(url);
    }

    private void showMoviesDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

}

