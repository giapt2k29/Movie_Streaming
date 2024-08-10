package com.example.moviestreaming.GetApi;

import com.google.gson.annotations.SerializedName;
public class Movie {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("slug")
    private String slug;
    @SerializedName("thumb_url")
    private String thumb_url;
    @SerializedName("poster_url")
    private String poster_url;
    @SerializedName("time")
    private String time;
    @SerializedName("episode_current")
    private String episode_current;
    @SerializedName("year")
    private String year;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEpisode_current() {
        return episode_current;
    }

    public void setEpisode_current(String episode_current) {
        this.episode_current = episode_current;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }
}
