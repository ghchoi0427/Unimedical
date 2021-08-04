package com.sample.unimedical.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

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
    private long backKeyPressedTime = 0;

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

        btnStockUpdate.setOnClickListener(v -> {
            clearStockList(stockAdapter);
            showItems();
        });
    }

    private void showItems() {

        new Thread(() -> {
            try {
                stockAdapter.addItems(getProductResults(sendEcountStockRequest(ZONE_CODE, SESSION_ID)));
                runOnUiThread(() -> stockAdapter.notifyDataSetChanged());

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void clearStockList(StockAdapter stockAdapter) {
        stockAdapter.clearItem();
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
