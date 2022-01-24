package com.example.mymovies.task;

import android.content.Context;
import android.os.AsyncTask;
import com.example.mymovies.database.MovieDatabase;
import com.example.mymovies.model.Movie;

public class DeleteMoviesTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }
    @Override
    protected Void doInBackground(Void... voids) {
         MovieDatabase.getInstance(context).movieDAO().deleteAllMovies();
         return null;
    }
}
