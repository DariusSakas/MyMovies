package com.example.mymovies.model;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favorite_movies")
public class FavoriteMovie extends Movie {
    public FavoriteMovie(int uniqueId, int id, int voteCount, String title, String originalTitle, String overView, String smallPosterPath
            , String bigPosterPath, String backdropPath, double voteAverage, String releaseDate) {
        super(uniqueId, id, voteCount, title, originalTitle, overView, smallPosterPath, bigPosterPath, backdropPath, voteAverage, releaseDate);
    }

    @Ignore
    public FavoriteMovie(Movie movie) {
        super(movie.getUniqueId(), movie.getId(), movie.getVoteCount(), movie.getTitle(), movie.getOriginalTitle(), movie.getOverView(), movie.getSmallPosterPath()
                , movie.getBigPosterPath(), movie.getBackdropPath(), movie.getVoteAverage(), movie.getReleaseDate());

    }
}
