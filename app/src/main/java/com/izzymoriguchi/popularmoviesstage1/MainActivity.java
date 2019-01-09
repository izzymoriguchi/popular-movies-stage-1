package com.izzymoriguchi.popularmoviesstage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.izzymoriguchi.popularmoviesstage1.model.Movie;
import com.izzymoriguchi.popularmoviesstage1.utilities.JsonUtils;
import com.izzymoriguchi.popularmoviesstage1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MovieAdapter adapter;
    private RecyclerView recyclerView;
    private MovieAdapter.MovieItemClickListener listener;
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

        listener = this;
    }

    @Override
    public void onMovieItemClick(int index) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        startActivity(intent);
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
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter = new MovieAdapter(listOfMovies, listener);
            recyclerView.setAdapter(adapter);
        }
    }
}
