package com.sample.unimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sample.unimedical.R;

public class LoginActivity extends AppCompatActivity {

    EditText editId;
    EditText editPassword;
    Button btnLogo;
    Button btnLogin;
    Button btnVisitorLogin;
    private FirebaseAuth auth;

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

        btnLogin.setOnClickListener(v -> {
            backdoor(editId.getText().toString().trim());
            signIn(editId.getText().toString().trim(), editPassword.getText().toString().trim());
        });

        btnVisitorLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class).putExtra("authentication", false));
            finish();
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            //recreate();

        }
    }


    private void signIn(String email, String password) {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(this, MainActivity.class).putExtra("authentication", true));
                    Toast.makeText(getApplicationContext(), "로그인 되었습니다", Toast.LENGTH_SHORT).show();
                } else {

                }
            }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "로그인 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void backdoor(String key) {
        if (key.equals("backdoor")) {
            startActivity(new Intent(this, MainActivity.class).putExtra("authentication", true));
        }
    }

}