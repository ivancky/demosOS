package com.osram.os.demos;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.series.DataPoint;
import com.yc.peddemo.sdk.RateChangeListener;

import java.util.ArrayList;

import static java.sql.Types.NULL;


public class Heart_Fragment3 extends Fragment {
    Handler handler;
    TextView tvMin, tvMax, tvAvg;
    int min = 200;
    int max = 0;
    int num = 0;
    int bpm = 0;
    float avg = 0;

    int[] IntegerArray = new int [5];

    // newInstance constructor for creating fragment with arguments
    public static Heart_Fragment3 newInstance(int page, String title) {
        Heart_Fragment3 fragment3 = new Heart_Fragment3();
        return fragment3;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler= new Handler(Looper.getMainLooper());
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_heart__fragment3, container, false);

        tvMin = (TextView)view.findViewById(R.id.tv_lowest_rate);
        tvMax = (TextView)view.findViewById(R.id.tv_highest_rate);
        tvAvg = (TextView)view.findViewById(R.id.tv_verage_rate);
        return view;
    }

    public void setMinMax(final int tempRate) {
        handler.post(new Runnable() {
            public void run() {
                IntegerArray[num] = tempRate;
                num = num + 1;
                if(num == 5) { // 5 values moving average
                    for (int j = 0; j < 5; j++) {
                        bpm = IntegerArray[j] + bpm;
                    }
                    avg = bpm/5;
                    tvAvg.setText(Integer.toString((int)avg));
                    num = 0;
                    bpm = 0;
                }

            if(tempRate > max){max = tempRate;}
            if(tempRate < min){min = tempRate;}
            tvMin.setText(Integer.toString(min));
            tvMax.setText(Integer.toString(max));
            }
        });
    }

}