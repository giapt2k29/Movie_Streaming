package com.example.moviestreaming.GetApi;

import com.google.gson.annotations.SerializedName;

public class ApiRespone {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }
}
