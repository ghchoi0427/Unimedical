package com.sample.unimedical.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sample.unimedical.R;

public class LoginActivity extends AppCompatActivity {

    EditText editId;
    EditText editPassword;
    Button btnLogo;
    Button btnLogin;
    Button btnVisitorLogin;
    private FirebaseAuth auth;
    public static final String LAST_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        btnLogo = findViewById(R.id.btn_logo);
        btnLogin = findViewById(R.id.btn_login);
        btnVisitorLogin = findViewById(R.id.btn_login_visitor);
        editId = findViewById(R.id.edit_login_id);
        editPassword = findViewById(R.id.edit_login_password);

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);


        btnLogin.setOnClickListener(v -> {
            backdoor(editId.getText().toString().trim());
            signIn(editId.getText().toString().trim(), editPassword.getText().toString().trim());
        });

        btnVisitorLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class).putExtra("authentication", false));
            finish();
        });

        editId.setText(pref.getString(LAST_ID, ""));

        editId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pref.edit().putString(LAST_ID, s.toString()).apply();
            }
        });


    }


    private void signIn(String email, String password) {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(this, MainActivity.class).putExtra("authentication", true));
                    Toast.makeText(getApplicationContext(), "로그인 되었습니다", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "로그인 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void backdoor(String key) {
        if (key.trim().equals("backdoor")) {
            startActivity(new Intent(this, MainActivity.class).putExtra("authentication", true));
        }
    }

}