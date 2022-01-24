package com.example.mymovies.adapter.trailer_adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mymovies.R;
import com.example.mymovies.adapter.OnPosterClickListener;
import com.example.mymovies.adapter.OnTrailerClickListener;
import com.example.mymovies.model.Trailer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TrailerViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewNameOfVideo;
    private OnTrailerClickListener onTrailerClickListener;

    public TrailerViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewNameOfVideo = itemView.findViewById(R.id.textViewNameOfVideo);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onTrailerClickListener != null){
                    onTrailerClickListener.onTrailerClick();
                }
            }
        });
    }

    public TextView getTextViewNameOfVideo() {
        return textViewNameOfVideo;
    }

    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }
}
