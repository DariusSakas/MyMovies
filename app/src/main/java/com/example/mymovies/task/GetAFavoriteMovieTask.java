package com.example.mymovies.task;

import android.content.Context;
import android.os.AsyncTask;
import com.example.mymovies.database.MovieDatabase;
import com.example.mymovies.model.FavoriteMovie;

public class GetAFavoriteMovieTask extends AsyncTask<Integer, Void, FavoriteMovie> {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected FavoriteMovie doInBackground(Integer... integers) {
        if (integers != null && integers.length > 0) {
            return MovieDatabase.getInstance(context).movieDAO().getFavoriteMovieById(integers[0]);
        }
        return null;
    }
}
