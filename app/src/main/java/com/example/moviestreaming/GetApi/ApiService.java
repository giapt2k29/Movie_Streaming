package com.example.moviestreaming.GetApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://phimapi.com/v1/api/danh-sach/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("phim-le")
    Call<ApiRespone> getMovies(@Query("page") int page);

    @GET("phim-bo")
    Call<ApiRespone> getMoviesbo(@Query("page") int page);

    @GET("hoat-hinh")
    Call<ApiRespone> getHoathinh(@Query("page") int page);

}
