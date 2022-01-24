package com.example.mymovies.adapter.review_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mymovies.R;
import com.example.mymovies.adapter.OnTrailerClickListener;
import com.example.mymovies.model.Review;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private ArrayList<Review> reviews;
    private OnTrailerClickListener onTrailerClickListener;

    @NotNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.getTextViewContent().setText(review.getContent());
        holder.getTextViewAuthor().setText(review.getAuthor());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }
}
