package com.example.android.popularmoviess1;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

/*Image adapter to populate movie posters in gridView */
class ImageAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private JSONArray mData;
    private int posters_xml;

    ImageAdapter(Context context, int resource, JSONArray data) {
        super(context, resource);
        mContext = context;
        posters_xml = resource;
        mData = data;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(posters_xml, parent, false);
        }

        imageView = (ImageView) convertView.findViewById(R.id.list_poster_image);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = getContext();
                Class destination = DetailActivity.class;
                Intent startDetailActivityIntent = new Intent(context, destination);
                try {
                    startDetailActivityIntent
                            .putExtra(Intent.EXTRA_TEXT, mData.getJSONObject(position).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                context.startActivity(startDetailActivityIntent);
            }
        });

        //Picasso library used to manage images from URL in main activity
        try {
            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185//"
                    + mData.getJSONObject(position).getString("poster_path"))
                    .resize(350, 450).into(imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return imageView;
    }

    public long getItemId(int itemID) {
        return 0;
    }

    @Override
    public String getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mData.length();
    }

}
