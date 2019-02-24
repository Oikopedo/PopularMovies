package com.example.popularmovies;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmovies.Movie.Movie;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private Movie[] mMoviedData;
    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMovieImageView;

        public MovieAdapterViewHolder(View view){
            super(view);
            mMovieImageView=(ImageView) view.findViewById(R.id.tv_movie_data);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            mClickHandler.onClick(mMoviedData[getAdapterPosition()]);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewHolder, int i) {
        Uri uri=Uri.parse("http://image.tmdb.org/t/p/w500/"+mMoviedData[i].getImageSource())
                .buildUpon().build();
        Log.v("URI GAGARIN",uri.toString());

        Picasso.get().load(uri).into(movieAdapterViewHolder.mMovieImageView);
    }
    @Override
    public int getItemCount() {
        if (null == mMoviedData) return 0;
        return mMoviedData.length;
    }
    public void setmMoviedData(Movie[] moviedData) {
        mMoviedData = moviedData;
        notifyDataSetChanged();
    }
}
