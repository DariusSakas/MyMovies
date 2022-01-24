package com.example.mymovies.adapter.movie_adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mymovies.DetailActivity;
import com.example.mymovies.R;
import com.example.mymovies.adapter.OnPosterClickListener;
import com.example.mymovies.adapter.OnReachEndListener;
import com.example.mymovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<Movie> movies;
    private OnReachEndListener onReachEndListener;

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public MovieAdapter() {
        this.movies = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.get().load(movie.getSmallPosterPath()).into(holder.getImageViewSmallPoster());
        holder.setOnPosterClickListener(new OnPosterClickListener() {
            @Override
            public void onPosterClick(int position1) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("id", movie.getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
        if(movies.size() >= 20 && position > movies.size() - 4 && onReachEndListener != null){
            onReachEndListener.onReachEnd();
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addMovies (List<Movie> movieArrayList){
        notifyDataSetChanged();
        this.movies.addAll(movieArrayList);
    }

    public List<Movie> getMovies() {
        notifyDataSetChanged();
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void clear(){
        this.movies.clear();
        notifyDataSetChanged();
    }

}
