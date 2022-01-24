package com.example.mymovies.task;

import android.content.Context;
import android.os.AsyncTask;
import com.example.mymovies.database.MovieDatabase;
import com.example.mymovies.model.Movie;

public class DeleteAMovieTask extends AsyncTask<Movie, Void, Void> {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Movie... movies) {
        if (movies != null && movies.length > 0) {
            MovieDatabase.getInstance(context).movieDAO().deleteMovie(movies[0]);
        }
        return null;
    }
}
