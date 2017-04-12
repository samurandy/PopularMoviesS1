package com.example.android.popularmoviess1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String THEMOVIEDB_URL = "https://api.themoviedb.org/3/movie";
    public static final String PARAM_API = "api_key";
    public static final String API_KEY = "";

    //Creation of Grid View
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.grid);

        if (isOnline()) {
            APIRequest("popular");
        } else {
            Toast.makeText(MainActivity.this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
        }
    }


    /*This method builds an url and starts api call in background thread*/
    public void APIRequest(String queryString) {
        URL searchUrl = JSONParseUtils.buildURL(queryString);
        new MovieQueryAsyncTask().execute(searchUrl);
    }

    /*This method checks if there is connection to internet*/
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }

    /**{@link AsyncTask} to perform the network request on a background thread and
     *it is shown on screen using imageadapater.
     */
    private class MovieQueryAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL mURL = params[0];
            String JSONResponse = null;

            try {
                JSONResponse = NetUtils.makeHTTPRequest(mURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return JSONResponse;
        }

        @Override
        protected void onPostExecute(String returnedData) {
            try {
                JSONArray parseData = JSONParseUtils.parseJSON(returnedData);
                ImageAdapter imageAdapter = new ImageAdapter(MainActivity.this,
                        R.layout.main_activity_screen, parseData);
                mGridView.setAdapter(imageAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /*Menu with sort options*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isOnline()) {
            APIRequest(item.getTitle().toString());
            return true;
        } else {
            Toast.makeText(MainActivity.this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
