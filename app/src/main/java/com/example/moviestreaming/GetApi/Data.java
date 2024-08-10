package com.example.moviestreaming.GetApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("items")
    private List<Movie> items;

    public List<Movie> getItems() {
        return items;
    }
}
