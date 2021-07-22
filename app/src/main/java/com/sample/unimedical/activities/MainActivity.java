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

    private long backKeyPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearchEngine = findViewById(R.id.btn_search_engine);
        btnSearchMarket = findViewById(R.id.btn_search_market);
        btnCommunity = findViewById(R.id.btn_input_sales);
        buttonMap = findViewById(R.id.btn_map);

        btnSearchEngine.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DeviceSearchActivity.class)));

        btnSearchMarket.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MarketSearchActivity.class)));

        buttonMap.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapActivity.class)));

        btnCommunity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EcountLoginActivity.class)));

    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }

}
