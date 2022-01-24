package com.example.mymovies.task;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONLoadTask extends AsyncTask<URL, Void, JSONObject> {
    @Override
    protected JSONObject doInBackground(URL... urls) {
        JSONObject jsonResult = null;

        if (urls == null || urls.length == 0){
            return jsonResult;
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) urls[0].openConnection();
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
