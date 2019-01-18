package com.izzymoriguchi.popularmoviesstage1;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.izzymoriguchi.popularmoviesstage1.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        TextView mTitle = findViewById(R.id.tv_movie_title);
        ImageView mMovieImage = findViewById(R.id.iv_movie_image);
        TextView mOverview = findViewById(R.id.tv_movie_overview);
        TextView mReleaseDate = findViewById(R.id.tv_release_date);
        TextView mVoteRating = findViewById(R.id.tv_user_rating);
        Intent intent = getIntent();

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            Movie movie = intent.getParcelableExtra(Intent.EXTRA_TEXT);
            Picasso.get().load(movie.getPosterPath()).error(R.mipmap.ic_launcher).into(mMovieImage);
            mTitle.setText(movie.getOriginalTitle());
            mOverview.setText(movie.getOverview());
            mReleaseDate.setText(movie.getReleaseDate());
            mVoteRating.setText(String.valueOf(movie.getVoteAverage()));
        }
    }
}
