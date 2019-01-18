package com.izzymoriguchi.popularmoviesstage1.utilities;

import android.net.Uri;

import com.izzymoriguchi.popularmoviesstage1.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

final public class NetworkUtils {
    public final static String SORT_BY_POPULAR = "popular";
    public final static String SORT_BY_TOP_RATED = "top_rated";
    final static String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/";

    final static String PARAM_API_KEY = "api_key";

    final static String PARAM_LANGUAGE = "language";
    final static String language = "en-US";

    private NetworkUtils() {
    }

    public static URL buildUrl(String sortType) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL + sortType).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.ApiKey)
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
