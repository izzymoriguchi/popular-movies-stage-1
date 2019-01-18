package com.izzymoriguchi.popularmoviesstage1;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import com.izzymoriguchi.popularmoviesstage1.model.Movie;
import com.izzymoriguchi.popularmoviesstage1.utilities.JsonUtils;
import com.izzymoriguchi.popularmoviesstage1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private MovieAdapter.MovieItemClickListener listener;
    private ArrayList<Movie> listOfMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listOfMovies = new ArrayList<>();

        URL url = NetworkUtils.buildUrl(NetworkUtils.SORT_BY_POPULAR);
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
        intent.putExtra(intent.EXTRA_TEXT, listOfMovies.get(index));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_sort_popular) {
            new MovieQueryTask().execute(NetworkUtils.buildUrl(NetworkUtils.SORT_BY_POPULAR));
            return true;
        } else if (itemId == R.id.menu_sort_top_rated) {
            new MovieQueryTask().execute(NetworkUtils.buildUrl(NetworkUtils.SORT_BY_TOP_RATED));
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            MovieAdapter adapter = new MovieAdapter(listOfMovies, listener);
            recyclerView.setAdapter(adapter);
        }
    }
}
