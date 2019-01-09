package com.izzymoriguchi.popularmoviesstage1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.izzymoriguchi.popularmoviesstage1.model.Movie;
import com.izzymoriguchi.popularmoviesstage1.utilities.JsonUtils;
import com.izzymoriguchi.popularmoviesstage1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int NUM_LIST_ITEMS = 100;

    private MovieAdapter adapter;
    private RecyclerView recyclerView;

    ArrayList<Movie> listOfMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listOfMovies = new ArrayList<>();

        URL url = NetworkUtils.buildUrl();
        new MovieQueryTask().execute(url);

        recyclerView = findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
    }

    public class MovieQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String response = null;
            try {
                response = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (response != null) {
                listOfMovies = JsonUtils.parseMovieJson(response);
                for (int i = 0; i < 10; i++) {
                    Movie movie = listOfMovies.get(i);
                    Log.d(TAG, "doInBackground originalTitle: " + movie.getOriginalTitle());
                    Log.d(TAG, "doInBackground posterPath: " + movie.getPosterPath());
                    Log.d(TAG, "doInBackground overview: " + movie.getOverview());
                    Log.d(TAG, "doInBackground voteAverage: " + movie.getVoteAverage());
                    Log.d(TAG, "doInBackground releaseDate: " + movie.getReleaseDate());
                    Log.d(TAG, "doInBackground: ======================================");
                }
            } else {
                Log.d(TAG, "doInBackground: no response");
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter = new MovieAdapter(listOfMovies);
            recyclerView.setAdapter(adapter);
        }
    }
}
