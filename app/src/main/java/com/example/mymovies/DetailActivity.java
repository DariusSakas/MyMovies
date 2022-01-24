package com.example.mymovies;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mymovies.adapter.OnTrailerClickListener;
import com.example.mymovies.adapter.review_adapter.ReviewAdapter;
import com.example.mymovies.adapter.trailer_adapter.TrailerAdapter;
import com.example.mymovies.database.MovieDatabase;
import com.example.mymovies.model.FavoriteMovie;
import com.example.mymovies.model.Movie;
import com.example.mymovies.model.Review;
import com.example.mymovies.model.Trailer;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.MainViewModel;
import com.example.mymovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewDescription;
    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private ScrollView scrollViewInfo;

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    private ImageView imageViewFavoriteButton;

    private MainViewModel mainViewModel;

    private int id;
    private Movie movie;
    private FavoriteMovie favoriteMovie;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itemMain:
                Intent intentMain = new Intent(this, MainActivity.class);
                startActivity(intentMain);
                break;
            case R.id.itemFavorite:
                Intent intentFavorites = new Intent(this, FavoriteActivity.class);
                startActivity(intentFavorites);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        loadActivityElements();

        setIdFromIntent();

        loadMovieInformation();

        setFavoriteMovie();

        Log.i("MovieToShow", movie.toString());

        reviewAdapter = new ReviewAdapter();
        trailerAdapter = new TrailerAdapter();

        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewReviews.setAdapter(reviewAdapter);
        recyclerViewTrailers.setAdapter(trailerAdapter);

        JSONObject jsonObjectTrailers = NetworkUtils.getJSONForVideos(movie.getId());
        JSONObject jsonObjectReviews = NetworkUtils.getJSONForReviews(movie.getId());

        ArrayList<Trailer> trailers = JSONUtils.getTrailersFromJSON(jsonObjectTrailers);
        ArrayList<Review> reviews = JSONUtils.getReviewsFromJSON(jsonObjectReviews);

        reviewAdapter.setReviews(reviews);
        trailerAdapter.setTrailers(trailers);

        scrollViewInfo.fullScroll(ScrollView.FOCUS_UP);
    }

    private void loadMovieInformation() {
        movie = mainViewModel.getMovieById(id);
        Picasso.get().load(movie.getBigPosterPath()).placeholder(R.drawable.icon).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewDescription.setText(movie.getOverView());
        textViewRating.setText(String.format("%s", movie.getVoteAverage()));
    }

    private void loadActivityElements() {
        scrollViewInfo = findViewById(R.id.scrollViewInfo);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewRating = findViewById(R.id.textViewRating);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewDescription = findViewById(R.id.textViewDescription);
        imageViewFavoriteButton = findViewById(R.id.imageViewAddToFavorite);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
    }

    public void onClickChangeFavorite(View view) {

        if (favoriteMovie == null) {
            mainViewModel.insertFavoriteMovie(new FavoriteMovie(movie));
            Toast.makeText(this, R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
        } else {
            mainViewModel.deleteFavoriteMovie(favoriteMovie);
            Toast.makeText(this, R.string.removed_from_favorites, Toast.LENGTH_SHORT).show();
        }

        setFavoriteMovie();
    }

    private void setFavoriteMovie() {
        favoriteMovie = mainViewModel.getFavoriteMovieById(id);
        if(favoriteMovie == null){
            imageViewFavoriteButton.setImageResource(R.drawable.favourite_add_to);
        }else{
            imageViewFavoriteButton.setImageResource(R.drawable.favourite_remove);
        }
    }

    private void setIdFromIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id"))
            id = intent.getIntExtra("id", -1);
        else
            finish();
    }
}
