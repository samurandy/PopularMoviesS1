package com.example.android.popularmoviess1;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.android.popularmoviess1.MainActivity.API_KEY;
import static com.example.android.popularmoviess1.MainActivity.PARAM_API;
import static com.example.android.popularmoviess1.MainActivity.THEMOVIEDB_URL;

/**
 * Utility functions to handle JSON data from TheMovie.
 */

public class JSONParseUtils {

    public static URL buildURL(String theMovieDBQuery) {
        Uri builtURI = Uri.parse(THEMOVIEDB_URL).buildUpon()
                .appendPath(theMovieDBQuery)
                .appendQueryParameter(PARAM_API, API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtURI.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static JSONArray parseJSON(String JsonData) throws JSONException {

        /*All movies are part of key:results array.*/
        final String MDB_RESULTS = "results";

        return new JSONObject(JsonData).getJSONArray(MDB_RESULTS);
    }

}