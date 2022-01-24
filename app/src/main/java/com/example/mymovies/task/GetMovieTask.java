package com.example.mymovies.task;

import android.content.Context;
import android.os.AsyncTask;
import com.example.mymovies.database.MovieDatabase;
import com.example.mymovies.model.Movie;

public class GetMovieTask extends AsyncTask<Integer, Void, Movie> {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }
    @Override
    protected Movie doInBackground(Integer... integers) {
        if (integers != null && integers.length > 0)
            return MovieDatabase.getInstance(context).movieDAO().getMovieById(integers[0]);
        return null;
    }
}
