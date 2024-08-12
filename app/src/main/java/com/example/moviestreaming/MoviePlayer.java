package com.example.moviestreaming;

import android.content.Intent;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.room.Room;

import com.example.moviestreaming.moviedatabase.AppDatabase;
import com.example.moviestreaming.moviedatabase.WatchedMovie;
import com.example.moviestreaming.moviedatabase.WatchedMovieDAO;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MoviePlayer extends AppCompatActivity {
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    ExoPlayer exoPlayer;
    PlayerView playerView;
    long delayTime = 60000;
    Handler handler = new Handler();
    boolean firstTimeOpen = true;
    boolean newItem = false;
    WatchedMovie watchedMovie2 = new WatchedMovie();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.playmovie);

        playerView = findViewById(R.id.movieplay);

        WatchedMovieDAO watchedMovieDAO = AppDatabase.getInstance(this).watchedMovieDAO();

        Intent intent = getIntent();
        String url = intent.getStringExtra("movieurl");

        if(firstTimeOpen == true) {
            if (watchedMovieDAO.getWatchedMoviebyID(url) == null) {
                try {
                    Log.e("Querry ok", "Nothing in here");
                    WatchedMovie watchedMovie = new WatchedMovie(url, 0);
                    watchedMovieDAO.insert(watchedMovie);
                }catch (Exception e) {
                    Log.e("Querry wrong", e.getMessage());
                }
                finally {
                    newItem = true;
                }
            }
            else {
                Log.e("Querry ok", watchedMovieDAO.getWatchedMoviebyID(url).getId());
                watchedMovie2 = watchedMovieDAO.getWatchedMoviebyID(url);
                newItem = false;
            }
            firstTimeOpen = false;
        }

        String link = "";
        exoPlayer = new ExoPlayer.Builder(this).build();

        for(int i = 0; i < url.length(); i++) {
            if(url.charAt(i) == '?') {
                for(int j = i + 5; j < url.length(); j++) {
                    link += url.charAt(j);
                }
            }
        }

        if (url != null && !url.isEmpty() && newItem == true) {
            Log.d("MoviePlayer", "URL received: " + url);

            exoPlayer = new ExoPlayer.Builder(this).build();
            playerView.setPlayer(exoPlayer);

            Uri videoUri = Uri.parse(link);
            MediaItem mediaItem = MediaItem.fromUri(videoUri);

            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.seekTo(0);
            exoPlayer.setPlayWhenReady(true);
        }

        if(url != null && !url.isEmpty() && newItem == false) {
            long startTime = watchedMovie2.getTimeWatched();

            exoPlayer = new ExoPlayer.Builder(this).build();
            playerView.setPlayer(exoPlayer);

            Uri videoUri = Uri.parse(link);
            MediaItem mediaItem = MediaItem.fromUri(videoUri);

            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();

            exoPlayer.seekTo(startTime);

            exoPlayer.setPlayWhenReady(true);
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mainHandler.post(() -> {
                    watchedMovie2 = new WatchedMovie(url, exoPlayer.getCurrentPosition());
                    watchedMovieDAO.update(watchedMovie2);
                });
            }
        };
        timer.scheduleAtFixedRate(task, 0, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.pause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.release();
    }
}
