package com.sample.unimedical.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sample.unimedical.R;
import com.sample.unimedical.adapter.DeviceAdapter;
import com.sample.unimedical.device.Item;
import com.sample.unimedical.device.ItemList;

import java.io.IOException;
import java.io.InputStream;

public class SearchActivity extends AppCompatActivity {
    EditText editText;
    Button searchButton;
    TextView resultView;
    RecyclerView recyclerView;
    DeviceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = findViewById(R.id.edit);
        searchButton = findViewById(R.id.btn_search_result);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new DeviceAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> {
            processResponse(getJsonFromAssets(getApplicationContext(), "device_data.json"));
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
        ItemList itemList = gson.fromJson(response, ItemList.class);

        clearItemList(adapter);

        for (Item i : itemList.getItems()) {
            adapter.addItem(i);
        }

        notifyDataSetChanged(adapter);
    }

    private void clearItemList(DeviceAdapter adapter) {
        adapter.clearItem();
    }

    private void notifyDataSetChanged(DeviceAdapter adapter) {
        runOnUiThread(adapter::notifyDataSetChanged);
    }

    private void print(String s) {
        Log.d("test1", s);
    }

}