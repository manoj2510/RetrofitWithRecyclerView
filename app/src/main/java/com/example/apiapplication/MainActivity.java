package com.example.apiapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.apiapplication.moviedetails.Movies;
import com.example.apiapplication.moviedetails.Result;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient.Builder builder;
    private RecyclerView mRecycler;
    private List<Result> resultList;
    private String apiKey = "aca8006bbca775523030b8e6a5a9929a";
    private String serviceName = "https://api.themoviedb.org/3/movie/";
    private MovieApi gi;
    private Retrofit retrofit;
    private Call<Movies> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        builder = getHttpClient();
        retrofit = new Retrofit.Builder().baseUrl(serviceName).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
        gi = retrofit.create(MovieApi.class);
        call = gi.getExpectedList(apiKey);
        callService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.popular_movies) {
            call = gi.getExpectedList(apiKey);
            callService();
        } else {
            call = gi.getTopList(apiKey);
            callService();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mRecycler = findViewById(R.id.movies_recycler);
    }
    /*private void callService()
    {
        //https://api.themoviedb.org/3/movie/popular?api_key=aca8006bbca775523030b8e6a5a9929a&language=en-US&page=1

        builder = getHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(serviceName).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
        MovieApi gi = retrofit.create(MovieApi.class);

        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                if(response!=null && response.body()!=null)
                {
                    Movies movies = response.body();
                    if(movies.getTotalPages()!=null && movies.getTotalResults()>0)
                    {
                        resultList = movies.getResults();
                        setValues(resultList);
                    }
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }

        });

    }*/


    private void callService() {
        //https://api.themoviedb.org/3/movie/popular?api_key=aca8006bbca775523030b8e6a5a9929a&language=en-US&page=1
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                if (response != null && response.body() != null) {
                    Movies movies = response.body();
                    if (movies.getTotalPages() != null && movies.getTotalResults() > 0) {
                        resultList = movies.getResults();
                        setValues(resultList);
                    }
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void setValues(List<Result> values) {
        MovieAdapter movieAdapter = new MovieAdapter(values, MainActivity.this);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mRecycler.setAdapter(movieAdapter);
    }

    public OkHttpClient.Builder getHttpClient() {
        if (builder == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(loggingInterceptor);
            client.writeTimeout(60000, TimeUnit.MILLISECONDS);
            client.readTimeout(60000, TimeUnit.MILLISECONDS);
            client.connectTimeout(60000, TimeUnit.MILLISECONDS);
            return client;
        }
        return builder;
    }
}
