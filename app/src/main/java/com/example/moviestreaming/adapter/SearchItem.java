package com.example.moviestreaming.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviestreaming.MovieInfoActivity;
import com.example.moviestreaming.R;
import com.example.moviestreaming.model.MovieSearch;

import java.util.List;

public class SearchItem extends RecyclerView.Adapter<SearchItem.ItemViewHolder> {
    Context context;
    List<MovieSearch> movieSearchList;
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(movieSearchList.get(position).getImageurl()).into(holder.imageView);
        holder.name.setText(movieSearchList.get(position).getName());
        holder.time.setText(movieSearchList.get(position).getTime());
        holder.year.setText(movieSearchList.get(position).getYear());
        holder.episode_current.setText(movieSearchList.get(position).getEpisode_current());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieInfoActivity.class);
                intent.putExtra("slug", movieSearchList.get(holder.getAdapterPosition()).getSlug());
                context.startActivity(intent);
            }
        });
    }

    public SearchItem(Context context, List<MovieSearch> movieSearchList) {
        this.context = context;
        this.movieSearchList = movieSearchList;
    }

    @Override
    public int getItemCount() {
        return movieSearchList.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, year, time, episode_current;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.search_movie_image);
            name = itemView.findViewById(R.id.search_movie_name);
            year = itemView.findViewById(R.id.year);
            time = itemView.findViewById(R.id.time);
            episode_current = itemView.findViewById(R.id.episode_current);

        }
    }
}
