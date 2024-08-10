package com.example.moviestreaming;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreaming.GetApi.ApiRespone;
import com.example.moviestreaming.GetApi.Movie;
import com.example.moviestreaming.GetApi.SearchApiService;
import com.example.moviestreaming.adapter.SearchItem;
import com.example.moviestreaming.model.MovieSearch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.search_activity);
        recyclerView = findViewById(R.id.search_recycler);
        editText = findViewById(R.id.search_bar);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                Search(input);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void setSearchRecycler(List<MovieSearch> allSearchList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SearchItem searchItem = new SearchItem(this, allSearchList);
        recyclerView.setAdapter(searchItem);
    }

    public void Search(String text) {
        SearchApiService.searchApiService.SearchMovie(text).enqueue(new Callback<ApiRespone>() {
            @Override
            public void onResponse(Call<ApiRespone> call, Response<ApiRespone> response) {
                List<MovieSearch> movieSearchList = new ArrayList<>();
                if(response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getData().getItems();
                    for(Movie movie : movies) {
                        String imageUrl = "https://phimimg.com/" + movie.getPoster_url();
                        Log.e("ImageUrl", imageUrl);
                        movieSearchList.add(new MovieSearch(movie.getName(), imageUrl, movie.getYear(), movie.getTime(), movie.getEpisode_current(), movie.getSlug()));
                    }
                }
                setSearchRecycler(movieSearchList);
            }

            @Override
            public void onFailure(Call<ApiRespone> call, Throwable t) {

            }
        });
    }
}
