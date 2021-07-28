package com.sample.unimedical.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sample.unimedical.R;
import com.sample.unimedical.adapter.StatusAdapter;
import com.sample.unimedical.domain.status.StatusList;

import java.io.IOException;
import java.io.InputStream;

public class StatusActivity extends AppCompatActivity {

    StatusList statusList;
    StatusAdapter adapter;
    EditText editStatus;
    RecyclerView recyclerViewStatus;
    Button btnStatusSearch;

    private static final String FILE_NAME = "sale_status.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        editStatus = findViewById(R.id.edit_status);
        recyclerViewStatus = findViewById(R.id.recyclerview_status);
        adapter = new StatusAdapter();
        btnStatusSearch = findViewById(R.id.btn_search_status);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewStatus.setLayoutManager(layoutManager);
        recyclerViewStatus.setAdapter(adapter);

        btnStatusSearch.setOnClickListener(v -> {
            try {
                processResponse(getJsonFromAssets(getApplicationContext()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private String getJsonFromAssets(Context context) throws IOException {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(FILE_NAME);
            byte[] buffer = new byte[is.available()];
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
        statusList = gson.fromJson(response, StatusList.class);

        clearItemList(adapter);
        searchItem(editStatus.getText().toString());
        notifyDataSetChanged(adapter);


    }

    private void searchItem(String keyword) {
        try {
            statusList.getStatuses().stream().filter(e -> e.getAddress().contains(keyword)).forEach(adapter::addItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearItemList(StatusAdapter adapter) {
        adapter.clearItem();
    }

    private void notifyDataSetChanged(StatusAdapter adapter) {
        runOnUiThread(adapter::notifyDataSetChanged);
    }
}
