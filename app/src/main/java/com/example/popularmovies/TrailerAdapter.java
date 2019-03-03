package com.example.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {
    private String[] mTrailerData;
    public static int height=200;
    private final TrailerAdapterOnClickHandler mClickHandler;

    public interface TrailerAdapterOnClickHandler {
        void onClick(String trailer);
    }

    public TrailerAdapter(TrailerAdapter.TrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTrailerTextView;

        private TrailerAdapterViewHolder(View view){
            super(view);
            mTrailerTextView=(TextView) view.findViewById(R.id.tv_trailer_data);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            mClickHandler.onClick(mTrailerData[getAdapterPosition()]);
        }
    }

    @Override
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.trailer_list_item, viewGroup, false);
        view.measure(0,0);
        Log.v("HEIGHT",String.valueOf(view.getMeasuredHeight()));
        height=view.getMeasuredHeight();
        return new TrailerAdapter.TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.TrailerAdapterViewHolder trailerAdapterViewHolder, int i) {
        trailerAdapterViewHolder.mTrailerTextView.setText(mTrailerData[i]);
    }
    @Override
    public int getItemCount() {
        if (null == mTrailerData) return 0;
        return mTrailerData.length;
    }
    public void setmTrailerData(String[] trailerData) {
        mTrailerData = trailerData;
        notifyDataSetChanged();
    }
}
