package com.example.mymovies;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mymovies.adapter.movie_adapter.MovieAdapter;
import com.example.mymovies.model.Movie;
import com.example.mymovies.utils.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private Switch switchSort;
    private TextView textViewRated;
    private TextView textViewPopularity;
    private MainViewModel viewModel;
    private ProgressBar progressBarLoading;

    private static final int LOADER_ID = 122;
    private LoaderManager loaderManager;

    private static int methodOfSort;
    private static int page = 1;
    private static boolean isLoading = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
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

    private int getColumnCount (){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        return Math.max(width / 185, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieAdapter = new MovieAdapter();
        loaderManager = LoaderManager.getInstance(this);

        setActivityViewComponents();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);


        setRecyclerView();
        callSetOnCheckedClickListener();
        callSetOnReachEndListener();
        observeMoviesOnLiveData();
    }

    private void setRecyclerView() {
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
    }

    private void observeMoviesOnLiveData() {
        LiveData<List<Movie>> moviesFromLiveData = viewModel.getMovies();
        moviesFromLiveData.observe(this, movies -> {
            if (page == 1){
                movieAdapter.setMovies(movies);
            }
        });
    }

    private void callSetOnReachEndListener() {
        movieAdapter.setOnReachEndListener(() -> {
            if (!isLoading) {
                downloadData(methodOfSort, page);
            }
        });
    }

    private void setActivityViewComponents() {
        textViewRated = findViewById(R.id.textViewTopRated);
        textViewPopularity = findViewById(R.id.textViewPopularity);
        switchSort = findViewById(R.id.switchSort);
        recyclerView = findViewById(R.id.recyclerViewPosters);
        progressBarLoading = findViewById(R.id.progressBarLoading);
    }

    private void callSetOnCheckedClickListener() {
        switchSort.setChecked(true);
        switchSort.setOnCheckedChangeListener((compoundButton, b) -> {
            page = 1;
            setMethodOfSort(b);
        });
        switchSort.setChecked(false);
    }

    public void onClickSetPopularity(View view) {
        setMethodOfSort(false);
        switchSort.setChecked(false);
    }

    public void onClickSetTopRated(View view) {
        setMethodOfSort(true);
        switchSort.setChecked(true);
    }

    private void setMethodOfSort(boolean b) {
        if (b) {
            textViewPopularity.setTextColor(getResources().getColor(R.color.white));
            textViewRated.setTextColor(getResources().getColor(R.color.pink));
            methodOfSort = NetworkUtils.TOP_RATED;
        } else {
            textViewPopularity.setTextColor(getResources().getColor(R.color.pink));
            textViewRated.setTextColor(getResources().getColor(R.color.white));
            methodOfSort = NetworkUtils.POPULARITY;
        }
        downloadData(methodOfSort, page);
    }

    private void downloadData(int methodOfSort, int page) {
        URL url = NetworkUtils.buildUrl(methodOfSort, page);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(LOADER_ID, bundle, this);
    }

    @NonNull
    @NotNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {
        JSONLoader jsonLoader = new JSONLoader(this, args);
        jsonLoader.setOnStartLoadingListener(new OnStartLoadingListener() {
            @Override
            public void onStartLoading() {
                progressBarLoading.setVisibility(View.VISIBLE);
                isLoading = true;
            }
        });
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull @NotNull Loader<JSONObject> loader, JSONObject data) {
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(data);
        if (movies != null && !movies.isEmpty()) {
            if(page == 1) {
                viewModel.deleteAllMovies();
                movieAdapter.clear();
            }
            for (Movie movie : movies) {
                viewModel.insertMovie(movie);
            }
            movieAdapter.addMovies(movies);
            page++;
        }
        progressBarLoading.setVisibility(View.INVISIBLE);
        isLoading = false;
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull @NotNull Loader<JSONObject> loader) {

    }
}
