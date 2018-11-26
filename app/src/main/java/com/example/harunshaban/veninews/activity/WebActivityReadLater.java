package com.example.harunshaban.veninews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.harunshaban.veninews.R;

public class WebActivityReadLater extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_read_later);
        LoadTheData();
    }
    private void LoadTheData(){
        final String url = getIntent().getStringExtra("url");
        WebView webView = findViewById(R.id.web_view_read_later_layout);
        webView.loadUrl(url);
    }
}
