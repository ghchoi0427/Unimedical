package com.sample.unimedical;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        ImageButton btnSetting = findViewById(R.id.btn_setting);
        Button btnSearch = findViewById(R.id.btn_search);
        Button btnCommunity = findViewById(R.id.btn_community);

        btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(SubActivity.this, SettingActivity.class));
        });

        btnSearch.setOnClickListener(v -> {
            startActivity(new Intent(SubActivity.this, SearchActivity.class));
        });

        btnCommunity.setOnClickListener(v -> {
            startActivity(new Intent(SubActivity.this, CommunityActivity.class));
        });
    }
}
