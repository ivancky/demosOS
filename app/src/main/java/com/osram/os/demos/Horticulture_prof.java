package com.osram.os.demos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.neo.arcchartview.ArcChartView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class Horticulture_prof extends AppCompatActivity implements View.OnClickListener {
    FragmentPagerAdapter adapterViewPager;
    private Horti_FragmentGraph_prof mFragmentGraph;
    private Horti_FragmentPie mFragmentPie;
    private ArcChartView myArcChartView;
    private static OkHttpClient client;
    Handler handler;
    private Button btnReset;

    double graphArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double waveArray[] = {360,	362,	364,	366,	368,	370,	372,	374,	376,	378,	380,	382,	384,	386,	388,	390,	392,	394,	396,	398,	400,	402,	404,	406,	408,	410,	412,	414,	416,	418,	420,	422,	424,	426,	428,	430,	432,	434,	436,	438,	440,	442,	444,	446,	448,	450,	452,	454,	456,	458,	460,	462,	464,	466,	468,	470,	472,	474,	476,	478,	480,	482,	484,	486,	488,	490,	492,	494,	496,	498,	500,	502,	504,	506,	508,	510,	512,	514,	516,	518,	520,	522,	524,	526,	528,	530,	532,	534,	536,	538,	540,	542,	544,	546,	548,	550,	552,	554,	556,	558,	560,	562,	564,	566,	568,	570,	572,	574,	576,	578,	580,	582,	584,	586,	588,	590,	592,	594,	596,	598,	600,	602,	604,	606,	608,	610,	612,	614,	616,	618,	620,	622,	624,	626,	628,	630,	632,	634,	636,	638,	640,	642,	644,	646,	648,	650,	652,	654,	656,	658,	660,	662,	664,	666,	668,	670,	672,	674,	676,	678,	680,	682,	684,	686,	688,	690,	692,	694,	696,	698,	700,	702,	704,	706,	708,	710,	712,	714,	716,	718,	720,	722,	724,	726,	728,	730,	732,	734,	736,	738,	740,	742,	744,	746,	748,	750,	752,	754,	756,	758,	760,	762,	764,	766,	768,	770,	772,	774,	776,	778,	780,	782,	784,	786,	788,	790,	792,	794,	796,	798,	800,	802,	804,	806,	808,	810,	812,	814,	816,	818,	820,	822,	824,	826,	828,	830};

    double LimeArray[] = {0.00125,	0.00375,	0.004,	0.00125,	0.001,	0.002,	0.0005,	0.00275,	0.00375,	0.0025,	0.0045,	0.003,	0.001,	0.00325,	0.0025,	0.00325,	0.003,	0.00175,	0.0015,	0.004,	0.0045,	0.00575,	0.01,	0.012,	0.01425,	0.021,	0.0255,	0.0315,	0.0395,	0.04625,	0.055,	0.0645,	0.07275,	0.078,	0.08275,	0.087,	0.092,	0.1013,	0.1068,	0.1077,	0.1015,	0.087,	0.06875,	0.05375,	0.0395,	0.03125,	0.0255,	0.025,	0.023,	0.02,	0.01725,	0.01725,	0.01625,	0.01575,	0.0165,	0.018,	0.02125,	0.02325,	0.026,	0.03025,	0.03725,	0.04675,	0.058,	0.0725,	0.09275,	0.1173,	0.1435,	0.1782,	0.2157,	0.2582,	0.3053,	0.3547,	0.408,	0.465,	0.523,	0.5787,	0.6353,	0.6822,	0.7293,	0.774,	0.806,	0.8407,	0.865,	0.8918,	0.9133,	0.9285,	0.9398,	0.951,	0.9633,	0.9727,	0.978,	0.9868,	0.9932,	0.991,	0.996,	0.9895,	0.9863,	0.9835,	0.981,	0.9775,	0.9602,	0.948,	0.9395,	0.932,	0.923,	0.9087,	0.894,	0.8817,	0.8653,	0.8473,	0.829,	0.8115,	0.7912,	0.7765,	0.7593,	0.7403,	0.7223,	0.6997,	0.6745,	0.6525,	0.6322,	0.6163,	0.5995,	0.5785,	0.5625,	0.54,	0.5232,	0.5048,	0.4855,	0.468,	0.4482,	0.4328,	0.4145,	0.4,	0.383,	0.366,	0.3512,	0.34,	0.3222,	0.309,	0.296,	0.2817,	0.272,	0.26,	0.2483,	0.2365,	0.2277,	0.2167,	0.206,	0.1967,	0.1873,	0.1795,	0.1688,	0.1602,	0.1525,	0.1475,	0.14,	0.1327,	0.1285,	0.1223,	0.1163,	0.1103,	0.1057,	0.09975,	0.097,	0.09,	0.08375,	0.0815,	0.07825,	0.07375,	0.06925,	0.0665,	0.06375,	0.05975,	0.05825,	0.053,	0.05225,	0.05025,	0.04825,	0.04475,	0.04175,	0.03925,	0.039,	0.037,	0.0345,	0.03125,	0.02975,	0.0295,	0.02725,	0.02725,	0.02475,	0.0225,	0.023,	0.021,	0.01975,	0.01925,	0.017,	0.0165,	0.016,	0.01475,	0.015,	0.0135,	0.01425,	0.012,	0.013,	0.013,	0.0115,	0.01025,	0.0095,	0.0085,	0.00825,	0.008,	0.00775,	0.00725,	0.00675,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double FSW4000Array[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0.00025,	0.0005001,	0.00025,	0.0007501,	0.001,	0.001,	0.001,	0.001,	0.001,	0.001,	0.00125,	0.002,	0.002,	0.00275,	0.003251,	0.004001,	0.004751,	0.006001,	0.007251,	0.009002,	0.01125,	0.01425,	0.01725,	0.021,	0.026,	0.03151,	0.03901,	0.04726,	0.05776,	0.06901,	0.08276,	0.09827,	0.115,	0.136,	0.161,	0.1905,	0.2267,	0.2712,	0.3244,	0.3871,	0.4583,	0.5398,	0.6291,	0.7196,	0.8083,	0.892,	0.9612,	0.997,	0.9745,	0.8819,	0.7346,	0.5718,	0.4226,	0.3059,	0.2193,	0.1572,	0.1125,	0.08151,	0.05951,	0.04426,	0.03301,	0.02425,	0.019,	0.015,	0.01175,	0.009002,	0.007251,	0.006001,	0.004751,	0.004001,	0.003251,	0.003001,	0.00225,	0.002,	0.002,	0.002,	0.00125,	0.001,	0.001,	0.001,	0.001,	0.001,	0.001,	0.001,	0.001,	0.001,	0.00025,	0.0007501,	0.00025,	0,	0,	0.0005001,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0.0005001,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double FSW5000Array[] = {0.001501,	0.002751,	0.002751,	0.003752,	0.002001,	0.001751,	0.0005003,	0.0002501,	0.0005003,	0.0002501,	0,	0.0005003,	0,	0.0005003,	0.0002501,	0.0002501,	0,	0.0005003,	0,	0,	0,	0.0005003,	0.0007504,	0.0002501,	0,	0,	0,	0,	0,	0,	0,	0.0007504,	0.0007504,	0,	0.0002501,	0.0007504,	0,	0,	0,	0,	0,	0.0005003,	0,	0.0005003,	0,	0,	0,	0.0002501,	0.0002501,	0.0005003,	0,	0,	0.0007504,	0.0002501,	0.0002501,	0.0002501,	0.0002501,	0.0005003,	0.0005003,	0.001001,	0.0007504,	0.0005003,	0,	0.0002501,	0.0002501,	0.0005003,	0.0005003,	0.0002501,	0.001001,	0.001001,	0.001001,	0.0002501,	0.0005003,	0.0007504,	0.0002501,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0.0005003,	0.0002501,	0.0002501,	0.0002501,	0.0002501,	0.0002501,	0.0005003,	0.0002501,	0,	0,	0,	0,	0,	0,	0,	0.0002501,	0.0007504,	0,	0,	0.0002501,	0.001001,	0.0002501,	0,	0,	0.0005003,	0.0007504,	0.0002501,	0,	0,	0,	0,	0,	0,	0.0005003,	0.0007504,	0.0007504,	0,	0.0007504,	0.0007504,	0,	0.0005003,	0.001001,	0.002001,	0.001751,	0.001251,	0.002001,	0.002251,	0.002501,	0.003752,	0.004002,	0.004752,	0.005503,	0.006503,	0.007254,	0.009255,	0.01126,	0.01376,	0.01701,	0.01951,	0.02301,	0.02851,	0.03352,	0.03927,	0.04627,	0.05478,	0.06503,	0.07629,	0.0908,	0.1051,	0.1239,	0.1448,	0.1696,	0.1973,	0.2284,	0.2654,	0.3032,	0.3394,	0.3822,	0.4279,	0.4742,	0.5153,	0.5616,	0.6178,	0.6791,	0.7379,	0.7929,	0.8579,	0.9208,	0.9668,	0.9953,	0.9895,	0.9398,	0.8421,	0.7134,	0.5693,	0.4404,	0.3302,	0.2444,	0.1761,	0.1273,	0.0933,	0.06954,	0.05078,	0.03852,	0.03002,	0.02251,	0.01851,	0.01501,	0.01176,	0.008504,	0.007254,	0.006253,	0.004252,	0.004252,	0.004002,	0.003752,	0.003502,	0.003002,	0.002751,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double purpleArray[] = {0.001499,	0.002249,	0.001999,	0.001999,	0.001749,	0.001749,	0.001999,	0.001999,	0.002249,	0.002249,	0.001999,	0.001999,	0.002249,	0.002998,	0.002998,	0.003498,	0.003748,	0.003998,	0.004747,	0.005747,	0.006247,	0.007246,	0.008245,	0.009245,	0.01099,	0.01274,	0.01399,	0.01599,	0.01799,	0.02074,	0.02474,	0.03023,	0.03898,	0.05072,	0.06871,	0.0957,	0.1329,	0.1841,	0.2527,	0.339,	0.4438,	0.5672,	0.7031,	0.838,	0.9472,	0.9932,	0.9573,	0.844,	0.6908,	0.5445,	0.428,	0.3436,	0.2808,	0.2304,	0.1866,	0.1487,	0.1161,	0.09095,	0.07146,	0.05747,	0.04697,	0.03823,	0.03123,	0.02499,	0.01999,	0.01674,	0.01324,	0.01099,	0.008995,	0.007246,	0.006746,	0.005747,	0.004747,	0.003998,	0.003748,	0.002998,	0.002998,	0.002748,	0.001999,	0.001999,	0.001999,	0.001999,	0.001749,	0.0009994,	0.0009994,	0.0009994,	0.0009994,	0.0009994,	0.0009994,	0.0009994,	0.0009994,	0.0009994,	0.0009994,	0.0009994,	0.0009994,	0.0007496,	0.0002499,	0.0009994,	0.0009994,	0.0007496,	0.0004997,	0.0002499,	0.0007496,	0.0002499,	0.0009994,	0.0009994,	0.0007496,	0.0004997,	0.0007496,	0.0009994,	0.0007496,	0.0004997,	0.0007496,	0.0007496,	0,	0.0002499,	0.0004997,	0.0002499,	0,	0,	0.0004997,	0,	0,	0.0004997,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0.0004997,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};

    int FSW4000i = 0, FSW5000i =0 , Limei = 0, purplei = 0;

    // PPF calculation
    double PPF = 0, BPF = 0;
    double PPFconst = 8.359 * 0.001;
    int PPFrangemin = 20, PPFrangemax = 170;
    int BPFrangemin = 0, BPFrangemax = 235;
    double bluePPF, greenPPF, redPPF, red2PPF;
    int bluePPFrangemin = 20, getBluePPFrangemax = 70, redPPFrangemin = 121, redPPFrangemax = 170, greenPPFrangemin = 71, greenPPFrangemax = 120, red2PPFrangemin = 171, red2PPFrangemax = 234;
    int maxcurrent = 100; //100mA

    GraphView graph;
    LineGraphSeries<DataPoint> series;       //an Object of the PointsGraphSeries for plotting scatter graphs


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horticulture_prof);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(0);
        handler= new Handler(Looper.getMainLooper());
        client = new OkHttpClient();

        myArcChartView = findViewById(R.id.arc_chart_view_horti);
        myArcChartView.setSectionValue(0, 0);
        myArcChartView.setSectionValue(1, 0);
        myArcChartView.setSectionValue(2, 0);
        myArcChartView.setSectionValue(3, 0);

        /** set colors */
        myArcChartView.setFilldeColor(0, this.getResources().getColor(R.color.colorDeepBlue));
        myArcChartView.setUnFilldeColor(0, this.getResources().getColor(R.color.colorDeepBlue_));
        myArcChartView.setFilldeColor(1, this.getResources().getColor(R.color.colorMint));
        myArcChartView.setUnFilldeColor(1, this.getResources().getColor(R.color.colorEQWhite_));
        myArcChartView.setFilldeColor(2, this.getResources().getColor(R.color.colorHyperRed));
        myArcChartView.setUnFilldeColor(2,this.getResources().getColor(R.color.colorHyperRed_));
        myArcChartView.setFilldeColor(3, this.getResources().getColor(R.color.colorFarRed2));
        myArcChartView.setUnFilldeColor(3, this.getResources().getColor(R.color.colorFarRed2_));

        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);

        myArcChartView.setListener(new ArcChartView.AcvListener() {
            @Override
            public void onSectionsIconClicked(int i) {
//                Toast.makeText(getApplication().getBaseContext(), Integer.toString(i),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStartSettingSectionValue(int i, int i1) {
//                Toast.makeText(getApplication().getBaseContext(), Integer.toString(i1),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onContinueSettingSectionValue(int i, int i1) {

            }
            @Override
            public void onFinishedSettingSectionValue(int i, int i1) {
                switch(i) {
                    case 0:
                        purplei = i1 * 128;
                        updateGraph();
                        Horti_FragmentGraph_prof fragment = (Horti_FragmentGraph_prof) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                        fragment.updateGraph(0, purplei);
                        sendMessage("!" + Integer.toString(i1));
                        break;
                    case 1:
                        Limei = i1 * 128;
                        updateGraph();
                        Horti_FragmentGraph_prof fragment2 = (Horti_FragmentGraph_prof) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                        fragment2.updateGraph(1, Limei);
                        sendMessage("@" + Integer.toString(i1));
                        break;
                    case 2:
                        FSW4000i = i1 * 128;
                        updateGraph();
                        Horti_FragmentGraph_prof fragment3 = (Horti_FragmentGraph_prof) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                        fragment3.updateGraph(2, FSW4000i);
                        sendMessage("#" + Integer.toString(i1));
                        break;
                    case 3:
                        FSW5000i = i1 * 128;
                        updateGraph();
                        Horti_FragmentGraph_prof fragment4 = (Horti_FragmentGraph_prof) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                        fragment4.updateGraph(3, FSW5000i);
                        sendMessage("$" + Integer.toString(i1));
                        break;
                    default:
                        break;
                }
            }
        });
    }

public void updateGraph() {
    handler.post(new Runnable() {
        public void run() {
            for (int z = 0; z < 235; z++) {
                graphArray[z] = (FSW4000Array[z] * FSW4000i) + (FSW5000Array[z] * FSW5000i) + (LimeArray[z] * Limei) + (purpleArray[z] * purplei);
            }
            for (int y = bluePPFrangemin; y < getBluePPFrangemax; y++){
                bluePPF = (PPFconst * graphArray[y] * waveArray [y]) + bluePPF;
            }
            for (int y = greenPPFrangemin; y < greenPPFrangemax; y++){
                greenPPF = (PPFconst * graphArray[y] * waveArray [y]) + greenPPF;
            }
            for (int y = redPPFrangemin; y < redPPFrangemax; y++){
                redPPF = (PPFconst * graphArray[y] * waveArray [y]) + redPPF;
            }
            for (int y = red2PPFrangemin; y < red2PPFrangemax; y++){
                red2PPF = (PPFconst * graphArray[y] * waveArray [y]) + red2PPF;
            }
            bluePPF = bluePPF * 100;
            greenPPF = greenPPF * 100;
            redPPF = redPPF * 100;
            red2PPF = red2PPF * 100;

            Horti_FragmentPie fragment1 = (Horti_FragmentPie) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":1");
            fragment1.updatePie((int)bluePPF, (int)greenPPF, (int)redPPF, (int)red2PPF);
            bluePPF = 0;
            redPPF = 0;
            greenPPF = 0;
            red2PPF = 0;
        }
    });
}

    public void onBackPressed(){
        Intent intent = new Intent(Horticulture_prof.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) { // reset all
        handler.post(new Runnable() {
            public void run() {
                /** Set everything back to 0 **/
                handler.removeCallbacksAndMessages(null);
                myArcChartView.setSectionValue(0, 0);
                myArcChartView.setSectionValue(1, 0);
                myArcChartView.setSectionValue(2, 0);
                myArcChartView.setSectionValue(3, 0);

                sendMessage("!0");
                sendMessage("@0");
                sendMessage("#0");
                sendMessage("$0");

//                sendMessage("-"); // send reset command to ESP

                Horti_FragmentPie fragment1 = (Horti_FragmentPie) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpPager + ":1");
                fragment1.updatePie(0, 0, 0, 0);
                Horti_FragmentGraph_prof fragment = (Horti_FragmentGraph_prof) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpPager + ":0");
                fragment.updateGraph(0, 0);
                fragment.updateGraph(1, 0);
                fragment.updateGraph(2, 0);
                fragment.updateGraph(3, 0);
                fragment.updateGraph(4, 0);
            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        // other code in your custom FragmentPagerAdapter...
        private int NUM_ITEMS = 2;
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            // Do NOT try to save references to the Fragments in getItem(),
            // because getItem() is not always called. If the Fragment
            // was already created then it will be retrieved from the FragmentManger
            // and not here (i.e. getItem() won't be called again).
            switch (position) {
                case 0:
                    return new Horti_FragmentGraph_prof();
                case 1:
                    return new Horti_FragmentPie();
                default:
                    // This should never happen. Always account for each position above
                    return null;
            }
        }

        // Here we can finally safely save a reference to the created
        // Fragment, no matter where it came from (either getItem() or
        // FragmentManger). Simply save the returned Fragment from
        // super.instantiateItem() into an appropriate reference depending
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            switch (position) {
                case 0:
                    mFragmentGraph = (Horti_FragmentGraph_prof) createdFragment;
                    break;
                case 1:
                    mFragmentPie = (Horti_FragmentPie) createdFragment;
                    break;
            }
            return createdFragment;
        }
    }

    public static final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {
//            output("Receiving : " + text);
        }
        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
//            output("Receiving bytes : " + bytes.hex());
        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
        }
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
//            output("Error : " + t.getMessage());
        }
    }

    public static void sendMessage(String msg){
        Request request = new Request.Builder().url("ws://192.168.1.1:81/").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        ws.send(msg);
    }
}


