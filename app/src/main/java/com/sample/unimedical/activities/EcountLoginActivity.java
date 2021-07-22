package com.sample.unimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import static com.sample.unimedical.util.ResponseHandler.validateJSON;

public class EcountLoginActivity extends AppCompatActivity {

    EditText editComCode;
    EditText editUserID;
    Button ecountLogin;
    TextView textEcountLoginResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecount_login);

        editComCode = findViewById(R.id.edit_com_code);
        editUserID = findViewById(R.id.edit_user_id);
        ecountLogin = findViewById(R.id.btn_ecount_login);
        textEcountLoginResult = findViewById(R.id.text_ecount_login_result);


        ecountLogin.setOnClickListener(view -> login());

    }

    private void login() {
        new Thread(() -> {
            try {
                String result = sendEcountLoginRequest(editComCode.getText().toString(), editUserID.getText().toString());

                if (validateJSON(result)) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show());
                    Intent ecountLoginComplete = new Intent(EcountLoginActivity.this, InputSaleActivity.class);
                    ecountLoginComplete.putExtra("SESSION_ID", getSessionID(result));
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
        }).start();
    }

    private void zone() throws Exception {
        sendEcountZoneRequest(editComCode.getText().toString());
    }
}
