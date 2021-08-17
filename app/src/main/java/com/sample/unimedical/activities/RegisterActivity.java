package com.sample.unimedical.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sample.unimedical.R;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth auth;

    EditText editRegisterId;
    EditText editRegisterPassword;
    Button btnRegisterAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editRegisterId = findViewById(R.id.edit_register_id);
        editRegisterPassword = findViewById(R.id.edit_register_password);
        btnRegisterAccount = findViewById(R.id.btn_register_account);

        auth = FirebaseAuth.getInstance();
        btnRegisterAccount.setOnClickListener(v -> createAccount(editRegisterId.getText().toString().trim(), editRegisterPassword.getText().toString().trim()));
    }

    private void createAccount(String email, String password) {
        Log.d("tester", auth.getCurrentUser() + "");

        try {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Log.d("tester", "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Register succeed", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w("tester", "createUserWithEmail:failure", task.getException());
                        }
                    }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
