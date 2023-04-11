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
    ProgressBar progressBar;
    Toolbar toolbar;
//    WebView webView;
    ImageView imageView;
    TextView titleText;
    TextView disText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolBar);
//        webView = findViewById(R.id.webView);
        imageView = findViewById(R.id.pImg);
        titleText = findViewById(R.id.pTitle);
        disText = findViewById(R.id.pDis);

        Intent intent = getIntent();
        int image = intent.getIntExtra("img",0);
        String title = intent.getStringExtra("title");
        String discription = intent.getStringExtra("discription");

        titleText.setText(title);
//        imageView.setImageResource(image);
        Glide.with(this).load(image).into(imageView);


        Document document = Jsoup.parse(discription );
        disText.setText(document.wholeText());


        setSupportActionBar(toolbar);
//        webView.setVisibility(View.INVISIBLE);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                progressBar.setVisibility(View.GONE);
//                webView.setVisibility(View.VISIBLE);
//            }
//        });
//        webView.loadUrl(getIntent().getStringExtra("url"));


    }
}