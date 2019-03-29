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
        final GridView gridView = (GridView)findViewById(R.id.gridview);
        final GridViewAdapter gridAdapter = new GridViewAdapter(this, demos);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                switch(position){
                    case 0: // wall washer demo
                        Intent intent4 = new Intent(MainActivity.this, Architecture.class);
                        intent4.putExtra("key", "wall");
                        startActivity(intent4);
                        break;
                    case 2: // wall washer demo
                        Intent intent6 = new Intent(MainActivity.this, Horticulture_prof.class);
                        intent6.putExtra("key", "hortiprof");
                        startActivity(intent6);
                        break;
                    case 3: // wall washer demo
                        Intent intent5 = new Intent(MainActivity.this, Horticulture_consumer.class);
                        intent5.putExtra("key", "horticonsumer");
                        startActivity(intent5);
                        break;
                    case 4: // horticulture
                        Intent intent = new Intent(MainActivity.this, Bluetooth.class);
                        intent.putExtra("key", "horti");
                        startActivity(intent);
                        break;
                    case 5: // horticulture
                        Intent intent2 = new Intent(MainActivity.this, Bluetooth.class);
                        intent2.putExtra("key", "horti2");
                        startActivity(intent2);
                        break;
                    case 1: // heart-rate sensing
                        Intent intent1 = new Intent(MainActivity.this, Bluetooth.class);
                        intent1.putExtra("key", "heart");
                        startActivity(intent1);
                        break;
                    case 6: // S5 horti demo
                        Intent intent3 = new Intent(MainActivity.this, Bluetooth.class);
                        intent3.putExtra("key", "S5");
                        startActivity(intent3);
                        break;
                    default:
                        break;
                }
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
            new Demos(R.string.wall_washer, R.drawable.wallwasher),
            new Demos(R.string.heart_rate_sensing, R.drawable.colors),
            new Demos(R.string.horti_prof, R.drawable.prof_horti),
            new Demos(R.string.horti_consumer, R.drawable.consumer_horti),
            new Demos(R.string.horticulture_lighting, R.drawable.s5demo),
            new Demos(R.string.horticulture_lighting_OSLON, R.drawable.hortidemo2),
            new Demos(R.string.S5_horti_demo, R.drawable.hortidemo),
    };
}


