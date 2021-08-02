package com.sample.unimedical.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;
import com.sample.unimedical.util.FirebaseHandler;

import java.io.FileNotFoundException;

public class UpdateActivity extends AppCompatActivity {

    Button btnUpdateHospital;
    Button btnUpdateCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        btnUpdateHospital = findViewById(R.id.btn_update_hospital);
        btnUpdateCustomer = findViewById(R.id.btn_update_customer);

        btnUpdateHospital.setOnClickListener(v -> {

        });

        btnUpdateCustomer.setOnClickListener(v -> {
            try {
                FirebaseHandler.downloadFile(getApplicationContext());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

    }
}
