package com.example.moviestreaming.GetApi;

import com.example.moviestreaming.movieinfo.MovieInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiServiceMovieInfo {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiServiceMovieInfo apiServiceMovieInfo = new Retrofit.Builder()
            .baseUrl("https://phimapi.com/phim/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServiceMovieInfo.class);
    @GET("{movie_slug}")
    Call<MovieInfo> getMovieInfo(@Path("movie_slug") String movie_slug);
}
