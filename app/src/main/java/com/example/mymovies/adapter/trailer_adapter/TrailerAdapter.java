package com.example.mymovies.adapter.trailer_adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mymovies.R;
import com.example.mymovies.adapter.OnTrailerClickListener;
import com.example.mymovies.model.Trailer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private ArrayList<Trailer> trailers;

    @NotNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TrailerViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.setOnTrailerClickListener(() -> {
            Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getKey()));
            holder.itemView.getContext().startActivity(intentToTrailer);
        });
        holder.getTextViewNameOfVideo().setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }
}
