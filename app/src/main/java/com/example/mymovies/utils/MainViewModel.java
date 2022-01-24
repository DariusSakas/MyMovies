package com.example.mymovies.utils;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.mymovies.database.MovieDatabase;
import com.example.mymovies.model.FavoriteMovie;
import com.example.mymovies.model.Movie;
import com.example.mymovies.task.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private final MovieDatabase movieDatabase;
    private final LiveData<List<Movie>> movies;
    private final LiveData<List<FavoriteMovie>> favoriteMovies;

    public MainViewModel(@NonNull @NotNull Application application) {
        super(application);
        movieDatabase = MovieDatabase.getInstance(getApplication());
        movies = movieDatabase.movieDAO().getAllMovies();
        favoriteMovies = movieDatabase.movieDAO().getAllFavoriteMovies();
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public Movie getMovieById(int id) {
        try {
            GetMovieTask getMovieTask = new GetMovieTask();
            getMovieTask.setContext(getApplication());
            return getMovieTask.execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllMovies(){
        DeleteMoviesTask deleteMoviesTask = new DeleteMoviesTask();
        deleteMoviesTask.setContext(getApplication());
        deleteMoviesTask.execute();
    }

    public void insertMovie(Movie movie){
        InsertMovieTask insertMovieTask = new InsertMovieTask();
        insertMovieTask.setContext(getApplication());
        insertMovieTask.execute(movie);
    }

    public void deleteMovie (Movie movie){
       DeleteAMovieTask deleteAMovieTask = new DeleteAMovieTask();
        deleteAMovieTask.setContext(getApplication());
        deleteAMovieTask.execute(movie);
    }

    public void insertFavoriteMovie(FavoriteMovie movie){
        InsertFavoriteMovieTask insertFavoriteMovieTask = new InsertFavoriteMovieTask();
        insertFavoriteMovieTask.setContext(getApplication());
        insertFavoriteMovieTask.execute(movie);
    }

    public void deleteFavoriteMovie (FavoriteMovie movie){
        DeleteAFavoriteMovieTask deleteAFavoriteMovieTask = new DeleteAFavoriteMovieTask();
        deleteAFavoriteMovieTask.setContext(getApplication());
        deleteAFavoriteMovieTask.execute(movie);
    }

    public FavoriteMovie getFavoriteMovieById(int id) {
        try {
            GetAFavoriteMovieTask getAFavoriteMovieTask = new GetAFavoriteMovieTask();
            getAFavoriteMovieTask.setContext(getApplication());
            return getAFavoriteMovieTask.execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
