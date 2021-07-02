package com.sample.unimedical.activities;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

public class CommunityActivity extends AppCompatActivity {
    WebView webView;

    private String url = "http://www.medicaltimes.com/Users/index.html?_REFERER=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        webView = findViewById(R.id.webview);
        try{

            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(url);
            webView.setWebChromeClient(new WebChromeClient());

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
