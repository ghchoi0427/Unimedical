package com.sample.unimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

public class SubActivity extends AppCompatActivity {
    ImageButton btnSetting;
    Button btnSearchEngine;
    Button btnCommunity;
    Button buttonMap;
    Button buttonMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        btnSetting = findViewById(R.id.btn_setting);
        btnSearchEngine = findViewById(R.id.btn_search_engine);
        btnCommunity = findViewById(R.id.btn_community);
        buttonMap = findViewById(R.id.btn_map);
        buttonMarker = findViewById(R.id.act_marker);

        btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(SubActivity.this, SettingActivity.class));
        });

        btnSearchEngine.setOnClickListener(v -> {
            startActivity(new Intent(SubActivity.this, SearchActivity.class));
        });

        btnCommunity.setOnClickListener(v -> {
            startActivity(new Intent(SubActivity.this, CommunityActivity.class));
        });

        buttonMap.setOnClickListener(v -> {
            startActivity(new Intent(SubActivity.this, MapActivity.class));
        });

        buttonMarker.setOnClickListener(v -> {
            startActivity(new Intent(SubActivity.this, MarkerActivity.class));
        });


    }

}
