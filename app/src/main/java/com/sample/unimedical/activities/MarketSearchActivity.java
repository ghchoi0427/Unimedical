package com.sample.unimedical.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sample.unimedical.R;
import com.sample.unimedical.adapter.ItemAdapter;
import com.sample.unimedical.domain.device.ItemList;

import java.io.IOException;
import java.io.InputStream;

public class MarketSearchActivity extends AppCompatActivity {
    EditText editText;
    Button searchButton;
    RecyclerView recyclerView;
    ItemAdapter adapter;
    ItemList itemList;

    private static final String FILE_NAME = "device_data_0712.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = findViewById(R.id.edit);
        searchButton = findViewById(R.id.btn_search_result);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ItemAdapter();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> processResponse(getJsonFromAssets(getApplicationContext(), FILE_NAME)));

    }

    private String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }

    public void processResponse(String response) {
        Gson gson = new Gson();
        itemList = gson.fromJson(response, ItemList.class);

        clearItemList(adapter);
        searchItem(editText.getText().toString());
        notifyDataSetChanged(adapter);
    }

    private void searchItem(String keyword) {

        try {
            itemList.getItems().stream().filter(e -> e.getMaker().toLowerCase().contains(keyword.toLowerCase())).forEach(adapter::addItem);
            itemList.getItems().stream().filter(e -> e.getVendor().toLowerCase().contains(keyword.toLowerCase())).forEach(adapter::addItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearItemList(ItemAdapter adapter) {
        adapter.clearItem();
    }

    private void notifyDataSetChanged(ItemAdapter adapter) {
        runOnUiThread(adapter::notifyDataSetChanged);
    }

}