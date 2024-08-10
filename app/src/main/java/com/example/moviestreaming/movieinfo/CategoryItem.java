package com.example.moviestreaming.movieinfo;

import com.google.gson.annotations.SerializedName;

public class CategoryItem {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
