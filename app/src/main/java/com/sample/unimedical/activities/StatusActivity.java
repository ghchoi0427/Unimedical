package com.sample.unimedical.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sample.unimedical.R;
import com.sample.unimedical.adapter.StatusAdapter;
import com.sample.unimedical.domain.status.StatusList;
import com.sample.unimedical.util.FirebaseHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static com.sample.unimedical.util.XMLHandler.readTextFile;

public class StatusActivity extends AppCompatActivity {

    StatusList statusList;
    StatusAdapter adapter;
    RecyclerView recyclerViewStatus;

    TextView textStatusLocation;

    ArrayAdapter<CharSequence> cityAdapter;
    Spinner citySpinner;

    private String CITY_NAME = "";
    private static final String FILE_NAME = "sale_status.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        recyclerViewStatus = findViewById(R.id.recyclerview_status);
        adapter = new StatusAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewStatus.setLayoutManager(layoutManager);
        recyclerViewStatus.setAdapter(adapter);

        textStatusLocation = findViewById(R.id.text_status_location_item);

        citySpinner = findViewById(R.id.spinner_city);
        cityAdapter = ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);


        File statFile = new File(getApplicationContext().getFilesDir() + "/" + FILE_NAME);
        if (!statFile.exists()) {
            try {
                FirebaseHandler.downloadFile(getApplicationContext(), FILE_NAME, FILE_NAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CITY_NAME = parent.getItemAtPosition(position).toString();
                processResponse(getJsonFromAssets(getApplicationContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String getJsonFromAssets(Context context) {
        String jsonString;
        try {
            InputStream is = context.openFileInput(FILE_NAME);
            jsonString = readTextFile(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void processResponse(String response) {
        Gson gson = new Gson();
        statusList = gson.fromJson(response, StatusList.class);

        clearItemList(adapter);
        searchItem(CITY_NAME);
        notifyDataSetChanged(adapter);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
