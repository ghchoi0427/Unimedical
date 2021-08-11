package com.sample.unimedical.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

public class MainActivity extends AppCompatActivity {

    private long backKeyPressedTime = 0;
    private static final String URL_TUTORIAL = "https://m.blog.naver.com/PostList.naver?blogId=unimedical777&categoryNo=42&logCode=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean AUTH = getIntent().getBooleanExtra("authentication", false);

        Button btnSearchEngine = findViewById(R.id.btn_search_engine);
        Button btnStock = findViewById(R.id.btn_stock_status);
        Button btnInputSale = findViewById(R.id.btn_input_sales);
        Button btnMap = findViewById(R.id.btn_map);
        Button btnStatus = findViewById(R.id.btn_status);
        Button btnUpdate = findViewById(R.id.btn_update);
        Button btnTutorial = findViewById(R.id.btn_tutorial);

        if (AUTH) {
            btnSearchEngine.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DeviceSearchActivity.class)));
            btnStock.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EcountActivity.class).putExtra("nextActivity", "StockActivity")));
            btnMap.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapActivity.class)));
            btnInputSale.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EcountActivity.class).putExtra("nextActivity", "InputSaleActivity")));
            btnStatus.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StatusActivity.class)));
            btnUpdate.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, UpdateActivity.class)));
            btnTutorial.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TutorialActivity.class)));
        } else {
            btnSearchEngine.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DeviceSearchActivity.class)));
            btnTutorial.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TutorialActivity.class)));
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