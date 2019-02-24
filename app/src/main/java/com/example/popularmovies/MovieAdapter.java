package com.example.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.Movie.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private Movie[] mMoviedData;

    public MovieAdapter(){

    }
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder{
        public final TextView mMovieTextView;

        public MovieAdapterViewHolder(View view){
            super(view);
            mMovieTextView=(TextView) view.findViewById(R.id.tv_movie_data);
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
        movieAdapterViewHolder.mMovieTextView.setText(mMoviedData[i].toString());
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
