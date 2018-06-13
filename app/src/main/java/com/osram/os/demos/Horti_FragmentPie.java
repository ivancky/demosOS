package com.osram.os.demos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.github.mikephil.charting.data.Entry;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;


public class Horti_FragmentPie extends Fragment {
    double graphArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double waveArray[] = {360,	362,	364,	366,	368,	370,	372,	374,	376,	378,	380,	382,	384,	386,	388,	390,	392,	394,	396,	398,	400,	402,	404,	406,	408,	410,	412,	414,	416,	418,	420,	422,	424,	426,	428,	430,	432,	434,	436,	438,	440,	442,	444,	446,	448,	450,	452,	454,	456,	458,	460,	462,	464,	466,	468,	470,	472,	474,	476,	478,	480,	482,	484,	486,	488,	490,	492,	494,	496,	498,	500,	502,	504,	506,	508,	510,	512,	514,	516,	518,	520,	522,	524,	526,	528,	530,	532,	534,	536,	538,	540,	542,	544,	546,	548,	550,	552,	554,	556,	558,	560,	562,	564,	566,	568,	570,	572,	574,	576,	578,	580,	582,	584,	586,	588,	590,	592,	594,	596,	598,	600,	602,	604,	606,	608,	610,	612,	614,	616,	618,	620,	622,	624,	626,	628,	630,	632,	634,	636,	638,	640,	642,	644,	646,	648,	650,	652,	654,	656,	658,	660,	662,	664,	666,	668,	670,	672,	674,	676,	678,	680,	682,	684,	686,	688,	690,	692,	694,	696,	698,	700,	702,	704,	706,	708,	710,	712,	714,	716,	718,	720,	722,	724,	726,	728,	730,	732,	734,	736,	738,	740,	742,	744,	746,	748,	750,	752,	754,	756,	758,	760,	762,	764,	766,	768,	770,	772,	774,	776,	778,	780,	782,	784,	786,	788,	790,	792,	794,	796,	798,	800,	802,	804,	806,	808,	810,	812,	814,	816,	818,	820,	822,	824,	826,	828,	830};

