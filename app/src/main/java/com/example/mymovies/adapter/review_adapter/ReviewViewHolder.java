package com.example.mymovies.adapter.review_adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mymovies.R;
import org.jetbrains.annotations.NotNull;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewAuthor;
    private TextView textViewContent;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
        textViewContent = itemView.findViewById(R.id.textViewContent);
    }

    public TextView getTextViewAuthor() {
        return textViewAuthor;
    }

    public TextView getTextViewContent() {
        return textViewContent;
    }
}
