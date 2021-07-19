package com.sample.unimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

public class SubActivity extends AppCompatActivity {
    Button btnSearchEngine;
    Button btnSearchMarket;
    Button btnCommunity;
    Button buttonMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        btnSearchEngine = findViewById(R.id.btn_search_engine);
        btnSearchMarket = findViewById(R.id.btn_search_market);
        btnCommunity = findViewById(R.id.btn_community);
        buttonMap = findViewById(R.id.btn_map);

        btnSearchEngine.setOnClickListener(v -> startActivity(new Intent(SubActivity.this, DeviceSearchActivity.class)));

        btnCommunity.setOnClickListener(v -> startActivity(new Intent(SubActivity.this, CommunityActivity.class)));

        buttonMap.setOnClickListener(v -> startActivity(new Intent(SubActivity.this, MapActivity.class)));

        btnSearchMarket.setOnClickListener(v -> startActivity(new Intent(SubActivity.this, MarketSearchActivity.class)));

    }

}
