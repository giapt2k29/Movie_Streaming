package com.example.moviestreaming.movieinfo;

import com.google.gson.annotations.SerializedName;

public class server_data {
    public String getLink_embed() {
        return link_embed;
    }

    public void setLink_embed(String link_embed) {
        this.link_embed = link_embed;
    }

    @SerializedName("link_embed")
    private String link_embed;
}
