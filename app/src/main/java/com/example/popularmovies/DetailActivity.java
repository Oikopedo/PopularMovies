package com.example.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.Movie.Movie;
import com.example.popularmovies.NetworkUtils.NetworkUtils;
import com.example.popularmovies.UIUtils.UIUtils;
import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String[]>, TrailerAdapter.TrailerAdapterOnClickHandler {

    public final static String EXTRA="Movie";
    public final static String IMAGE_PATH="http://image.tmdb.org/t/p/w500/";

    private RecyclerView mTrailerRecyclerView;
    private TrailerAdapter mTrailerAdapter;

    private Movie mMovie;
    //private TextView mMovieName;
    //private ImageView mMovieImage;
    //private TextView mMovieSynopsis;
    //private TextView mMovieRating;
    //private TextView mMovieReleaseDate;

    private static final int TRAILER_LOADER_ID=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView mMovieName=(TextView) findViewById(R.id.tv_movie_name);
        ImageView mMovieImage=(ImageView) findViewById(R.id.tv_movie_image);
        TextView mMovieSynopsis=(TextView) findViewById(R.id.tv_movie_synopsis);
        TextView mMovieRating=(TextView) findViewById(R.id.tv_movie_rating);
        TextView mMovieReleaseDate=(TextView) findViewById(R.id.tv_movie_release_date);
        mTrailerRecyclerView=(RecyclerView) findViewById(R.id.trailer_recycler_view);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        mTrailerRecyclerView.setLayoutManager(layoutManager);
        mTrailerRecyclerView.setHasFixedSize(true);
        mTrailerAdapter=new TrailerAdapter(this);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);
        setHeight();

        Intent intent=getIntent();
        if(intent!=null){
            if(intent.hasExtra(EXTRA)){
                mMovie=(Movie)intent.getParcelableExtra(EXTRA);

                mMovieName.setText(mMovie.getName());
                Uri uri=Uri.parse(IMAGE_PATH+mMovie.getImageSource())
                        .buildUpon().build();
                Picasso.get().load(uri).into(mMovieImage);

                mMovieSynopsis.setText(mMovie.getSynopsis());
                mMovieRating.setText(Float.toString(mMovie.getRating()));
                mMovieReleaseDate.setText(mMovie.getReleaseDate());
            }
        }
        getSupportLoaderManager().initLoader(TRAILER_LOADER_ID,null,DetailActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId=item.getItemId();
        if (itemId==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<String[]> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String[]>(this) {

            String[] trailers=null;

            @Override
            protected void onStartLoading() {
                if(trailers!=null){
                    deliverResult(trailers);
                }else{
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public String[] loadInBackground() {
                try{
                    return NetworkUtils.getTrailers(mMovie.getId());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void deliverResult(@Nullable String[] data) {
                //Log.v("LENGTH",String.valueOf(data.length));
                trailers=data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String[]> loader, String[] data) {
        mTrailerAdapter.setmTrailerData(data);
        setHeight();
        if (data==null){
            mTrailerRecyclerView.setVisibility(View.INVISIBLE);
        }else{
            mTrailerRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String[]> loader) {

    }

    private void invalidateData(){
        mTrailerAdapter.setmTrailerData(null);
        setHeight();
    }

    @Override
    public void onClick(String trailer) {
        Toast toast=Toast.makeText(this,"Not implemented: "+trailer,Toast.LENGTH_LONG);
        toast.show();

    }

    private void setHeight(){
        ViewGroup.LayoutParams params = mTrailerRecyclerView.getLayoutParams();
        params.height =  TrailerAdapter.height*mTrailerAdapter.getItemCount();
        Log.v("H",String.valueOf(TrailerAdapter.height));
        Log.v("HEOGHT",String.valueOf( TrailerAdapter.height*mTrailerAdapter.getItemCount()));
        mTrailerRecyclerView.setLayoutParams(params);
        mTrailerRecyclerView.requestLayout();
    }
}
