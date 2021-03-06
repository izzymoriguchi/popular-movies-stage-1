package com.izzymoriguchi.popularmoviesstage1;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.izzymoriguchi.popularmoviesstage1.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> mListOfMovies;
    private final MovieItemClickListener mOnClickListener;

    public interface MovieItemClickListener {
        void onMovieItemClick(int index);
    }

    public MovieAdapter(ArrayList<Movie> listOfMovies, MovieItemClickListener listener) {
        mListOfMovies = listOfMovies;
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.rv_list_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return mListOfMovies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieView;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieView = itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        /**
         * A method we wrote for convenience. This method will take an integer as input and
         * use that integer to display the appropriate text within a list item.
         * @param listIndex Position of the item in the list
         */
        void bind(int listIndex) {
            Picasso.get().load(mListOfMovies.get(listIndex).getPosterPath()).into(movieView);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onMovieItemClick(getAdapterPosition());
        }
    }
}
