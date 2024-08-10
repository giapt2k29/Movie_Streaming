package com.example.moviestreaming.GetApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceNewFilm {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiServiceNewFilm apiServiceNewFilm = new Retrofit.Builder()
            .baseUrl("https://phimapi.com/danh-sach/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServiceNewFilm.class);
    @GET("phim-moi-cap-nhat")
    Call<NewFilm> getMoicapnhat(@Query("page") int page);
}
