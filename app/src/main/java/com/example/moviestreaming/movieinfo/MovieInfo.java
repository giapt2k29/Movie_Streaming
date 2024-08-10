package com.example.moviestreaming.movieinfo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieInfo {
    @SerializedName("movie")
    Movie_InFo movie;

    public List<Episodes> getEpisodes_movie() {
        return episodes_movie;
    }

    public void setEpisodes_movie(List<Episodes> episodes_movie) {
        this.episodes_movie = episodes_movie;
    }

    @SerializedName("episodes")
    List<Episodes> episodes_movie;

    public Movie_InFo getMovie() {
        return movie;
    }

    public void setMovie(Movie_InFo movie) {
        this.movie = movie;
    }

}
