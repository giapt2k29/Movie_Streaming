package com.example.moviestreaming;

import android.content.Intent;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class MoviePlayer extends AppCompatActivity {
    ExoPlayer exoPlayer;
    PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.playmovie);

        playerView = findViewById(R.id.movieplay);

        Intent intent = getIntent();
        String url = intent.getStringExtra("movieurl");

        String link = "";
        exoPlayer = new ExoPlayer.Builder(this).build();

        for(int i = 0; i < url.length(); i++) {
            if(url.charAt(i) == '?') {
                for(int j = i + 5; j < url.length(); j++) {
                    link += url.charAt(j);
                }
            }
        }

        if (url != null && !url.isEmpty()) {
            Log.d("MoviePlayer", "URL received: " + url);

            exoPlayer = new ExoPlayer.Builder(this).build();
            playerView.setPlayer(exoPlayer);

            Uri videoUri = Uri.parse(link);
            MediaItem mediaItem = MediaItem.fromUri(videoUri);

            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.setPlayWhenReady(true);
        } else {
            Log.e("MoviePlayer", "Invalid URL");
        }
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
