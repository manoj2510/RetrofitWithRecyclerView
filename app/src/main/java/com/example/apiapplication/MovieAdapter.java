package com.example.apiapplication;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apiapplication.moviedetails.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<Result> moviePosters;
    private Context mContext;
    private String POSTER_PREFIX_URL = "http://image.tmdb.org/t/p/";
    private String POSTER_SIZE = "w185";

    public MovieAdapter(List<Result> moviePosters, Context mContext) {
        this.moviePosters = moviePosters;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_each_movie, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String url = moviePosters.get(position).getPosterPath();
        String IMAGE_URL = POSTER_PREFIX_URL + POSTER_SIZE + url;
        Log.e("Okay", "onBindViewHolder: " + IMAGE_URL);
        /*Picasso.get()
                .load(IMAGE_URL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.mMoviePoster);*/
        Glide
                .with(mContext)
                .load(IMAGE_URL)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()
                .into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        return moviePosters.size();
    }
}
