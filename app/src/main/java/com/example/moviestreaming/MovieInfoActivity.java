package com.example.moviestreaming;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestreaming.GetApi.ApiServiceMovieInfo;
import com.example.moviestreaming.movieinfo.CategoryItem;
import com.example.moviestreaming.movieinfo.Episodes;
import com.example.moviestreaming.movieinfo.MovieInfo;
import com.example.moviestreaming.movieinfo.Movie_InFo;
import com.example.moviestreaming.movieinfo.server_data;
import com.google.android.flexbox.FlexboxLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieInfoActivity extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    TextView name, year, content, time, actor, director, category;
    ImageButton watch_btn;
    String listCategory = "Thể loại: ";
    String actorList = "Diễn viên: ";
    String directorList = "Đạo diễn: ";
    String ytbUrl = "";
    List<Episodes> episodesList = new ArrayList<>();
    List<server_data> server_dataList = new ArrayList<>();
    List<String> urlMovieList;
    Intent intent;
    FlexboxLayout Tapphim;
    String id;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        urlMovieList = new ArrayList<>();

        setContentView(R.layout.movieinfo);

        youTubePlayerView = findViewById(R.id.movietrailer);
        getLifecycle().addObserver(youTubePlayerView);

        textView = findViewById(R.id.textViewMovieInfo);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MovieInfoActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        name = findViewById(R.id.moviename);
        year = findViewById(R.id.year);
        content = findViewById(R.id.content_movie);
        time = findViewById(R.id.time);
        actor = findViewById(R.id.actor);
        director = findViewById(R.id.director);
        category = findViewById(R.id.category);
        watch_btn = findViewById(R.id.playmovie);
        Tapphim = findViewById(R.id.tapphim);

        intent = getIntent();
        String slug = intent.getStringExtra("slug");

        setMovie(slug);

        watch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MovieInfoActivity.this, MoviePlayer.class);
                intent.putExtra("movieurl", server_dataList.get(0).getLink_embed());
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    private void setMovie(String slug) {
        ApiServiceMovieInfo.apiServiceMovieInfo.getMovieInfo(slug).enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Movie_InFo movie_inFo = response.body().getMovie();

                    id = movie_inFo.getId();

                    List<CategoryItem> categoryItemList = movie_inFo.getCategoryItemList();
                    List<String> ActorList = movie_inFo.getActor();
                    List<String> DirectorList = movie_inFo.getDirectors();

                    for(String Actor : ActorList) {
                        actorList += Actor + ", ";
                    }
                    for(CategoryItem Actor : categoryItemList) {
                        listCategory += Actor.getName() + ", ";
                    }
                    for(String Director : DirectorList) {
                        directorList += Director + ", ";
                    }

                    String setTime = "Thời lượng: " + movie_inFo.getTime();
                    String setYear = "Năm sản xuất: " + movie_inFo.getYear();

                    Log.e("Trailer", movie_inFo.getTrailer_url());

                    name.setText(movie_inFo.getName());
                    year.setText(setYear);
                    content.setText(movie_inFo.getContent());
                    time.setText(setTime);
                    director.setText(directorList);
                    actor.setText(actorList);
                    category.setText(listCategory);

                    for(int i = 0; i < movie_inFo.getTrailer_url().length(); i++) {
                        if(movie_inFo.getTrailer_url().charAt(i) == 'v' && movie_inFo.getTrailer_url().charAt(i + 1) == '=') {
                            for(int j = i + 2; j < movie_inFo.getTrailer_url().length(); j++) {
                                ytbUrl += movie_inFo.getTrailer_url().charAt(j);
                            }
                            break;
                        }
                    }

                    youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                        @Override
                        public void onYouTubePlayer(@NonNull YouTubePlayer youTubePlayer) {
                            youTubePlayer.loadVideo(ytbUrl, 0);
                            youTubePlayer.play();
                        }
                    });

                    episodesList = response.body().getEpisodes_movie();


                    for(Episodes episodes : episodesList) {
                        server_dataList = episodes.getServer_data();

                        for(server_data serverData : server_dataList) {
                            Log.e("Movie Url", serverData.getLink_embed());
                            urlMovieList.add(serverData.getLink_embed());
                        }
                    }
                }
                if(urlMovieList.size() > 1) {
                    for(int i = 0; i < urlMovieList.size(); i++) {
                        String Tap = "Tập " + String.valueOf(i + 1);
                        Button button = new Button(MovieInfoActivity.this);
                        button.setText(Tap);
                        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT
                        );
                        int index = i;
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                intent = new Intent(MovieInfoActivity.this, MoviePlayer.class);
                                intent.putExtra("movieurl", server_dataList.get(index).getLink_embed());
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                        });
                        button.setLayoutParams(params);
                        Tapphim.addView(button);
                    }
                }
                else {
                    Log.e("Api", "Api not work");
                }
            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {
                Log.e("Api respone", t.getMessage());
            }
        });
    }
}