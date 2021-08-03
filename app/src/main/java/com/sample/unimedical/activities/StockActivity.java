package com.sample.unimedical.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;
import com.sample.unimedical.adapter.StockAdapter;

import org.json.JSONException;

import java.io.IOException;

import static com.sample.unimedical.util.RequestSender.sendEcountStockRequest;

public class StockActivity extends AppCompatActivity {
    private String SESSION_ID = "";
    private String ZONE_CODE = "";
    RecyclerView recyclerViewStock;
    StockAdapter stockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        recyclerViewStock = findViewById(R.id.recyclerview_stock);
        stockAdapter = new StockAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewStock.setLayoutManager(layoutManager);
        recyclerViewStock.setAdapter(stockAdapter);

        SESSION_ID = getIntent().getStringExtra("SESSION_ID");
        ZONE_CODE = getIntent().getStringExtra("ZONE_CODE");

        new Thread(() -> {
            try {
                sendEcountStockRequest(ZONE_CODE, SESSION_ID);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();


    }
}
