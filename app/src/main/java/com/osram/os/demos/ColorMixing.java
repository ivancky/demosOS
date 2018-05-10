package com.osram.os.demos;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

import java.util.UUID;

public class ColorMixing extends AppCompatActivity implements View.OnClickListener {
    FragmentPagerAdapter adapterViewPager;
    private Color_FragmentGraph mFragmentGraph;
    Handler handler;
    BluetoothConnectionService mBluetoothConnection;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private SeekBar SeekDeepblue, SeekBlue, SeekGreen, SeekLime, SeekYellow, SeekAmber, SeekRed, SeekWhite1, SeekWhite2;
    private Button btnReset2;
    BluetoothDevice bluetoothDevice;

    int deepbluei = 0, bluei = 0, greeni = 0, limei = 0, yellowi = 0, amberi = 0, redi = 0, white1i =0 , white2i = 0;
    double graphArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double waveArray[] = {360,	362,	364,	366,	368,	370,	372,	374,	376,	378,	380,	382,	384,	386,	388,	390,	392,	394,	396,	398,	400,	402,	404,	406,	408,	410,	412,	414,	416,	418,	420,	422,	424,	426,	428,	430,	432,	434,	436,	438,	440,	442,	444,	446,	448,	450,	452,	454,	456,	458,	460,	462,	464,	466,	468,	470,	472,	474,	476,	478,	480,	482,	484,	486,	488,	490,	492,	494,	496,	498,	500,	502,	504,	506,	508,	510,	512,	514,	516,	518,	520,	522,	524,	526,	528,	530,	532,	534,	536,	538,	540,	542,	544,	546,	548,	550,	552,	554,	556,	558,	560,	562,	564,	566,	568,	570,	572,	574,	576,	578,	580,	582,	584,	586,	588,	590,	592,	594,	596,	598,	600,	602,	604,	606,	608,	610,	612,	614,	616,	618,	620,	622,	624,	626,	628,	630,	632,	634,	636,	638,	640,	642,	644,	646,	648,	650,	652,	654,	656,	658,	660,	662,	664,	666,	668,	670,	672,	674,	676,	678,	680,	682,	684,	686,	688,	690,	692,	694,	696,	698,	700,	702,	704,	706,	708,	710,	712,	714,	716,	718,	720,	722,	724,	726,	728,	730,	732,	734,	736,	738,	740,	742,	744,	746,	748,	750,	752,	754,	756,	758,	760,	762,	764,	766,	768,	770,	772,	774,	776,	778,	780,	782,	784,	786,	788,	790,	792,	794,	796,	798,	800,	802,	804,	806,	808,	810,	812,	814,	816,	818,	820,	822,	824,	826,	828,	830};

    GraphView graph;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.color_mixing:
                    return true;
                case R.id.heart_rate:
                    Intent intent = new Intent(ColorMixing.this, DeviceScanActivity.class);
                    intent.putExtra("btdevice", bluetoothDevice); // maintain BT connection
                    startActivity(intent);
                    resetColors();
                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_mixing);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.color_mixing);


        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPagerColor);
        adapterViewPager = new ColorMixing.MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(0);
        handler= new Handler(Looper.getMainLooper());

