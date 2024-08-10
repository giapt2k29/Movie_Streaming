package com.example.moviestreaming;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.moviestreaming.GetApi.ApiRespone;
import com.example.moviestreaming.GetApi.ApiService;
import com.example.moviestreaming.GetApi.ApiServiceNewFilm;
import com.example.moviestreaming.GetApi.Items;
import com.example.moviestreaming.GetApi.Movie;
import com.example.moviestreaming.GetApi.NewFilm;
import com.example.moviestreaming.GetApi.SearchApiService;
import com.example.moviestreaming.adapter.MainRecyclerAdapter;
import com.example.moviestreaming.adapter.PaperAdapter;
import com.example.moviestreaming.model.AllCategory;
import com.example.moviestreaming.model.BannerMovies;
import com.example.moviestreaming.model.CategoryItem;
import com.example.moviestreaming.model.MovieSearch;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    PaperAdapter bannerMoviePaperAdapter;
    TabLayout indicatorTab, categoryTab;
    ViewPager bannerMovieViewPaper;
    ImageButton search;

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;
    List<AllCategory> allCategoryList = new ArrayList<>();

    //List Banner Film
    List<BannerMovies> bannerMoviesList = new ArrayList<>();
    List<BannerMovies> PhimleList = new ArrayList<>();
    List<BannerMovies> PhimboList = new ArrayList<>();
    List<BannerMovies> Hoathinh = new ArrayList<>();
    List<CategoryItem> homeCatListItem1, homCatListItem2, homeCatListItem3, homeCatListItem4;
    private final Integer paperCount = 5;
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        indicatorTab = findViewById(R.id.tab_indicator);
        categoryTab = findViewById(R.id.tabLayout);
        search = findViewById(R.id.btn_search);

        homeCatListItem1 = new ArrayList<>();
        homCatListItem2 = new ArrayList<>();
        homeCatListItem3 = new ArrayList<>();
        homeCatListItem4 = new ArrayList<>();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        getPhimmoi();
        getPhimLe();
        getPhimbo();
        getHoathinh();

        HomeCategory();
        HomeCategory2();
        HomeCategory3();
        HomeCategory4();

        setMainRecycler(allCategoryList);

        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        setBannerMoviesPaperAdapter(bannerMoviesList);
                        setMainRecycler(allCategoryList);
                        return;
                    case 1:
                        setBannerMoviesPaperAdapter(PhimleList);
                        setMainRecycler(allCategoryList);
                        return;
                    case 2:
                        setBannerMoviesPaperAdapter(PhimboList);
                        setMainRecycler(allCategoryList);
                        return;
                    case 3:
                        setBannerMoviesPaperAdapter(Hoathinh);
                        setMainRecycler(allCategoryList);
                        return;
                    default:
                        setBannerMoviesPaperAdapter(bannerMoviesList);
                        setMainRecycler(allCategoryList);
                        return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class AutoSlider extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(() -> {
                if(bannerMovieViewPaper.getCurrentItem() < paperCount - 1) {
                    bannerMovieViewPaper.setCurrentItem(bannerMovieViewPaper.getCurrentItem() + 1);
                }else{
                    bannerMovieViewPaper.setCurrentItem(0);
                }
            });
        }
    }

    public void getPhimmoi() {
        ApiServiceNewFilm.apiServiceNewFilm.getMoicapnhat(1).enqueue(new Callback<NewFilm>() {
            @Override
            public void onResponse(Call<NewFilm> call, Response<NewFilm> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Items> movies = response.body().getItems();
                    for (Items movie : movies) {
                        Log.e("Movie", movie.getId() + " " + movie.getName());
                        bannerMoviesList.add(new BannerMovies(movie.getId(), movie.getName(), movie.getThumb(), "", movie.getSlug()));

                        if(bannerMoviesList.size() == 5) {
                            break;
                        }
                    }
                    if(firstTime == true)
                    {
                        setBannerMoviesPaperAdapter(bannerMoviesList);
                        firstTime = false;
                    }
                } else {
                    Log.e("Error", "Response not successful or body is null");
                }
            }
            @Override
            public void onFailure(Call<NewFilm> call, Throwable t) {
                Log.e("API Failure", "Error" + t.getMessage());
                t.printStackTrace();
            }
        });
    }
    public void getPhimLe() {
            ApiService.apiService.getMovies(1).enqueue(new Callback<ApiRespone>() {
                @Override
                public void onResponse(Call<ApiRespone> call, Response<ApiRespone> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Movie> movies = response.body().getData().getItems();
                        for (Movie movie : movies) {
                            String ImageUrl = "https://phimimg.com/" + movie.getThumb_url();
                            PhimleList.add(new BannerMovies(movie.getId(), movie.getName(), ImageUrl, movie.getSlug(), movie.getSlug()));
                            if(PhimleList.size() == 5) {
                                break;
                            }
                        }
                    } else {
                        Log.e("Error", "Response not successful or body is null");
                    }
                }

                @Override
                public void onFailure(Call<ApiRespone> call, Throwable t) {
                    Log.e("API Failure", "Error" + t.getMessage());
                    t.printStackTrace();
                }
            });
    }

    public void getPhimbo() {
        ApiService.apiService.getMoviesbo(1).enqueue(new Callback<ApiRespone>() {
            @Override
            public void onResponse(Call<ApiRespone> call, Response<ApiRespone> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getData().getItems();
                    for(Movie movie : movies) {
                        String ImageUrl = "https://phimimg.com/" + movie.getThumb_url();
                        PhimboList.add(new BannerMovies(movie.getId(), movie.getName(), ImageUrl, movie.getSlug(), movie.getSlug()));
                        if(PhimboList.size() == 5) {
                            break;
                        }
                    }
                }
                else {
                    Log.e("Error", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<ApiRespone> call, Throwable t) {
                Log.e("API Failure", "Error" + t.getMessage());
                t.printStackTrace();
            }
        });
    }
    public void getHoathinh() {
        ApiService.apiService.getHoathinh(1).enqueue(new Callback<ApiRespone>() {
            @Override
            public void onResponse(Call<ApiRespone> call, Response<ApiRespone> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getData().getItems();
                    for(Movie movie : movies) {
                        String ImageUrl = "https://phimimg.com/" + movie.getThumb_url();
                        Hoathinh.add(new BannerMovies(movie.getId(), movie.getName(), ImageUrl, movie.getSlug(), movie.getSlug()));
                        if(Hoathinh.size() == 5) {
                            break;
                        }
                    }
                }
                else {
                    Log.e("Error", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<ApiRespone> call, Throwable t) {
                Log.e("API Failure", "Error" + t.getMessage());
                t.printStackTrace();
            }
        });
    }
    private void setBannerMoviesPaperAdapter(List<BannerMovies> bannerMoviesList) {
        bannerMovieViewPaper = findViewById(R.id.banner_view);
        bannerMoviePaperAdapter = new PaperAdapter(this, bannerMoviesList);

        bannerMovieViewPaper.setAdapter(bannerMoviePaperAdapter);
        indicatorTab.setupWithViewPager(bannerMovieViewPaper);

        Timer sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        indicatorTab.setupWithViewPager(bannerMovieViewPaper, true);
    }
    public void setMainRecycler(List<AllCategory> allCategoryList) {
        mainRecycler = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(MainActivity.this, allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);
    }
    public void HomeCategory() {
        SearchApiService.searchApiService.SearchMovie("hành động").enqueue(new Callback<ApiRespone>() {
            public void onResponse(Call<ApiRespone> call, Response<ApiRespone> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getData().getItems();
                    for(Movie movie : movies) {
                        String imageUrl = "https://phimimg.com/" + movie.getThumb_url();
                        Log.e("ImageUrl_test", imageUrl);
                        homeCatListItem1.add(new CategoryItem(1, movie.getName(), imageUrl, movie.getSlug()));
                        if(homeCatListItem1.size() == 5) {
                            break;
                        }
                    }
                    allCategoryList.add(new AllCategory(1, "Hành Động", homeCatListItem1));
                    setMainRecycler(allCategoryList);
                }
            }

            @Override
            public void onFailure(Call<ApiRespone> call, Throwable t) {
                Log.e("Api Error", t.getMessage());
            }
        });
    }
    public void HomeCategory2() {
        SearchApiService.searchApiService.SearchMovie("kinh dị").enqueue(new Callback<ApiRespone>() {
            public void onResponse(Call<ApiRespone> call, Response<ApiRespone> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getData().getItems();
                    for(Movie movie : movies) {
                        String imageUrl = "https://phimimg.com/" + movie.getThumb_url();
                        Log.e("ImageUrl_test", imageUrl);
                        homCatListItem2.add(new CategoryItem(1, movie.getName(), imageUrl, movie.getSlug()));
                        if(homCatListItem2.size() == 5) {
                            break;
                        }
                    }
                    allCategoryList.add(new AllCategory(1, "Kinh dị", homCatListItem2));
                }
            }

            @Override
            public void onFailure(Call<ApiRespone> call, Throwable t) {
                Log.e("Api Error", t.getMessage());
            }
        });
    }
    public void HomeCategory3() {
        SearchApiService.searchApiService.SearchMovie("Học đường").enqueue(new Callback<ApiRespone>() {
            public void onResponse(Call<ApiRespone> call, Response<ApiRespone> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getData().getItems();
                    for(Movie movie : movies) {
                        String imageUrl = "https://phimimg.com/" + movie.getThumb_url();
                        Log.e("ImageUrl_test", imageUrl);
                        homeCatListItem3.add(new CategoryItem(1, movie.getName(), imageUrl, movie.getSlug()));
                        if(homeCatListItem3.size() == 5) {
                            break;
                        }
                    }
                    allCategoryList.add(new AllCategory(1, "Học đường", homeCatListItem3));
                }
            }

            @Override
            public void onFailure(Call<ApiRespone> call, Throwable t) {
                Log.e("Api Error", t.getMessage());
            }
        });
    }
    public void HomeCategory4() {
        SearchApiService.searchApiService.SearchMovie("Hàn Quốc").enqueue(new Callback<ApiRespone>() {
            public void onResponse(Call<ApiRespone> call, Response<ApiRespone> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getData().getItems();
                    for(Movie movie : movies) {
                        String imageUrl = "https://phimimg.com/" + movie.getThumb_url();
                        Log.e("ImageUrl_test", imageUrl);
                        homeCatListItem4.add(new CategoryItem(1, movie.getName(), imageUrl, movie.getSlug()));
                        if(homeCatListItem4.size() == 5) {
                            break;
                        }
                    }
                    allCategoryList.add(new AllCategory(1, "Hàn Quốc", homeCatListItem4));
                }
            }

            @Override
            public void onFailure(Call<ApiRespone> call, Throwable t) {
                Log.e("Api Error", t.getMessage());
            }
        });
    }
}