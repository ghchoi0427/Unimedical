package com.sample.unimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.sample.unimedical.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "사용자";
    private ImageButton btnLogin, btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogOut = findViewById(R.id.btnLogout);

        btnLogin.setOnClickListener(v -> UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
                Toast.makeText(getApplicationContext(), "Login failure", Toast.LENGTH_SHORT).show();
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());

                UserApiClient.getInstance().me((user, meError) -> {
                    if (meError != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", meError);
                    } else {
                        System.out.println("로그인 완료");
                        Toast.makeText(getApplicationContext(), "Login complete", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, user.toString());
                        {
                            Log.i(TAG, "사용자 정보 요청 성공" +
                                    "\n회원번호: " + user.getId() +
                                    "\n이메일: " + user.getKakaoAccount().getEmail());
                        }
                        Account user1 = user.getKakaoAccount();
                        System.out.println("사용자 계정" + user1);

                    }
                    return null;
                });
                //로그인 성공 시 화면 전환
                startActivity(new Intent(MainActivity.this, SubActivity.class));

            }
            return null;
        }));

        btnLogOut.setOnClickListener(v -> UserApiClient.getInstance().logout(error -> {
            if (error != null) {
                Log.e(TAG, "로그아웃 실패, SDK에서 토큰 삭제됨", error);
                Toast.makeText(getApplicationContext(), "Log Out failure", Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "로그아웃 성공, SDK에서 토큰 삭제됨");
                Toast.makeText(getApplicationContext(), "Log Out complete", Toast.LENGTH_SHORT).show();
            }
            return null;
        }));
    }
}