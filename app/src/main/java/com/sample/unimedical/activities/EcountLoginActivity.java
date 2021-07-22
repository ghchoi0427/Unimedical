package com.sample.unimedical.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

import static com.sample.unimedical.util.RequestSender.sendEcountLoginRequest;

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

        ecountLogin.setOnClickListener(view -> new Thread(() -> {

            try {
                String result = sendEcountLoginRequest("310316", "양예림");
                runOnUiThread(() -> textEcountLoginResult.setText(result));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start());

    }
}
