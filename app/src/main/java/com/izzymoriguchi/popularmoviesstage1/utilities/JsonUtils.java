package com.izzymoriguchi.popularmoviesstage1.utilities;

import com.izzymoriguchi.popularmoviesstage1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    public static ArrayList<Movie> parseMovieJson(String jsonStr) {
        ArrayList<Movie> listOfMovies = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject movie = results.getJSONObject(i);
                String posterPath = movie.getString("poster_path");
                String originalTitle = movie.getString("original_title");
                String overview = movie.getString("overview");
                double voteAverage = movie.getDouble("vote_average");
                String releaseDate = movie.getString("release_date");
                listOfMovies.add(new Movie(posterPath, originalTitle, overview, voteAverage, releaseDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listOfMovies;
    }
}
