package com.osram.os.demos;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 34945 on 2/6/2018.
 */

public class GridViewAdapter extends BaseAdapter {

    private final Context mContext;
    private final Demos[] demos;

    // 1
    public GridViewAdapter(Context context, Demos[] demos) {
        this.mContext = context;
        this.demos = demos;
    }

    // 2
    @Override
    public int getCount() {
        return demos.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Demos book = demos[position];

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.grid_view, null);
        }

        // 3
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_book_name);

        // 4
        imageView.setImageResource(book.getImageResource());
        nameTextView.setText(mContext.getString(book.getName()));

        return convertView;
    }

}