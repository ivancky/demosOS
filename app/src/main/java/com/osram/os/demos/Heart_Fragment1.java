package com.osram.os.demos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Heart_Fragment1 extends Fragment {

    Handler handler;
    TextView setStressLevel;


    // newInstance constructor for creating fragment with arguments
    public static Heart_Fragment1 newInstance() {
        Heart_Fragment1 myFragment = new Heart_Fragment1();
        return myFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
        handler= new Handler(Looper.getMainLooper());

//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            int data = bundle.getInt("key");
//        }

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_heart__fragment1, container, false);
        setStressLevel = (TextView)view.findViewById(R.id.tvStress);
        return view;
    }

    public void setStress(final int tempRate) {
    handler.post(new Runnable() {
        public void run() {
            if (tempRate < 70){
                setStressLevel.setText("Low");
                getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorLightGreen));
            }else if (tempRate > 69 && tempRate < 100) {
                setStressLevel.setText("Normal");
                getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorLightGrey));
            } else {
                setStressLevel.setText("High");
                getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorLightRed));
            }
//            int step = 42;
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
