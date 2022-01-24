package com.example.mymovies.adapter.movie_adapter;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mymovies.R;
import com.example.mymovies.adapter.OnPosterClickListener;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageViewSmallPoster;
    private OnPosterClickListener onPosterClickListener;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);

        imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);

        itemView.setOnClickListener(view -> {
            if(onPosterClickListener !=null){
                onPosterClickListener.onPosterClick(getAdapterPosition());
            }
        });
    }

    public OnPosterClickListener getOnPosterClickListener() {
        return onPosterClickListener;
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public ImageView getImageViewSmallPoster() {
        return imageViewSmallPoster;
    }
}
