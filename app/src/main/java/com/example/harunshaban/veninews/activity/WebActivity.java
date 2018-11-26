package com.example.harunshaban.veninews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.example.harunshaban.veninews.R;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        LoadTheData();
    }
    private void LoadTheData(){
        final String url = getIntent().getStringExtra("url");
        WebView webView = findViewById(R.id.activity_web_wv);
        webView.loadUrl(url);
    }
}
