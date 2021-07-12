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
import com.sample.unimedical.adapter.DeviceAdapter;
import com.sample.unimedical.device.ItemList;

import java.io.IOException;
import java.io.InputStream;

public class SearchActivity extends AppCompatActivity {
    EditText editText;
    Button searchButton;
    RecyclerView recyclerView;
    DeviceAdapter adapter;
    ItemList itemList;

    RadioGroup radioGroup;

    private static final int SEARCH_NAME = 0;
    private static final int SEARCH_CODE = 1;
    private static final int SEARCH_MAKER = 2;
    private static final int SEARCH_VENDOR = 3;

    private int searchMode = SEARCH_NAME;

    private static final String FILE_NAME = "device_data_0712.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = findViewById(R.id.edit);
        searchButton = findViewById(R.id.btn_search_result);
        recyclerView = findViewById(R.id.recyclerView);
        radioGroup = findViewById(R.id.radio_group);
        adapter = new DeviceAdapter();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> processResponse(getJsonFromAssets(getApplicationContext(), FILE_NAME)));

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_product_name:
                    searchMode = SEARCH_NAME;
                    break;
                case R.id.radio_primary_code:
                    searchMode = SEARCH_CODE;
                    break;
                case R.id.radio_maker:
                    searchMode = SEARCH_MAKER;
                    break;
                case R.id.radio_vendor:
                    searchMode = SEARCH_VENDOR;
                    break;
            }
        });

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
            switch (searchMode) {
                case SEARCH_CODE:
                    itemList.getItems().stream().filter(e -> e.getPrimaryCode().toLowerCase().contains(keyword.toLowerCase())).forEach(adapter::addItem);
                    break;
                case SEARCH_NAME:
                    itemList.getItems().stream().filter(e -> e.getProductName().toLowerCase().contains(keyword.toLowerCase())).forEach(adapter::addItem);
                    break;
                case SEARCH_MAKER:
                    itemList.getItems().stream().filter(e -> e.getMaker().toLowerCase().contains(keyword.toLowerCase())).forEach(adapter::addItem);
                    break;
                case SEARCH_VENDOR:
                    itemList.getItems().stream().filter(e -> e.getVendor().toLowerCase().contains(keyword.toLowerCase())).forEach(adapter::addItem);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearItemList(DeviceAdapter adapter) {
        adapter.clearItem();
    }

    private void notifyDataSetChanged(DeviceAdapter adapter) {
        runOnUiThread(adapter::notifyDataSetChanged);
    }

}