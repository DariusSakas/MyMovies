package com.example.mymovies.utils;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONLoader extends AsyncTaskLoader<JSONObject> {

    private Bundle bundle;
    private OnStartLoadingListener onStartLoadingListener;

    public void setOnStartLoadingListener(OnStartLoadingListener onStartLoadingListener) {
        this.onStartLoadingListener = onStartLoadingListener;
    }

    public JSONLoader(@NonNull @NotNull Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(onStartLoadingListener != null){
            onStartLoadingListener.onStartLoading();
        }
        forceLoad();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public JSONObject loadInBackground() {
        if(bundle == null){
           return null;
        }
        URL url = null;
        String urlAsString = bundle.getString("url");
        try {

            url = new URL(urlAsString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JSONObject jsonResult = null;

        if (url == null){
            return null;
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }

            jsonResult = new JSONObject(stringBuilder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }
        return jsonResult;
    }
}
