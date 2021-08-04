package com.sample.unimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

public class MainActivity extends AppCompatActivity {
    Button btnSearchEngine;
    Button btnStock;
    Button btnInputSale;
    Button btnMap;
    Button btnStatus;
    Button btnUpdate;

    private long backKeyPressedTime = 0;
    private boolean AUTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AUTH = getIntent().getBooleanExtra("authentication", false);

        btnSearchEngine = findViewById(R.id.btn_search_engine);
        btnStock = findViewById(R.id.btn_stock_status);
        btnInputSale = findViewById(R.id.btn_input_sales);
        btnMap = findViewById(R.id.btn_map);
        btnStatus = findViewById(R.id.btn_status);
        btnUpdate = findViewById(R.id.btn_update);

        if (AUTH) {
            btnSearchEngine.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DeviceSearchActivity.class)));
            btnStock.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EcountActivity.class).putExtra("nextActivity", "StockActivity")));
            btnMap.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapActivity.class)));
            btnInputSale.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EcountActivity.class).putExtra("nextActivity", "InputSaleActivity")));
            btnStatus.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StatusActivity.class)));
            btnUpdate.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, UpdateActivity.class)));
        } else {
            btnSearchEngine.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DeviceSearchActivity.class)));
        }
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