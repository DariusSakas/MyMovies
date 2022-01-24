package com.example.mymovies;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mymovies.adapter.movie_adapter.MovieAdapter;
import com.example.mymovies.model.FavoriteMovie;
import com.example.mymovies.model.Movie;
import com.example.mymovies.utils.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavoriteMovies;
    private MovieAdapter movieAdapter;
    private MainViewModel mainViewModel;

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
        setContentView(R.layout.activity_favorite);

        recyclerViewFavoriteMovies = findViewById(R.id.recyclerViewFavoriteMovies);
        recyclerViewFavoriteMovies.setLayoutManager(new GridLayoutManager(this, 2));

        movieAdapter = new MovieAdapter();

        recyclerViewFavoriteMovies.setAdapter(movieAdapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        LiveData<List<FavoriteMovie>> movies = mainViewModel.getFavoriteMovies();
        movies.observe(this, favoriteMovies -> {
            if (favoriteMovies != null) {
                List<Movie> moviesList = new ArrayList<>(favoriteMovies);
                movieAdapter.setMovies(moviesList);
            }
        });
    }
}
