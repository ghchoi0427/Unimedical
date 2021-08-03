package com.sample.unimedical.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

public class StockActivity extends AppCompatActivity {
    private String SESSION_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        SESSION_ID = getIntent().getStringExtra("SESSION_ID");

    }
}
