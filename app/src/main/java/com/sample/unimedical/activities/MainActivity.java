package com.sample.unimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

public class MainActivity extends AppCompatActivity {
    Button btnSearchEngine;
    Button btnSearchMarket;
    Button btnCommunity;
    Button buttonMap;
    Button btnStatus;
    Button btnUpdate;

    private long backKeyPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearchEngine = findViewById(R.id.btn_search_engine);
        btnSearchMarket = findViewById(R.id.btn_search_market);
        btnCommunity = findViewById(R.id.btn_input_sales);
        buttonMap = findViewById(R.id.btn_map);
        btnStatus = findViewById(R.id.btn_status);
        btnUpdate = findViewById(R.id.btn_update);

        btnSearchEngine.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DeviceSearchActivity.class)));

        btnSearchMarket.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MarketSearchActivity.class)));

        buttonMap.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapActivity.class)));

        btnCommunity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EcountLoginActivity.class)));

        btnStatus.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StatusActivity.class)));

        btnUpdate.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, UpdateActivity.class)));
    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.msg_quit), Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }

}