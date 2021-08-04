package com.sample.unimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

import org.json.JSONException;

import static com.sample.unimedical.util.RequestSender.sendEcountLoginRequest;
import static com.sample.unimedical.util.RequestSender.sendEcountZoneRequest;
import static com.sample.unimedical.util.ResponseHandler.getErrorMessage;
import static com.sample.unimedical.util.ResponseHandler.getSessionID;
import static com.sample.unimedical.util.ResponseHandler.getZoneCode;
import static com.sample.unimedical.util.ResponseHandler.validateLoginJSON;

public class EcountActivity extends AppCompatActivity {

    EditText editComCode;
    EditText editUserID;
    Button ecountLogin;

    private String ZONE_CODE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecount_login);

        editComCode = findViewById(R.id.edit_com_code);
        editUserID = findViewById(R.id.edit_user_id);
        ecountLogin = findViewById(R.id.btn_ecount_login);

        Thread loginThread = new Thread(() -> login());
        Thread zoneThread = new Thread(() -> zone());

        ecountLogin.setOnClickListener(view -> {
            try {
                zoneThread.start();
                try {
                    zoneThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loginThread.start();
            } catch (IllegalThreadStateException e) {
                e.printStackTrace();
            }
        });

    }

    private void zone() {
        String response = null;
        try {
            response = sendEcountZoneRequest(editComCode.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ZONE_CODE = getZoneCode(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        if (ZONE_CODE.isEmpty()) {
            return;
        }
        try {
            String result = sendEcountLoginRequest(ZONE_CODE, editComCode.getText().toString(), editUserID.getText().toString());

            if (validateLoginJSON(result)) {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show());

                Intent ecountLoginComplete = setIntent(getIntent().getStringExtra("nextActivity"));
                ecountLoginComplete.putExtra("SESSION_ID", getSessionID(result));
                ecountLoginComplete.putExtra("ZONE_CODE", ZONE_CODE);
                startActivity(ecountLoginComplete);

            } else {
                runOnUiThread(() -> {
                    try {
                        Toast.makeText(getApplicationContext(), getErrorMessage(result), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Intent setIntent(String extra) {
        Intent intent = new Intent();
        if (extra.equals("InputSaleActivity")) {
            intent = new Intent(getApplicationContext(), InputSaleActivity.class);
        }

        if (extra.equals("StockActivity")) {
            intent = new Intent(getApplicationContext(), StockActivity.class);
        }

        return intent;
    }

}
