package com.izzymoriguchi.popularmoviesstage1.utilities;

import com.izzymoriguchi.popularmoviesstage1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    final static String IMAGE_SIZE = "w185";

    public static ArrayList<Movie> parseMovieJson(String jsonStr) {
        ArrayList<Movie> listOfMovies = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject movie = results.getJSONObject(i);
                String posterPath = IMAGE_BASE_URL + IMAGE_SIZE + movie.getString("poster_path");
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
