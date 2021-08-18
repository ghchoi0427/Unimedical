package com.sample.unimedical.activities;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;
import com.sample.unimedical.adapter.StockAdapter;
import com.sample.unimedical.domain.stock.Stock;
import com.sample.unimedical.util.DataMapper;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sample.unimedical.util.JsonFactory.JsonArrayToStockList;
import static com.sample.unimedical.util.RequestSender.sendEcountStockRequest;
import static com.sample.unimedical.util.ResponseHandler.getProductResults;

public class StockActivity extends AppCompatActivity {
    private String SESSION_ID;
    private String ZONE_CODE;
    RecyclerView recyclerViewStock;
    StockAdapter stockAdapter;
    Button btnStockUpdate;
    Button btnSearchStock;
    EditText editSearchStock;
    private long backKeyPressedTime = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        btnSearchStock = findViewById(R.id.btn_search_stock);
        editSearchStock = findViewById(R.id.edit_search_stock);

        btnStockUpdate.setOnClickListener(v -> {
            clearStockList(stockAdapter);
            showItems();
        });

        btnSearchStock.setOnClickListener(v -> {
            stockAdapter.addItems(searchItem(stockAdapter.stocks, editSearchStock.getText().toString().trim()));
            runOnUiThread(() -> stockAdapter.notifyDataSetChanged());
        });
    }

    private void showItems() {

        new Thread(() -> {
            try {
                stockAdapter.addItems(JsonArrayToStockList(getProductResults(sendEcountStockRequest(ZONE_CODE, SESSION_ID))));
                runOnUiThread(() -> stockAdapter.notifyDataSetChanged());

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void clearStockList(StockAdapter stockAdapter) {
        stockAdapter.clearItem();
    }

    private List<Stock> searchItem(List<Stock> list, String keyword) {
        List<Stock> searchedList = new ArrayList<>();
        DataMapper dataMapper = new DataMapper();
        for (Stock stock : list) {
            try {
                if (dataMapper.getProductNameByCode(stock.getPROD_CD()).contains(keyword)) {
                    searchedList.add(stock);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return searchedList;
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