    Handler handler;
    PieChart pieChart ;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;

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
        View view = inflater.inflate(R.layout.fragment_horti__fragment_pie, container, false);
         pieChart = (PieChart) view.findViewById(R.id.piechart);
         entries = new ArrayList<>();
         PieEntryLabels = new ArrayList<String>();
        return view;
    }

    public DataPoint[] data(){
        int n = 235;
        DataPoint[] values = new DataPoint[n];     //creating an object of type DataPoint[] of size 'n'
        for(int i = 0; i < n; i++){
            DataPoint v = new DataPoint(waveArray[i], graphArray[i]);
            values[i] = v;
        }
        return values;
    }

    public void updatePie(final int bluePPF, final int greenPPF, final int redPPF, final int red2PPF) {
        handler.post(new Runnable() {
            public void run() {
                entries.clear();
                PieEntryLabels.clear();
                AddValuesToPIEENTRY(bluePPF, greenPPF, redPPF, red2PPF);
//                AddValuesToPieEntryLabels(bluePPF, greenPPF, redPPF);
                pieDataSet = new PieDataSet(entries, "");
                pieData = new PieData(PieEntryLabels, pieDataSet);
                pieData.setValueTextSize(11f);
                pieData.setValueFormatter(new PercentFormatter());
                pieChart.setDescription("% by PPF contribution");
                pieChart.setHoleRadius(60);
                pieChart.setCenterText("PPF %");
                pieChart.setCenterTextSize(23);
                pieChart.setTransparentCircleRadius(0);
                pieChart.setUsePercentValues(true);
                pieChart.setRotationEnabled(false);
                pieChart.setDrawSliceText(false);
                if(bluePPF > 0 && greenPPF == 0 && redPPF == 0 && red2PPF == 0){
                    pieDataSet.setColors(new int[] {R.color.colorLightBlue}, getContext());
                }
                else if(bluePPF == 0 && greenPPF > 0 && redPPF == 0 && red2PPF == 0){
                    pieDataSet.setColors(new int[] {R.color.colorLightGreen2}, getContext());
                }
                else if(bluePPF == 0 && greenPPF == 0 && redPPF > 0 && red2PPF == 0){
                    pieDataSet.setColors(new int[] {R.color.colorAccent2}, getContext());
                }
                else if(bluePPF == 0 && greenPPF == 0 && redPPF == 0 && red2PPF > 0){
                    pieDataSet.setColors(new int[] {R.color.colorFarRed}, getContext());
                }
                else if(bluePPF > 0 && greenPPF > 0 && redPPF == 0 && red2PPF == 0){
                    pieDataSet.setColors(new int[] {R.color.colorLightBlue, R.color.colorLightGreen2}, getContext());
                }
                else if(bluePPF > 0 && greenPPF == 0 && redPPF > 0 && red2PPF == 0){
                    pieDataSet.setColors(new int[] {R.color.colorLightBlue, R.color.colorAccent2}, getContext());
                }
                else if(bluePPF > 0 && greenPPF == 0 && redPPF == 0 && red2PPF > 0){
                    pieDataSet.setColors(new int[] {R.color.colorLightBlue, R.color.colorFarRed}, getContext());
                }
                else if(bluePPF == 0 && greenPPF > 0 && redPPF > 0 && red2PPF == 0){
                    pieDataSet.setColors(new int[] {R.color.colorLightGreen2, R.color.colorAccent2}, getContext());
                }
                else if(bluePPF == 0 && greenPPF > 0 && redPPF == 0 && red2PPF > 0){
                    pieDataSet.setColors(new int[] {R.color.colorLightGreen2, R.color.colorFarRed}, getContext());
                }
                else if(bluePPF == 0 && greenPPF == 0 && redPPF > 0 && red2PPF > 0){
                    pieDataSet.setColors(new int[] {R.color.colorAccent2, R.color.colorFarRed}, getContext());
                }
                else if(bluePPF > 0 && greenPPF > 0 && redPPF > 0 && red2PPF == 0){
                    pieDataSet.setColors(new int[] {R.color.colorLightBlue, R.color.colorLightGreen2, R.color.colorAccent2}, getContext());
                }
                else if(bluePPF > 0 && greenPPF > 0 && redPPF == 0 && red2PPF > 0){
                    pieDataSet.setColors(new int[] {R.color.colorLightBlue, R.color.colorLightGreen2, R.color.colorFarRed}, getContext());
                }
                else if(bluePPF > 0 && greenPPF == 0 && redPPF > 0 && red2PPF > 0){
                    pieDataSet.setColors(new int[] {R.color.colorLightBlue, R.color.colorAccent2, R.color.colorFarRed}, getContext());
                }
                else if(bluePPF == 0 && greenPPF > 0 && redPPF > 0 && red2PPF > 0){
                    pieDataSet.setColors(new int[] {R.color.colorLightGreen2, R.color.colorAccent2, R.color.colorFarRed}, getContext());
                }
                else if(bluePPF > 0 && greenPPF > 0 && redPPF > 0 && red2PPF > 0){
                    pieDataSet.setColors(new int[] {R.color.colorLightBlue, R.color.colorLightGreen2, R.color.colorAccent2, R.color.colorFarRed}, getContext());
                }
                pieChart.setData(pieData);
                pieChart.getLegend().setEnabled(true);
                pieChart.animateY(500, Easing.EasingOption.EaseInOutQuad);}
        });
    }

    public void AddValuesToPIEENTRY(final int bluePPF, final int greenPPF, final int redPPF, final int red2PPF){
        if(bluePPF > 0) {
            entries.add(new BarEntry(bluePPF, 0));
            PieEntryLabels.add("400 - 500 nm");
        }
        if(greenPPF > 0) {
            entries.add(new BarEntry(greenPPF, 1));
            PieEntryLabels.add("500 - 600 nm");
        }
        if(redPPF > 0) {
            entries.add(new BarEntry(redPPF, 2));
            PieEntryLabels.add("600 - 700 nm");
        }
        if(red2PPF > 0) {
            entries.add(new BarEntry(red2PPF, 3));
            PieEntryLabels.add("700 - 800 nm");
        }
    }

    public void AddValuesToPieEntryLabels(final int bluePPF, final int greenPPF, final int redPPF){
        if(bluePPF > 0) {
            PieEntryLabels.add("400 - 500 nm");
        }
        if(greenPPF > 0) {
            PieEntryLabels.add("500 - 600 nm");
        }
        if(redPPF > 0) {
            PieEntryLabels.add("600 - 700 nm");
        }
    }
}