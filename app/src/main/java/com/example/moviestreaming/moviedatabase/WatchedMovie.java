package com.example.moviestreaming.moviedatabase;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "watched_Movie")
public class WatchedMovie {
    @PrimaryKey
    @NonNull private String id;
    private long timeWatched;

    public WatchedMovie() {
    }

    public WatchedMovie(@NonNull String id, long timeWatched) {
        this.id = id;
        this.timeWatched = timeWatched;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public long getTimeWatched() {
        return timeWatched;
    }

    public void setTimeWatched(long timeWatched) {
        this.timeWatched = timeWatched;
    }
}
