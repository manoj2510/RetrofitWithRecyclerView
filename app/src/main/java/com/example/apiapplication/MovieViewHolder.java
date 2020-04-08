package com.example.apiapplication;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    ImageView mMoviePoster;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        mMoviePoster = itemView.findViewById(R.id.movie_poster);
    }
}
