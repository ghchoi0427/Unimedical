package com.sample.unimedical.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sample.unimedical.R;
import com.sample.unimedical.adapter.DeviceAdapter;
import com.sample.unimedical.device.Item;
import com.sample.unimedical.device.ItemList;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    EditText editText;
    Button searchButton;
    RecyclerView recyclerView;
    DeviceAdapter adapter;
    ItemList itemList;

    List<Item> currentItem;
    List<Item> searchItems;
    private int itemCursor = 0;

    private static final String FILE_NAME = "device_data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = findViewById(R.id.edit);
        searchButton = findViewById(R.id.btn_search_result);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new DeviceAdapter();

        searchItems = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> {
            processResponse(getJsonFromAssets(getApplicationContext(), FILE_NAME));
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //check if it is last element
                if (!recyclerView.canScrollVertically(1)) {

                }
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

            for (int i = 0; i < itemList.getItems().size(); i++) {
                //Log.d("test", itemList.getItem(i).getPrimaryCode());
                searchItems.add(itemList.getItem(i));
            }
                /*if (!i.getPrimaryCode().isEmpty()) {
                    if (i.getPrimaryCode().toLowerCase().contains(keyword.toLowerCase())) {
                        wholeItem.add(i);
                    }
                    if (i.getProductName().toLowerCase().contains(keyword.toLowerCase())) {
                        wholeItem.add(i);
                    }
                    if (i.getMaker().toLowerCase().contains(keyword.toLowerCase())) {
                        wholeItem.add(i);
                    }
                    if (i.getVendor().toLowerCase().contains(keyword.toLowerCase())) {
                        wholeItem.add(i);
                    }
                }*/

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