package com.osram.os.demos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import static com.osram.os.demos.Horticulture_recipe_consumer.sendMessage;


public class Horti_recipe_fragment_growth_consumer extends Fragment implements View.OnClickListener{
    Handler handler;

    TextView tvPPFD, tvRB, tvEfficacy, tvApplications, tvPower, tvDescription, tvRecipe;
//    String PPFD, RB, Efficacy, Applications, Power;
    double graphArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double waveArray[] = {360,	362,	364,	366,	368,	370,	372,	374,	376,	378,	380,	382,	384,	386,	388,	390,	392,	394,	396,	398,	400,	402,	404,	406,	408,	410,	412,	414,	416,	418,	420,	422,	424,	426,	428,	430,	432,	434,	436,	438,	440,	442,	444,	446,	448,	450,	452,	454,	456,	458,	460,	462,	464,	466,	468,	470,	472,	474,	476,	478,	480,	482,	484,	486,	488,	490,	492,	494,	496,	498,	500,	502,	504,	506,	508,	510,	512,	514,	516,	518,	520,	522,	524,	526,	528,	530,	532,	534,	536,	538,	540,	542,	544,	546,	548,	550,	552,	554,	556,	558,	560,	562,	564,	566,	568,	570,	572,	574,	576,	578,	580,	582,	584,	586,	588,	590,	592,	594,	596,	598,	600,	602,	604,	606,	608,	610,	612,	614,	616,	618,	620,	622,	624,	626,	628,	630,	632,	634,	636,	638,	640,	642,	644,	646,	648,	650,	652,	654,	656,	658,	660,	662,	664,	666,	668,	670,	672,	674,	676,	678,	680,	682,	684,	686,	688,	690,	692,	694,	696,	698,	700,	702,	704,	706,	708,	710,	712,	714,	716,	718,	720,	722,	724,	726,	728,	730,	732,	734,	736,	738,	740,	742,	744,	746,	748,	750,	752,	754,	756,	758,	760,	762,	764,	766,	768,	770,	772,	774,	776,	778,	780,	782,	784,	786,	788,	790,	792,	794,	796,	798,	800,	802,	804,	806,	808,	810,	812,	814,	816,	818,	820,	822,	824,	826,	828,	830};
    double LimeArray[] = {0.00125,	0.00375,	0.004,	0.00125,	0.001,	0.002,	0.0005,	0.00275,	0.00375,	0.0025,	0.0045,	0.003,	0.001,	0.00325,	0.0025,	0.00325,	0.003,	0.00175,	0.0015,	0.004,	0.0045,	0.00575,	0.01,	0.012,	0.01425,	0.021,	0.0255,	0.0315,	0.0395,	0.04625,	0.055,	0.0645,	0.07275,	0.078,	0.08275,	0.087,	0.092,	0.1013,	0.1068,	0.1077,	0.1015,	0.087,	0.06875,	0.05375,	0.0395,	0.03125,	0.0255,	0.025,	0.023,	0.02,	0.01725,	0.01725,	0.01625,	0.01575,	0.0165,	0.018,	0.02125,	0.02325,	0.026,	0.03025,	0.03725,	0.04675,	0.058,	0.0725,	0.09275,	0.1173,	0.1435,	0.1782,	0.2157,	0.2582,	0.3053,	0.3547,	0.408,	0.465,	0.523,	0.5787,	0.6353,	0.6822,	0.7293,	0.774,	0.806,	0.8407,	0.865,	0.8918,	0.9133,	0.9285,	0.9398,	0.951,	0.9633,	0.9727,	0.978,	0.9868,	0.9932,	0.991,	0.996,	0.9895,	0.9863,	0.9835,	0.981,	0.9775,	0.9602,	0.948,	0.9395,	0.932,	0.923,	0.9087,	0.894,	0.8817,	0.8653,	0.8473,	0.829,	0.8115,	0.7912,	0.7765,	0.7593,	0.7403,	0.7223,	0.6997,	0.6745,	0.6525,	0.6322,	0.6163,	0.5995,	0.5785,	0.5625,	0.54,	0.5232,	0.5048,	0.4855,	0.468,	0.4482,	0.4328,	0.4145,	0.4,	0.383,	0.366,	0.3512,	0.34,	0.3222,	0.309,	0.296,	0.2817,	0.272,	0.26,	0.2483,	0.2365,	0.2277,	0.2167,	0.206,	0.1967,	0.1873,	0.1795,	0.1688,	0.1602,	0.1525,	0.1475,	0.14,	0.1327,	0.1285,	0.1223,	0.1163,	0.1103,	0.1057,	0.09975,	0.097,	0.09,	0.08375,	0.0815,	0.07825,	0.07375,	0.06925,	0.0665,	0.06375,	0.05975,	0.05825,	0.053,	0.05225,	0.05025,	0.04825,	0.04475,	0.04175,	0.03925,	0.039,	0.037,	0.0345,	0.03125,	0.02975,	0.0295,	0.02725,	0.02725,	0.02475,	0.0225,	0.023,	0.021,	0.01975,	0.01925,	0.017,	0.0165,	0.016,	0.01475,	0.015,	0.0135,	0.01425,	0.012,	0.013,	0.013,	0.0115,	0.01025,	0.0095,	0.0085,	0.00825,	0.008,	0.00775,	0.00725,	0.00675,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double FSW4000Array[] = {0.00225,	0.004,	0.0045,	0.00525,	0.004,	0.0045,	0.005,	0.004,	0.0035,	0.004,	0.00425,	0.004,	0.004,	0.004,	0.004,	0.0035,	0.004,	0.004,	0.00475,	0.0055,	0.00525,	0.00625,	0.008,	0.00975,	0.01125,	0.01425,	0.0185,	0.02375,	0.03075,	0.0395,	0.05325,	0.06825,	0.09,	0.1155,	0.1493,	0.1935,	0.249,	0.3227,	0.4193,	0.5405,	0.679,	0.8228,	0.933,	0.9942,	0.973,	0.89,	0.775,	0.6737,	0.5985,	0.548,	0.5102,	0.4738,	0.4362,	0.393,	0.3567,	0.3313,	0.3195,	0.3153,	0.318,	0.3258,	0.335,	0.346,	0.3592,	0.3735,	0.3907,	0.4105,	0.429,	0.4482,	0.466,	0.4843,	0.499,	0.5177,	0.5338,	0.5475,	0.562,	0.577,	0.5902,	0.6043,	0.6192,	0.633,	0.6448,	0.6572,	0.6672,	0.6778,	0.6905,	0.6978,	0.7083,	0.7157,	0.7215,	0.7293,	0.735,	0.7395,	0.7402,	0.7413,	0.7418,	0.7442,	0.7423,	0.7403,	0.7405,	0.7353,	0.7308,	0.73,	0.7273,	0.723,	0.7198,	0.7165,	0.714,	0.712,	0.7135,	0.7112,	0.7092,	0.7105,	0.7117,	0.7135,	0.7168,	0.7217,	0.725,	0.7293,	0.7332,	0.742,	0.7465,	0.7533,	0.765,	0.7748,	0.781,	0.7895,	0.7985,	0.8065,	0.8122,	0.8173,	0.822,	0.8263,	0.8295,	0.832,	0.8327,	0.832,	0.8272,	0.8255,	0.8198,	0.814,	0.807,	0.7983,	0.7873,	0.7748,	0.766,	0.7525,	0.7368,	0.721,	0.7055,	0.6873,	0.6672,	0.6512,	0.631,	0.6107,	0.5935,	0.5742,	0.557,	0.538,	0.5185,	0.4985,	0.4788,	0.461,	0.4408,	0.4245,	0.4063,	0.3885,	0.3705,	0.3525,	0.3363,	0.3215,	0.3052,	0.29,	0.2758,	0.2625,	0.2502,	0.2362,	0.2228,	0.2117,	0.2002,	0.1902,	0.179,	0.1697,	0.1595,	0.1502,	0.1425,	0.1337,	0.1275,	0.1192,	0.1128,	0.1063,	0.1005,	0.09375,	0.0885,	0.0825,	0.07675,	0.072,	0.069,	0.0645,	0.06,	0.057,	0.054,	0.05025,	0.04775,	0.0445,	0.0415,	0.0395,	0.03725,	0.03525,	0.0325,	0.031,	0.0295,	0.0275,	0.02575,	0.024,	0.02275,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double FSW5000Array[] = {0.0025,	0.004,	0.00425,	0.00425,	0.0045,	0.0035,	0.004,	0.00475,	0.004,	0.003,	0.00375,	0.00375,	0.00325,	0.004,	0.0035,	0.004,	0.004,	0.004,	0.00425,	0.005,	0.005,	0.0055,	0.006,	0.00725,	0.00825,	0.01,	0.01275,	0.016,	0.02025,	0.0265,	0.036,	0.0465,	0.061,	0.08025,	0.1058,	0.1387,	0.1808,	0.235,	0.309,	0.4063,	0.5275,	0.671,	0.8128,	0.9332,	0.9932,	0.9698,	0.883,	0.771,	0.6667,	0.596,	0.5443,	0.507,	0.4705,	0.4292,	0.3858,	0.3478,	0.321,	0.3072,	0.3022,	0.3018,	0.3057,	0.309,	0.3153,	0.3243,	0.333,	0.344,	0.3567,	0.37,	0.3842,	0.3948,	0.409,	0.4185,	0.4305,	0.4397,	0.4505,	0.4605,	0.4708,	0.4795,	0.4883,	0.498,	0.507,	0.512,	0.5213,	0.5288,	0.5345,	0.5395,	0.5455,	0.5495,	0.5517,	0.5555,	0.556,	0.5573,	0.5597,	0.5575,	0.5567,	0.5573,	0.554,	0.5492,	0.5463,	0.5425,	0.5375,	0.5328,	0.5293,	0.5237,	0.5183,	0.5152,	0.511,	0.5075,	0.5017,	0.4995,	0.4948,	0.493,	0.4907,	0.4878,	0.4872,	0.4858,	0.485,	0.4848,	0.4837,	0.4862,	0.4875,	0.4882,	0.4908,	0.492,	0.495,	0.4967,	0.4972,	0.4993,	0.501,	0.5025,	0.503,	0.503,	0.5018,	0.501,	0.5003,	0.4955,	0.4965,	0.4918,	0.486,	0.4818,	0.4752,	0.4695,	0.4625,	0.4547,	0.448,	0.4383,	0.4292,	0.4177,	0.4088,	0.3965,	0.3858,	0.3745,	0.3653,	0.3537,	0.3423,	0.3312,	0.3183,	0.3078,	0.298,	0.2853,	0.2745,	0.264,	0.252,	0.244,	0.232,	0.2215,	0.212,	0.203,	0.1928,	0.1828,	0.1745,	0.1655,	0.158,	0.1498,	0.141,	0.135,	0.1285,	0.1212,	0.1138,	0.1087,	0.1023,	0.097,	0.09175,	0.087,	0.082,	0.07625,	0.0725,	0.06825,	0.06425,	0.06075,	0.057,	0.05325,	0.05075,	0.04775,	0.04425,	0.04175,	0.03925,	0.037,	0.03475,	0.03275,	0.03075,	0.029,	0.02775,	0.02575,	0.0245,	0.023,	0.02175,	0.02,	0.019,	0.018,	0.0165,	0.0155,	0.01475,	0.01375,	0.01275,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double purpleArray[] = {0.0015,	0.003,	0.0025,	0.00225,	0.002,	0.0025,	0.00275,	0.0025,	0.00225,	0.0025,	0.00275,	0.00225,	0.0025,	0.002,	0.002,	0.00225,	0.0025,	0.00275,	0.00275,	0.00325,	0.00275,	0.00325,	0.004,	0.00425,	0.005,	0.006,	0.00825,	0.01125,	0.01575,	0.02175,	0.03,	0.0415,	0.05825,	0.07875,	0.1068,	0.1403,	0.1835,	0.2392,	0.3113,	0.4047,	0.5153,	0.6337,	0.7313,	0.7847,	0.7603,	0.669,	0.546,	0.4333,	0.3502,	0.2908,	0.249,	0.2118,	0.1752,	0.1405,	0.1095,	0.0855,	0.06825,	0.056,	0.047,	0.0385,	0.03275,	0.02675,	0.021,	0.01725,	0.01425,	0.012,	0.01,	0.00875,	0.00725,	0.00625,	0.00575,	0.005,	0.00425,	0.00375,	0.003,	0.003,	0.00275,	0.0025,	0.002,	0.002,	0.002,	0.002,	0.002,	0.002,	0.002,	0.002,	0.002,	0.002,	0.002,	0.00225,	0.003,	0.003,	0.00325,	0.004,	0.00475,	0.00575,	0.00675,	0.008,	0.01,	0.012,	0.015,	0.0185,	0.02325,	0.0285,	0.03525,	0.04375,	0.05225,	0.064,	0.07725,	0.09275,	0.1115,	0.1305,	0.1565,	0.1832,	0.215,	0.248,	0.2855,	0.3243,	0.3662,	0.4098,	0.4555,	0.503,	0.5505,	0.595,	0.6422,	0.69,	0.7317,	0.7717,	0.809,	0.8453,	0.8785,	0.906,	0.93,	0.951,	0.9692,	0.9832,	0.9905,	0.9968,	0.999,	0.9993,	0.9957,	0.9887,	0.9787,	0.965,	0.9522,	0.937,	0.9175,	0.8975,	0.8775,	0.8563,	0.8312,	0.8068,	0.7807,	0.7538,	0.7305,	0.7078,	0.681,	0.6543,	0.6308,	0.6042,	0.579,	0.5538,	0.531,	0.5087,	0.484,	0.461,	0.4392,	0.4163,	0.395,	0.376,	0.3562,	0.3398,	0.321,	0.304,	0.286,	0.271,	0.257,	0.2432,	0.2295,	0.2163,	0.2035,	0.1913,	0.1803,	0.1705,	0.1602,	0.1502,	0.142,	0.1332,	0.1258,	0.118,	0.11,	0.103,	0.0965,	0.0905,	0.0855,	0.07975,	0.07475,	0.07025,	0.06625,	0.06275,	0.05975,	0.0555,	0.052,	0.04825,	0.0455,	0.043,	0.0405,	0.0385,	0.03575,	0.033,	0.03125,	0.02925,	0.02775,	0.02625,	0.02475,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};

    // define recipe here
    int FSW4000i = 0, FSW5000i =0 , Limei = 0, purplei = 0;

    // PPF calculation
    double PPF = 0, BPF = 0;
    double PPFconst = 8.359 * 0.001;
    int PPFrangemin = 20, PPFrangemax = 170;
    int BPFrangemin = 0, BPFrangemax = 235;
    double bluePPF, greenPPF, redPPF, red2PPF;
    int bluePPFrangemin = 20, getBluePPFrangemax = 70, redPPFrangemin = 121, redPPFrangemax = 170, greenPPFrangemin = 71, greenPPFrangemax = 120, red2PPFrangemin = 171, red2PPFrangemax = 234;

    Button tvRecipes;

    public Horti_recipe_fragment_growth_consumer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Horti_recipe_fragment_growth.
     */
    // TODO: Rename and change types and number of parameters
    public static Horti_recipe_fragment_growth_consumer newInstance(String param1, String param2) {
        Horti_recipe_fragment_growth_consumer fragment = new Horti_recipe_fragment_growth_consumer();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler= new Handler(Looper.getMainLooper());
        updateRequirements("To support plant growth by horticulture lighting the spectra concentrates on an efficient creation of photons in the photosynthetic relevant region from 400 - 700 nm and especially at the high absorption region around 450 nm and 660 nm. \n \n" +
                "The most efficient way to create photons for photosynthesis is a direct emitting hyper red LED at 660 nm.");
        updateGraph();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horti_recipe_description, container, false);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvRecipes = view.findViewById(R.id.tvRecipes);
        tvRecipes.setOnClickListener(this);
        updateLights();
        return view;
    }


    public void updateRequirements( final String Description){
        handler.post(new Runnable() {
            public void run() {
            tvDescription.setText(Description);
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
                Horti_FragmentPie fragment1 = (Horti_FragmentPie) getFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":1");
                fragment1.updatePie((int)bluePPF, (int)greenPPF, (int)redPPF, (int)red2PPF);
                Horti_FragmentGraph_consumer fragment = (Horti_FragmentGraph_consumer) getFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                fragment.updateGraph(0, purplei);
                fragment.updateGraph(1, Limei);
                fragment.updateGraph(2, FSW4000i);
                fragment.updateGraph(3, FSW5000i);

                bluePPF = 0;
                redPPF = 0;
                greenPPF = 0;
                red2PPF = 0;
            }
        });
    }
    public void updateLights() {
    // update lights
        handler.post(new Runnable() {
            public void run() {
                Horticulture_consumer.sendMessage("@" + Integer.toString(200));
//                int j = 187; //0xD
//                Horticulture_recipe.mBluetoothConnection.write(j);
//                Horticulture_recipe.mBluetoothConnection.write(bluei);
//                j = 204; //0xDD
//                Horticulture_recipe.mBluetoothConnection.write(j);
//                Horticulture_recipe.mBluetoothConnection.write(whitei);
//                j = 170; //0xDD
//                Horticulture_recipe.mBluetoothConnection.write(j);
//                Horticulture_recipe.mBluetoothConnection.write(redi);
//                j = 221; //0xDD
//                Horticulture_recipe.mBluetoothConnection.write(j);
//                Horticulture_recipe.mBluetoothConnection.write(red2i);
//                j = 238; //0xEE
//                Horticulture_recipe.mBluetoothConnection.write(j);
//                Horticulture_recipe.mBluetoothConnection.write(purplei);
            }
        });
    }

    @Override
    public void onClick(View view) {
        // get fragment manager
        FragmentManager fm = getFragmentManager();
        // replace frame layout with fragment
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_down_back, R.anim.fade_out);
        ft.replace(R.id.frameLayout, new Horti_recipe_fragment_container_consumer());
        ft.commit();
    }

}
