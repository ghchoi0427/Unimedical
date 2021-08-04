package com.sample.unimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

public class LoginActivity extends AppCompatActivity {

    EditText editPassword;
    Button btnLogin;
    Button btnVisitorLogin;
    String password = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        btnVisitorLogin = findViewById(R.id.btn_login_visitor);
        editPassword = findViewById(R.id.edit_login_code);

        btnLogin.setOnClickListener(v -> {
            if (validatePassword(editPassword.getText().toString().trim())) {
                startActivity(new Intent(this, MainActivity.class).putExtra("authentication", true));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btnVisitorLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class).putExtra("authentification", false));
            finish();
        });

    }

    private boolean validatePassword(String input) {
        return input.equals(password);
    }
}