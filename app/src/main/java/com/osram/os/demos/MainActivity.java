package com.osram.os.demos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
//    String[] Activities = new String[] { "Activity1", "Activtiy2", "Activity3" };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setTitle("OSRAM OS Demos");
        final GridView gridView = (GridView)findViewById(R.id.gridview);
        final GridViewAdapter gridAdapter = new GridViewAdapter(this, demos);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
//                Demos demo = demos[position];
//                demo.toggleFavorite();

                switch(position){
                    case 0: // horticulture
                        Intent intent = new Intent(MainActivity.this, Bluetooth.class);
                        intent.putExtra("key", "horti");
                        startActivity(intent);
                        break;
                    case 1: // horticulture
                        Intent intent2 = new Intent(MainActivity.this, Bluetooth.class);
                        intent2.putExtra("key", "horti2");
                        startActivity(intent2);
                        break;
                    case 2: // heart-rate sensing
                        Intent intent1 = new Intent(MainActivity.this, Bluetooth.class);
                        intent1.putExtra("key", "heart");
                        startActivity(intent1);
                        break;
                    case 3: // S5 horti demo
                        Intent intent3 = new Intent(MainActivity.this, Bluetooth.class);
                        intent3.putExtra("key", "S5");
                        startActivity(intent3);
                        break;
                    case 4: // wall washer demo
                        Intent intent4 = new Intent(MainActivity.this, Architecture.class);
                        intent4.putExtra("key", "wall");
                        startActivity(intent4);
                        break;
                    default:
                        break;
                }
//                String val = demos[arg2]; // arg2 is the index of item
//                Class ourClass  = Class.forName("yourpackagename."+val);
//                Intent intent = new Intent(ActivityName.this,ourClass);
//                startActivity(intent);

                // This tells the GridView to redraw itself
                // in turn calling your BooksAdapter's getView method again for each cell
                gridAdapter.notifyDataSetChanged();
            }
        });


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void onBackPressed(){
        this.finish();
    }

    private Demos[] demos = {
//            new Demos(R.string.filament, R.drawable.filament),
//            new Demos(R.string.fog_detection_streetlight, R.drawable.fog),
            new Demos(R.string.horticulture_lighting, R.drawable.hortidemo),
            new Demos(R.string.horticulture_lighting_OSLON, R.drawable.hortidemo2),
            new Demos(R.string.heart_rate_sensing, R.drawable.colors),
            new Demos(R.string.S5_horti_demo, R.drawable.s5demo),
            new Demos(R.string.wall_washer, R.drawable.wallwasher),
//            new Demos(R.string.filament, R.drawable.filament),
//            new Demos(R.string.fog_detection_streetlight, R.drawable.fog),
    };
}