//        maintain connection on this Activity
        bluetoothDevice = getIntent().getExtras().getParcelable("btdevice");
        mBluetoothConnection = new BluetoothConnectionService(ColorMixing.this);
        mBluetoothConnection.startClient(bluetoothDevice, MY_UUID_INSECURE);

        SeekDeepblue = findViewById(R.id.seekDeepBlue);
        SeekBlue = findViewById(R.id.seekBlue);
        SeekGreen = findViewById(R.id.seekGreen);
        SeekLime = findViewById(R.id.seekLime);
        SeekYellow = findViewById(R.id.seekYellow);
        SeekAmber = findViewById(R.id.seekAmber);
        SeekRed = findViewById(R.id.seekRed);
        SeekWhite1 = findViewById(R.id.seekWhite1);
        SeekWhite2 = findViewById(R.id.seekWhite2);
        btnReset2 = findViewById(R.id.btnReset2);
        btnReset2.setOnClickListener(this);

        SeekDeepblue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                deepbluei = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Color_FragmentGraph fragment = (Color_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPagerColor+":0");
                fragment.updateGraph(0, deepbluei);
                int j = 170; // 0xAA
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(deepbluei);
                try {
                    //set time in milix
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeekBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                bluei = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Color_FragmentGraph fragment = (Color_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPagerColor+":0");
                fragment.updateGraph(1, bluei);
                int j = 187; // 0xBB
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(bluei);
                try {
                    //set time in milix
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeekGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                greeni = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Color_FragmentGraph fragment = (Color_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPagerColor+":0");
                fragment.updateGraph(2, greeni);
                int j = 204; // 0xCC
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(greeni);
                try {
                    //set time in milix
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeekLime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                limei = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Color_FragmentGraph fragment = (Color_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPagerColor+":0");
                fragment.updateGraph(3, limei);
                int j = 221; // 0xDD
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(limei);
                try {
                    //set time in milix
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeekYellow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                yellowi = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Color_FragmentGraph fragment = (Color_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPagerColor+":0");
                fragment.updateGraph(4, yellowi);
                int j = 238; // 0xEE
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(yellowi);
                try {
                    //set time in milix
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeekAmber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                amberi = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Color_FragmentGraph fragment = (Color_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPagerColor+":0");
                fragment.updateGraph(5, amberi);
                int j = 255; // 0xFF
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(amberi);
                try {
                    //set time in milix
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeekRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                redi = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Color_FragmentGraph fragment = (Color_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPagerColor+":0");
                fragment.updateGraph(6, redi);
                int j = 160; // 0xA0
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(redi);
                try {
                    //set time in milix
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeekWhite1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                white1i = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Color_FragmentGraph fragment = (Color_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPagerColor+":0");
                fragment.updateGraph(7, white1i);
                int j = 176; // 0xB0
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(white1i);
                try {
                    //set time in milix
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeekWhite2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                white2i = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Color_FragmentGraph fragment = (Color_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPagerColor+":0");
                fragment.updateGraph(8, white2i);
                int j = 192; // 0xC0
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(white2i);
                try {
                    //set time in milix
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) { // reset all
        resetColors();
    }

    public void resetColors(){
        handler.post(new Runnable() {
            public void run() {
                mBluetoothConnection.write(10);
                mBluetoothConnection.write(10);
                Color_FragmentGraph fragment = (Color_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpPagerColor + ":0");
                fragment.updateGraph(0, 0);
                fragment.updateGraph(1, 0);
                fragment.updateGraph(2, 0);
                fragment.updateGraph(3, 0);
                fragment.updateGraph(4, 0);
                fragment.updateGraph(5, 0);
                fragment.updateGraph(6, 0);
                fragment.updateGraph(7, 0);
                fragment.updateGraph(8, 0);
                fragment.updateGraph(9, 0);
                SeekDeepblue.setProgress(0);
                SeekBlue.setProgress(0);
                SeekGreen.setProgress(0);
                SeekLime.setProgress(0);
                SeekYellow.setProgress(0);
                SeekAmber.setProgress(0);
                SeekRed.setProgress(0);
                SeekWhite1.setProgress(0);
                SeekWhite2.setProgress(0);
            }
        });
    }

    public void updateGraph() {
        handler.post(new Runnable() {
            public void run() {

            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        // other code in your custom FragmentPagerAdapter...
        private int NUM_ITEMS = 1;

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
                    return new Color_FragmentGraph();
//                case 1:
//                    return new Horti_FragmentPie();
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
                    mFragmentGraph = (Color_FragmentGraph) createdFragment;
                    break;
//                case 1:
//                    mFragmentPie = (Horti_FragmentPie) createdFragment;
//                    break;
            }
            return createdFragment;
        }
    }
}
