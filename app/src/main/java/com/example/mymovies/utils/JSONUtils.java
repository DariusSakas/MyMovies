package com.example.mymovies.utils;

import com.example.mymovies.model.Movie;
import com.example.mymovies.model.Review;
import com.example.mymovies.model.Trailer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    private static final String KEY_RESULT = "results";

    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";

    private static final String KEY_NAME = "name";
    private static final String KEY_KEY_OF_VIDEO = "key";
    private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";

    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_POSTER_SIZE = "w185";
    public static final String BIG_POSTER_SIZE = "w780";

    public static ArrayList<Review> getReviewsFromJSON(JSONObject jsonObject){
        ArrayList<Review> resultArrayList = new ArrayList<>();
        if(jsonObject == null){
            return resultArrayList;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULT);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectReview = jsonArray.getJSONObject(i);
                String author = jsonObjectReview.getString(KEY_AUTHOR);
                String content = jsonObjectReview.getString(KEY_CONTENT);
                Review review = new Review(author, content);
                resultArrayList.add(review);
            }
            return resultArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Trailer> getTrailersFromJSON(JSONObject jsonObject){
        ArrayList<Trailer> resultArrayList = new ArrayList<>();
        if(jsonObject == null){
            return resultArrayList;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULT);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectReview = jsonArray.getJSONObject(i);
                String key = BASE_YOUTUBE_URL + jsonObjectReview.getString(KEY_KEY_OF_VIDEO);
                String name = jsonObjectReview.getString(KEY_NAME);
                Trailer trailer = new Trailer(key, name);
                resultArrayList.add(trailer);
            }
            return resultArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Movie> getMoviesFromJSON (JSONObject jsonObject){
        ArrayList<Movie> resultArrayList = new ArrayList<>();
        if(jsonObject == null){
            return resultArrayList;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULT);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonMovie = jsonArray.getJSONObject(i);
                int id = jsonMovie.getInt(KEY_ID);
                int voteCount = jsonMovie.getInt(KEY_VOTE_COUNT);
                String title = jsonMovie.getString(KEY_TITLE);
                String originalTitle = jsonMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = jsonMovie.getString(KEY_OVERVIEW);
                String smallPosterPath = BASE_IMAGE_URL + SMALL_POSTER_SIZE + jsonMovie.getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_IMAGE_URL + BIG_POSTER_SIZE + jsonMovie.getString(KEY_POSTER_PATH);
                String backdropPath = jsonMovie.getString(KEY_BACKDROP_PATH);
                double voteAverage = jsonMovie.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = jsonMovie.getString(KEY_RELEASE_DATE);

                Movie movie = new Movie(id, voteCount, title, originalTitle, overview, smallPosterPath, bigPosterPath, backdropPath, voteAverage, releaseDate);
                resultArrayList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultArrayList;
    }
}
