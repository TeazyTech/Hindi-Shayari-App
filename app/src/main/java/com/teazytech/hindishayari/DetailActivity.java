package com.teazytech.hindishayari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DetailActivity extends AppCompatActivity {
//    ProgressBar progressBar;
    Toolbar toolbar;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolBar);
        webView = findViewById(R.id.webView);
        Intent intent = getIntent();
        String imgcompress = "<style>img{display: inline; max-width: 100%;}</style>";
        String discription = intent.getStringExtra("discription");
        String  data = imgcompress+discription;

        setSupportActionBar(toolbar);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadData(data,"text/html", "UTF-8");

        webView.setDrawingCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        if(webView.canGoBack()){
            webView.goBack();
        } else if (webView.canGoForward()) {
            webView.goForward();
        }


    }
}