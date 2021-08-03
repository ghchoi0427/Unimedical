package com.sample.unimedical.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;
import com.sample.unimedical.adapter.StockAdapter;

import org.json.JSONException;

import java.io.IOException;

import static com.sample.unimedical.util.RequestSender.sendEcountStockRequest;
import static com.sample.unimedical.util.ResponseHandler.getProductResults;

public class StockActivity extends AppCompatActivity {
    private String SESSION_ID = "";
    private String ZONE_CODE = "";
    RecyclerView recyclerViewStock;
    StockAdapter stockAdapter;
    Button btnStockUpdate;

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

        btnStockUpdate = findViewById(R.id.btn_stock_update);

        showItems();
        stockAdapter.notifyDataSetChanged();
        btnStockUpdate.setOnClickListener(v -> {

        });

    }

    private void showItems() {

        new Thread(() -> {
            try {
                stockAdapter.addItems(getProductResults(sendEcountStockRequest(ZONE_CODE, SESSION_ID)));
                Log.d("tester", stockAdapter.stocks.get(0).getPROD_CD() + "");
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
