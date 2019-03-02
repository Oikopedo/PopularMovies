package com.example.popularmovies;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
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

import com.example.popularmovies.Movie.Movie;
import com.example.popularmovies.NetworkUtils.NetworkUtils;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler
        , LoaderManager.LoaderCallbacks<Movie[]> {
    private static final String BASE_URL="http://api.themoviedb.org/3/movie";
    private static final String POPULAR_PATH="/popular";
    private static final String TOP_RATED_PATH="/top_rated";
    private static final String QUERY="?api_key=1be795252d059d8804d4c2ff0386b872";

    private RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private static String choice=BASE_URL+POPULAR_PATH+QUERY;

    private static final int MOVIE_LOADER_ID = 0;


    @NonNull
    @Override
    public Loader<Movie[]> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Movie[]>(this) {

            Movie[] mMovies=null;

            @Override
            protected void onStartLoading() {
                if(mMovies!=null){
                    deliverResult(mMovies);
                }else{
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public Movie[] loadInBackground() {
                try{
                    return NetworkUtils.getMovies(choice);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void deliverResult(@Nullable Movie[] data) {
                mMovies=data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Movie[]> loader, Movie[] data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMovieAdapter.setmMovieData(data);
        if (data==null){
            showErrorMessage();
        }else {
            showMoviesDataView();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Movie[]> loader) {

    }

    private void invalidateData(){
        mMovieAdapter.setmMovieData(null);
    }

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
            //loadMoviesData(choice);
            invalidateData();
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID,null,this);
            return true;
        }
        if (id==R.id.sort_top_rated){
            mRecyclerView.setVisibility(View.INVISIBLE);
            choice=BASE_URL+TOP_RATED_PATH+QUERY;
            //loadMoviesData(choice);
            invalidateData();
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID,null,this);
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
        //int loaderId=MOVIE_LOADER_ID;
        //LoaderManager.LoaderCallbacks<Movie[]> callbacks=MainActivity.this;
        //Bundle bundle=null;
        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID,null,MainActivity.this);

    }

    @Override
    public void onClick(Movie movie) {
        Intent intent=new Intent(this,DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA,movie);
        startActivity(intent);
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

