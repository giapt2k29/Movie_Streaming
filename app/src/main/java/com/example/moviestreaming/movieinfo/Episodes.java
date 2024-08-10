package com.example.moviestreaming.movieinfo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Episodes {
    @SerializedName("server_data")
    List<server_data> server_dataList;

    public List<com.example.moviestreaming.movieinfo.server_data> getServer_data() {
        return server_dataList;
    }

    public void setServer_data(List<com.example.moviestreaming.movieinfo.server_data> server_dataList) {
        this.server_dataList = server_dataList;
    }
}
