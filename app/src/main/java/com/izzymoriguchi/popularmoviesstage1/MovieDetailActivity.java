package com.izzymoriguchi.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.izzymoriguchi.popularmoviesstage1.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView mTitle;
    private ImageView mMovieImage;
    private TextView mOverview;
    private TextView mReleaseDate;
    private TextView mVoteRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mTitle = findViewById(R.id.tv_movie_title);
        mMovieImage = findViewById(R.id.iv_movie_image);
        mOverview = findViewById(R.id.tv_movie_overview);
        mReleaseDate = findViewById(R.id.tv_release_date);
        mVoteRating = findViewById(R.id.tv_user_rating);

        Intent intent = getIntent();

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            Movie movie = intent.getParcelableExtra(Intent.EXTRA_TEXT);
            Picasso.get().load(movie.getPosterPath()).into(mMovieImage);
            mTitle.setText(movie.getOriginalTitle());
            mOverview.setText(movie.getOverview());
            mReleaseDate.setText(movie.getReleaseDate());
            mVoteRating.setText(String.valueOf(movie.getVoteAverage()));
        }
    }
}
