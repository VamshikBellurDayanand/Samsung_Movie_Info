package com.example.vamshikbd.movieapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*
    This is the fragment which shows Now Playing Movies.
 */
public class NowPlayingActivity extends Fragment {
    private static final String TAG = "NowPlaying";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Activity mActivity;
    private String[] title;
    private String[] popularity;
    private String[] posterPath;
    private String[] originalLanguage;
    private Boolean[] isAdultMovie;
    private String[] overview;
    private String[] releaseDate;
    private Bitmap[] img;
    private Integer[] voteAverage;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        view = inflater.inflate(R.layout.movie_layout, container, false);
        new NowPlaying().execute();
        return view;
    }

    public void createAlertDialog(String title, String message) {
        android.support.v7.app.AlertDialog.Builder alert= new android.support.v7.app.AlertDialog.Builder(mActivity);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.create().show();
    }

    class NowPlaying extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Pre Execute called");
            mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground called");
            HttpHandler httpHandler = new HttpHandler();
            String message;

            final String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=469afd1ae8f8cbb2f1181ac28519d9a0&language=en-US&page=1";
            final String json = httpHandler.makeServiceCall(url);
            Log.i("NowPlaying Activity", json);
            if(json != null) {
                try {
                    final JSONObject jsonObject = new JSONObject(json);
                    final JSONArray resultsArray = jsonObject.getJSONArray("results");
                    title = new String[resultsArray.length()];
                    popularity = new String[resultsArray.length()];
                    originalLanguage = new String[resultsArray.length()];
                    isAdultMovie = new Boolean[resultsArray.length()];
                    overview = new String[resultsArray.length()];
                    releaseDate = new String[resultsArray.length()];
                    posterPath = new String[resultsArray.length()];
                    img = new Bitmap[resultsArray.length()];
                    voteAverage = new Integer[resultsArray.length()];
                    popularity = new String[resultsArray.length()];
                    isAdultMovie = new Boolean[resultsArray.length()];

                    for(int i = 0; i < resultsArray.length(); i++) {
                        JSONObject resultObject = resultsArray.getJSONObject(i);
                        Integer voteCount = resultObject.getInt("vote_count");
                        voteAverage[i] = resultObject.getInt("vote_average");
                        title[i] = resultObject.getString("title");
                        popularity[i] = resultObject.getString("popularity");
                        posterPath[i] = resultObject.getString("poster_path");
                        originalLanguage[i] = resultObject.getString("original_language");
                        isAdultMovie[i] = resultObject.getBoolean("adult");
                        overview[i] = resultObject.getString("overview");
                        releaseDate[i] = resultObject.getString("release_date");
                        posterPath[i] = "http://image.tmdb.org/t/p/w185/" + posterPath[i].substring(1, posterPath[i].length());

                        try {
                            URL imageurl = new URL(posterPath[i]);
                            HttpURLConnection connection = (HttpURLConnection) imageurl.openConnection();
                            InputStream is = connection.getInputStream();
                            img[i] = BitmapFactory.decodeStream(is);
                        }catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mAdapter = new MyAdapter(title, overview, releaseDate, img, mActivity, resultsArray.length(), voteAverage, popularity, isAdultMovie, R.layout.movie_layout);
                            if(mAdapter != null && mRecyclerView != null) {
                                mRecyclerView.setAdapter(mAdapter);
                            } else {
                                Log.e(TAG, "Adapter or recycler view is null");
                            }
                        }
                    });
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            } else {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "Couldn't get json from server.");
                        createAlertDialog("Error", "Couldn't get json from server.");
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.i(TAG, "onPostExecute");
            if(result != null) {
                Log.i(TAG, "Result is not null");
            }
        }
    }
}
