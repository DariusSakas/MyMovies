package com.example.mymovies.utils;

import android.net.Uri;
import com.example.mymovies.task.JSONLoadTask;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String BASE_URL_GET_VIDEOS = "https://api.themoviedb.org/3/movie/%s/videos";
    private static final String BASE_URL_GET_REVIEWS =  "https://api.themoviedb.org/3/movie/%s/reviews";

    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String PARAMS_SORT_BY = "sort_by";
    private static final String PARAMS_PAGE = "page";
    private static final String PARAMS_MIN_VOTE_COUNT = "vote_count.gte";

    private static final String API_KEY = "065d36a40c1cebd0e279c4c05777eff3";
    private static final String LANGUAGE_VALUE = "en-US";
    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_TOP_RATED = "vote_average.desc";
    private static final String MINIMUM_VOTE_COUNT = "1000";

    public static final int POPULARITY = 0;
    public static final int TOP_RATED = 1;

    public static URL buildGetVideosUrl(int id){
        Uri uri = Uri.parse(String.format(BASE_URL_GET_VIDEOS, id)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONForVideos(int id){
        JSONObject jsonResult = null;
        URL url = buildGetVideosUrl(id);
        try {
            jsonResult = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    public static URL buildGetReviewsUrl(int id){
        Uri uri = Uri.parse(String.format(BASE_URL_GET_REVIEWS, id)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONForReviews(int id){
        JSONObject jsonResult = null;
        URL url = buildGetReviewsUrl(id);
        try {
            jsonResult = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    public static URL buildUrl(int sortBy, int page) {
        URL result = null;
        String methodOfSort;
        if (sortBy == POPULARITY) {
            methodOfSort = SORT_BY_POPULARITY;
        } else
            methodOfSort = SORT_BY_TOP_RATED;

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))
                .appendQueryParameter(PARAMS_MIN_VOTE_COUNT, MINIMUM_VOTE_COUNT)
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONFromNetwork(int sortBy, int page) {
        JSONObject jsonResult = null;
        URL url = buildUrl(sortBy, page);
        try {
            jsonResult = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
}
