package com.example.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.net.ConnectivityManager;
import android.content.Context;
import android.net.NetworkInfo;

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

    private static String choice=BASE_URL+POPULAR_PATH+QUERY;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sort_popular) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            choice=BASE_URL+POPULAR_PATH+QUERY;
            loadMoviesData(choice);
            return true;
        }
        if (id==R.id.sort_top_rated){
            mRecyclerView.setVisibility(View.INVISIBLE);
            choice=BASE_URL+TOP_RATED_PATH+QUERY;
            loadMoviesData(choice);
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
        //if (mRecyclerView==null){
        //    Log.v("Log","recycler null");
        //}
        //if (layoutManager==null){
        //    Log.v("Log","layoutmanager null");
        //}
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        loadMoviesData(choice);

    }

    @Override
    public void onClick(Movie movie) {
        Intent intent=new Intent(this,DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA,movie);
        startActivity(intent);
    }

    private class FetchMovies extends AsyncTask<String,Void, Movie[]>{
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
                mMovieAdapter.setmMovieData(movies);
            } else {
                showErrorMessage();
            }
        }

    }

    private void loadMoviesData(String url) {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            new FetchMovies().execute(url);
        }else{
            new FetchMovies().execute((String)null);
        }
    }

    private void showMoviesDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

}

