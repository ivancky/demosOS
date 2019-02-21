package com.osram.os.demos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yc.peddemo.utils.GlobalVariable;

public class Architecture extends Activity {

    WebView browserView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Removes the title bar in the application
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.architecture);

        //Creation of the Webview found in the XML Layout file
        browserView = (WebView)findViewById(R.id.webkit);

        //Enable Javascripts
        browserView.getSettings().setJavaScriptEnabled(true);

        //Removes both vertical and horizontal scroll bars
        browserView.setVerticalScrollBarEnabled(false);
        browserView.setHorizontalScrollBarEnabled(false);

        //Prevent app from opening new page in browser
        browserView.setWebViewClient(new WebViewClient());

        //The website which is wrapped to the webview
        browserView.loadUrl("http://192.168.1.1:80/");
    }

    public void onBackPressed(){
        Intent intent = new Intent(Architecture.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}