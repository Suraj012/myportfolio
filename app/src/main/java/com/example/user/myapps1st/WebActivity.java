package com.example.user.myapps1st;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;

public class WebActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressView progressView;
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // this.requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_web);
        String title = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        progressView = (ProgressView) findViewById(R.id.progressview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");


       progress();

        myWebView.loadUrl(url);
    }

    public void progress() {
        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressView.setVisibility(View.VISIBLE);
                myWebView.setVisibility(View.GONE);


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressView.setVisibility(View.GONE);
                myWebView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Toast.makeText(WebActivity.this, "OOPS! ERROR, POOR INTERNET CONNECTION PLEASE TRY AGAIN",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

