package com.osram.os.demos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import com.neo.arcchartview.ArcChartView;

import java.util.ArrayList;


public class Architecture extends Activity implements View.OnClickListener {

    private Button btnReset;
    private SeekBar blueSeek, limeSeek, redSeek, farredSeek, purpleSeek;
    private TextView tvPPF, tvBPF, tvPurple, tvReset, tvSectionsValue;
    private OkHttpClient client;
    private ArcChartView myArcChartView;
    private ToggleButton btnShow;

    int bluei = 0, redi = 0, whitei =0 , red2i = 0, purplei = 0;
    int j = 0, slice = 0, k = 0;
    Handler handler1 = new Handler();

    WebView browserView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new OkHttpClient();

        //Removes the title bar in the application
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.architecture);

        ToggleButton btnShow = (ToggleButton) findViewById(R.id.btn1); // initiate a toggle button
//        btnShow.setOnClickListener(this);
        btnReset = findViewById(R.id.btnR);
        btnReset.setOnClickListener(this);

        myArcChartView = findViewById(R.id.arc_chart_view);
        myArcChartView.setSectionValue(0, 0);
        myArcChartView.setSectionValue(1, 0);
        myArcChartView.setSectionValue(2, 0);
        myArcChartView.setSectionValue(3, 0);
        myArcChartView.setSectionValue(4, 0);
        myArcChartView.setSectionValue(5, 0);
        myArcChartView.setSectionValue(6, 0);

        /** set colors */
        myArcChartView.setFilldeColor(0, this.getResources().getColor(R.color.colorBlue2));
        myArcChartView.setUnFilldeColor(0, this.getResources().getColor(R.color.colorBlue2_));
        myArcChartView.setFilldeColor(1, this.getResources().getColor(R.color.colorCyan));
        myArcChartView.setUnFilldeColor(1, this.getResources().getColor(R.color.colorCyan_));
        myArcChartView.setFilldeColor(2, this.getResources().getColor(R.color.colorGreen));
        myArcChartView.setUnFilldeColor(2,this.getResources().getColor(R.color.colorLightGreen));
        myArcChartView.setFilldeColor(3, this.getResources().getColor(R.color.colorMint));
        myArcChartView.setUnFilldeColor(3, this.getResources().getColor(R.color.colorMint_));
        myArcChartView.setFilldeColor(4, this.getResources().getColor(R.color.colorYellow));
        myArcChartView.setUnFilldeColor(4, this.getResources().getColor(R.color.colorYellow_));
        myArcChartView.setFilldeColor(5, this.getResources().getColor(R.color.colorAmber));
        myArcChartView.setUnFilldeColor(5, this.getResources().getColor(R.color.colorAmber_));
        myArcChartView.setFilldeColor(6, this.getResources().getColor(R.color.colorRed));
        myArcChartView.setUnFilldeColor(6, this.getResources().getColor(R.color.colorLightRed));

        btnShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sendMessage("+");
                    // The toggle is enabled
                    for (int a = 1; a <= 5600; a++) {
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(slice == 0) {
                                    j++;
                                    myArcChartView.setSectionValue(0, j);
//                                    sendMessage("!" + Integer.toString(j));
                                    if (j == 8) { slice = 1; }
                                }
                                else if (slice == 1){
                                    j--;
                                    k++;
                                    myArcChartView.setSectionValue(0, j);
//                                    sendMessage("!" + Integer.toString(j));
                                    myArcChartView.setSectionValue(1, k);
//                                    sendMessage("@" + Integer.toString(k));
                                    if (j == 0) { slice = 2; }
                                }
                                else if (slice == 2){
                                    j++;
                                    k--;
                                    myArcChartView.setSectionValue(2, j);
//                                    sendMessage("#" + Integer.toString(j));
                                    myArcChartView.setSectionValue(1, k);
//                                    sendMessage("@" + Integer.toString(k));
                                    if (j == 8) { slice = 3; }
                                }
                                else if (slice == 3){
                                    j--;
                                    k++;
                                    myArcChartView.setSectionValue(2, j);
//                                    sendMessage("#" + Integer.toString(j));
                                    myArcChartView.setSectionValue(3, k);
//                                    sendMessage("$" + Integer.toString(k));
                                    if (j == 0) { slice = 4; }
                                }
                                else if (slice == 4){
                                    j++;
                                    k--;
                                    myArcChartView.setSectionValue(4, j);
//                                    sendMessage("%" + Integer.toString(j));
                                    myArcChartView.setSectionValue(3, k);
//                                    sendMessage("$" + Integer.toString(k));
                                    if (j == 8) { slice = 5; }
                                }
                                else if (slice == 5){
                                    j--;
                                    k++;
                                    myArcChartView.setSectionValue(4, j);
//                                    sendMessage("%" + Integer.toString(j));
                                    myArcChartView.setSectionValue(5, k);
//                                    sendMessage("^" + Integer.toString(k));
                                    if (j == 0) { slice = 6; }
                                }
                                else if (slice == 6){
                                    j++;
                                    k--;
                                    myArcChartView.setSectionValue(6, j);
//                                    sendMessage("&" + Integer.toString(j));
                                    myArcChartView.setSectionValue(5, k);
//                                    sendMessage("^" + Integer.toString(k));
                                    if (j == 8) { slice = 7; }
                                }
                                else if (slice == 7){
                                    j--;
                                    k++;
                                    myArcChartView.setSectionValue(6, j);
//                                    sendMessage("&" + Integer.toString(j));
                                    myArcChartView.setSectionValue(0, k);
//                                    sendMessage("!" + Integer.toString(k));
                                    if (j == 0) { slice = 1; j = 8; k = 0;}
                                }
                            }
                        }, 500 * a); //320 previously
                    }
                } else {
                    handler1.removeCallbacksAndMessages(null);
                    slice = 0; j = 0; k = 0;
                    myArcChartView.setSectionValue(0, 0);
                    myArcChartView.setSectionValue(1, 0);
                    myArcChartView.setSectionValue(2, 0);
                    myArcChartView.setSectionValue(3, 0);
                    myArcChartView.setSectionValue(4, 0);
                    myArcChartView.setSectionValue(5, 0);
                    myArcChartView.setSectionValue(6, 0);
                    sendMessage("-");
//                    sendMessage("@" + Integer.toString(0));
//                    sendMessage("#" + Integer.toString(0));
//                    sendMessage("$" + Integer.toString(0));
//                    sendMessage("%" + Integer.toString(0));
//                    sendMessage("^" + Integer.toString(0));
//                    sendMessage("&" + Integer.toString(0));

                }
            }
        });

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
//                Toast.makeText(getApplication().getBaseContext(), Integer.toString(i1),Toast.LENGTH_SHORT).show();
                switch(i) {
                    case 0:
                        sendMessage("!" + Integer.toString(i1));
                        break;
                    case 1:
                        sendMessage("@" + Integer.toString(i1));
                        break;
                    case 2:
                        sendMessage("#" + Integer.toString(i1));
                        break;
                    case 3:
                        sendMessage("$" + Integer.toString(i1));
                        break;
                    case 4:
                        sendMessage("%" + Integer.toString(i1));
                        break;
                    case 5:
                        sendMessage("^" + Integer.toString(i1));
                        break;
                    case 6:
                        sendMessage("&" + Integer.toString(i1));
                        break;
                    case 7:
                        sendMessage("*" + Integer.toString(i1));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void onBackPressed(){
        Intent intent = new Intent(Architecture.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnR:{
                handler1.removeCallbacksAndMessages(null);
                slice = 0; j = 0; k = 0;
                myArcChartView.setSectionValue(0, 0);
                myArcChartView.setSectionValue(1, 0);
                myArcChartView.setSectionValue(2, 0);
                myArcChartView.setSectionValue(3, 0);
                myArcChartView.setSectionValue(4, 0);
                myArcChartView.setSectionValue(5, 0);
                myArcChartView.setSectionValue(6, 0);
                sendMessage(")");
                break;
            }
        }
    }

    public final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
//            webSocket.send("WebSocket opened");
//            webSocket.send("What's up ?");
//            webSocket.send(ByteString.decodeHex("deadbeef"));
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
//            output("Closing : " + code + " / " + reason);
        }
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
//            output("Error : " + t.getMessage());
        }
    }

    private void sendMessage(String msg){
        Request request = new Request.Builder().url("ws://192.168.1.1:81/").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        ws.send(msg);
    }

//    private void output(final String txt) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
////                output.setText(output.getText().toString() + "\n\n" + txt);
//            }
//        });
//    }

}