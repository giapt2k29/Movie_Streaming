package com.example.moviestreaming.GetApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewFilm {
    @SerializedName("status")
    private boolean status;
    @SerializedName("items")
    private List<Items> items;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
