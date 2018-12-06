package com.osram.os.demos;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.UUID;

public class Horticulture_recipe_s5 extends AppCompatActivity {
    Handler handler;

    FragmentPagerAdapter adapterViewPager;
    private Horti_FragmentGraph_s5 mFragmentGraph;
    private Horti_FragmentPie mFragmentPie;
    private Horti_recipe_fragment_growth_s5 mFragment1;
    private Horti_recipe_fragment_container_s5 mFragement2;

    public static TextView tvTitle, tvColors;

    public static BluetoothConnectionService mBluetoothConnection;
    public BluetoothDevice bluetoothDevice;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // update lights
                    int j = 170; //0xDD
                    mBluetoothConnection.write(j);
                    mBluetoothConnection.write(0);
                    j = 187; //0xDD
                    mBluetoothConnection.write(j);
                    mBluetoothConnection.write(0);
                    j = 204; //0xDD
                    mBluetoothConnection.write(j);
                    mBluetoothConnection.write(0);
                    j = 221; //0xDD
                    mBluetoothConnection.write(j);
                    mBluetoothConnection.write(0);

                    handler.post(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(Horticulture_recipe_s5.this, Horticulture_s5.class);
                            intent.putExtra("btdevice", bluetoothDevice); // maintain BT connection
                            startActivity(intent);
                        }
                    });
                    mBluetoothConnection.cancel();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
            }
            return false;
        }
    };


    GraphView graph;
    LineGraphSeries<DataPoint> series;       //an Object of the PointsGraphSeries for plotting scatter graphs


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_horticulture_recipe_hp);
        handler= new Handler(Looper.getMainLooper());

        //        maintain connection on this Activity
        bluetoothDevice = getIntent().getExtras().getParcelable("btdevice");
        mBluetoothConnection = new BluetoothConnectionService(Horticulture_recipe_s5.this);
        mBluetoothConnection.startClient(bluetoothDevice, MY_UUID_INSECURE);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);

        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new Horticulture_recipe_s5.MyPagerAdapter3(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(0);

        tvTitle = findViewById(R.id.tvTitle);
        tvColors = findViewById(R.id.tvColors);

        // get fragment manager
        FragmentManager fm = getSupportFragmentManager();
    // replace frame layout with fragment
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, new Horti_recipe_fragment_container_s5());
        ft.commit();

    }

    public void onBackPressed(){
        Intent intent = new Intent(Horticulture_recipe_s5.this, Horticulture_s5.class);
        intent.putExtra("btdevice", bluetoothDevice); // maintain BT connection
        startActivity(intent);
        finish();
    }

    private class MyPagerAdapter3 extends FragmentPagerAdapter {
        // other code in your custom FragmentPagerAdapter...
        private int NUM_ITEMS = 2;

        public MyPagerAdapter3(FragmentManager fragmentManager) {
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
                    return new Horti_FragmentGraph_s5();
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
                    mFragmentGraph = (Horti_FragmentGraph_s5) createdFragment;
                    break;
                case 1:
                    mFragmentPie = (Horti_FragmentPie) createdFragment;
                    break;
            }
            return createdFragment;
        }
    }

}
