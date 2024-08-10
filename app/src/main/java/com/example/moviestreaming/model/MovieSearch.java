package com.example.moviestreaming.model;

public class MovieSearch {
    private String name;
    private String Imageurl;
    private String time;
    private String year;
    private String episode_current;
    private String slug;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEpisode_current() {
        return episode_current;
    }

    public void setEpisode_current(String episode_current) {
        this.episode_current = episode_current;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    public MovieSearch(String name, String imageurl, String time, String year, String episode_current, String slug) {
        this.name = name;
        Imageurl = imageurl;
        this.time = time;
        this.year = year;
        this.episode_current = episode_current;
        this.slug = slug;
    }
}
