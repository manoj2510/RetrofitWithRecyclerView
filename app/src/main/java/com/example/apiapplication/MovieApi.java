package com.example.apiapplication;

import com.example.apiapplication.moviedetails.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("popular?")
    Call<Movies> getExpectedList(@Query("api_key") String apiKey );

    @GET("top_rated?")
    Call<Movies> getTopList(@Query("api_key") String apiKey);
}
