package com.sample.unimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler.createAsync(Looper.myLooper()).postDelayed((Runnable) () -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)), 1000);

    }
}
