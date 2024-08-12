package com.example.moviestreaming.moviedatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface WatchedMovieDAO {
    @Insert
    void insert(WatchedMovie... watchedMovies);
    @Update
    void update(WatchedMovie... watchedMovies);

    @Query("SELECT * FROM watched_Movie WHERE id = :id")
    WatchedMovie getWatchedMoviebyID(String id);
}
