package com.sample.unimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        ImageButton btnSetting = findViewById(R.id.btn_setting);
        Button btnSearchEngine = findViewById(R.id.btn_search_engine);
        Button btnCommunity = findViewById(R.id.btn_community);
        Button testButton = findViewById(R.id.btn_test);

        btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(SubActivity.this, SettingActivity.class));
        });

        btnSearchEngine.setOnClickListener(v -> {
            startActivity(new Intent(SubActivity.this, SearchActivity.class));
        });

        btnCommunity.setOnClickListener(v -> {
            startActivity(new Intent(SubActivity.this, CommunityActivity.class));
        });

        testButton.setOnClickListener(v -> {
            startActivity(new Intent(SubActivity.this, TestActivity.class));
        });
    }
}
