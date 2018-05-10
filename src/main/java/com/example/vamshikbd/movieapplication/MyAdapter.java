
package com.example.vamshikbd.movieapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/*
    This adapter method places the content in respective UI components.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] movieName;
    private String[] movieOverview;
    private String[] movieReleaseDate;
    private Bitmap[] moviePoster;
    private Integer[] voteAverage;
    private Boolean[] isAdultMovie;
    private String[] popularity;
    private Context mContext;
    private Integer resultArrayLength;
    private Integer resource;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;
        public TextView mOverviewTextView;
        public ImageView mMoviePoster;
        public TextView mReleaseDate;
        public TextView mVoteAverage;
        public TextView mPopularity;
        public TextView mAdultMovie;

        /*
            This method initializes the UI components.
         */
        public ViewHolder(View v) {
            super(v);
            mTitleTextView = (TextView)v.findViewById(R.id.movieTitle);
            mOverviewTextView = (TextView)v.findViewById(R.id.movieOverView);
            mMoviePoster = (ImageView)v.findViewById(R.id.moviePoster);
            mReleaseDate = (TextView)v.findViewById(R.id.releaseDate);
            mVoteAverage = (TextView)v.findViewById(R.id.voteAverage);
            mPopularity = (TextView)v.findViewById(R.id.popularity);
            mAdultMovie = (TextView)v.findViewById(R.id.adultMovie);
        }
    }

    /*
        This is the constructor method which initializes the data.
     */
    public MyAdapter(String[] movieName, String[] movieOverview, String[] movieReleaseDate, Bitmap[] moviePoster, Context mContext, Integer resultArrayLength, Integer[] voteAverage, String[] popularity, Boolean[] isAdultMovie, Integer resource) {
        this.movieName = movieName;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.moviePoster = moviePoster;
        this.mContext = mContext;
        this.resultArrayLength = resultArrayLength;
        this.isAdultMovie = isAdultMovie;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
        this.resource = resource;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(mContext).inflate(resource, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /*
        This method sets the data to the UI Components.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitleTextView.setText(movieName[position]);
        holder.mOverviewTextView.setText(movieOverview[position]);
        holder.mMoviePoster.setImageBitmap(moviePoster[position]);
        holder.mReleaseDate.setText("Release Date: " + movieReleaseDate[position]);
        holder.mVoteAverage.setText("Vote Average: " + voteAverage[position]);
        holder.mPopularity.setText("Popularity: " + popularity[position]);
        holder.mAdultMovie.setText("Adult Movie: " + isAdultMovie[position]);
    }

    @Override
    public int getItemCount() {
        return resultArrayLength;
    }
}


