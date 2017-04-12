package com.example.android.popularmoviess1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    public JSONObject mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // View setup
        TextView mTitle = (TextView) findViewById(R.id.movie_title);
        TextView mDescription = (TextView) findViewById(R.id.description);
        TextView mRating = (TextView) findViewById(R.id.rating);
        TextView mRelease = (TextView) findViewById(R.id.release_date);
        ImageView mMoviePoster = (ImageView) findViewById(R.id.detail_movie_poster);

        // Get movie from Intent and assign to views
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                try {
                    mData = new JSONObject(intentThatStartedThisActivity
                            .getStringExtra(Intent.EXTRA_TEXT));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Picasso.with(DetailActivity.this)
                        .load("http://image.tmdb.org/t/p/w154//" + mData.optString("poster_path"))
                        .resize(450, 650).into(mMoviePoster);
                mRating.setText(getString(R.string.top_rating, mData.optString("vote_average")));
                mTitle.setText(mData.optString("original_title"));
                mDescription.setText(mData.optString("overview"));
                mRelease.setText(mData.optString("release_date"));

            }
        }
    }
}