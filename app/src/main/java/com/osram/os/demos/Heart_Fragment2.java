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

import static java.sql.Types.NULL;


public class Heart_Fragment2 extends Fragment {
    Handler handler;
    TextView setCCT;
    int num = 0;
    int bpm = 0;
    float avg = 0;

    int[] IntegerArray = new int [5];

    // newInstance constructor for creating fragment with arguments
    public static Heart_Fragment2 newInstance() {
        Heart_Fragment2 fragment2 = new Heart_Fragment2();
        return fragment2;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler= new Handler(Looper.getMainLooper());
//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_heart__fragment2, container, false);
//                TextView tvStress = view.findViewById(R.id.tvStress);
        setCCT = (TextView)view.findViewById(R.id.tvCCT);

//        tvLabel.setText(page + " -- " + title);
//        TextView tvStress =  view.findViewById(R.id.tvStress);
        return view;
    }

    public void setCCT(final int tempRate) {
        handler.post(new Runnable() {
            public void run() {
                IntegerArray[num] = tempRate;
                num = num + 1;
                if(num == 5) { // 5 values moving average
                    for (int j = 0; j < 5; j++) {
                        bpm = IntegerArray[j] + bpm;
                    }
                    avg = bpm/5;
                    num = 0;
                    bpm = 0;
                }
                int step = 50;
                int CCT;
                double red, green, blue;
                if (avg < 50){
                }else {
//                CCT = (180 - tempRate) * step;
                    CCT = (180 - (int) avg) * step;
                    setCCT.setText(CCT + " K");
                    double tmpCCT = CCT / 100;
                    red = 255;
                    green = 99.47 * Math.log(tmpCCT) - 161.12;
                    if (green < 0) {
                        green = 0;
                    }
                    if (green > 255) {
                        green = 255;
                    }
                    blue = tmpCCT - 10;
                    blue = 138.52 * Math.log(blue) - 305.05;
                    if (blue < 0) {
                        blue = 0;
                    }
                    if (blue > 255) {
                        blue = 255;
                    }
                    getView().setBackgroundColor(Color.rgb((int) red, (int) green, (int) blue));
                    Log.d("Frag2", String.valueOf((int) red));
                    Log.d("Frag2", String.valueOf((int) green));
                    Log.d("Frag2", String.valueOf((int) blue));
                }
                //http://www.tannerhelland.com/4435/convert-temperature-rgb-algorithm-code/


//                red = 255;
//                green = 200 + (((float)CCT/6500) * 55);
//                blue = 90 + (((float)CCT/6500) * 165);
//                getView().setBackgroundColor(Color.rgb((int)red,(int) green,(int) blue));
//                Log.d("Frag2", String.valueOf((int)red));
//                Log.d("Frag2", String.valueOf((int)green));
//                Log.d("Frag2", String.valueOf((int)blue));
//            int CCT;
//            CCT = tempRate * step;
//            tvCCT.setText(CCT + " K");
//            if (tempRate < 70) {
//                tvStress.setText("Low");
//                tvStress.setTextColor(ContextCompat.getColor(HeartRate.this, R.color.colorGreen));
//            } else if (tempRate > 69 && tempRate < 90) {
//                tvStress.setText("Normal");
//                tvStress.setTextColor(ContextCompat.getColor(HeartRate.this, R.color.colorBlack));
//            } else {
//                tvStress.setText("High");
//                tvStress.setTextColor(ContextCompat.getColor(HeartRate.this, R.color.colorRed));
//            }
            }
        });
    }

}
