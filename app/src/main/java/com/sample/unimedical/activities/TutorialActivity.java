package com.sample.unimedical.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

public class TutorialActivity extends AppCompatActivity {
    Button btnLecture01;
    Button btnLecture02;
    Button btnLecture03;
    Button btnLecture04;
    Button btnLecture05;
    Button btnLecture06;
    Button btnLecture07;
    Button btnLecture08;
    Button btnLecture09;
    Button btnLecture10;
    Button btnLecture11;

    private String LECTURE_URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        btnLecture01 = findViewById(R.id.btn_lec01);
        btnLecture02 = findViewById(R.id.btn_lec02);
        btnLecture03 = findViewById(R.id.btn_lec03);
        btnLecture04 = findViewById(R.id.btn_lec04);
        btnLecture05 = findViewById(R.id.btn_lec05);
        btnLecture06 = findViewById(R.id.btn_lec06);
        btnLecture07 = findViewById(R.id.btn_lec07);
        btnLecture08 = findViewById(R.id.btn_lec08);
        btnLecture09 = findViewById(R.id.btn_lec09);
        btnLecture10 = findViewById(R.id.btn_lec10);
        btnLecture11 = findViewById(R.id.btn_lec11);

        btnLecture01.setOnClickListener(onClickListener);
        btnLecture02.setOnClickListener(onClickListener);
        btnLecture03.setOnClickListener(onClickListener);
        btnLecture04.setOnClickListener(onClickListener);
        btnLecture05.setOnClickListener(onClickListener);
        btnLecture06.setOnClickListener(onClickListener);
        btnLecture07.setOnClickListener(onClickListener);
        btnLecture08.setOnClickListener(onClickListener);
        btnLecture09.setOnClickListener(onClickListener);
        btnLecture10.setOnClickListener(onClickListener);
        btnLecture11.setOnClickListener(onClickListener);

    }


    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_lec01:
                LECTURE_URL = "https://m.blog.naver.com/unimedical777/222442112053";
                break;
            case R.id.btn_lec02:
                LECTURE_URL = "https://m.blog.naver.com/unimedical777/222442116662";
                break;
            case R.id.btn_lec03:
                LECTURE_URL = "https://m.blog.naver.com/unimedical777/222442118795";
                break;
            case R.id.btn_lec04:
                LECTURE_URL = "https://m.blog.naver.com/unimedical777/222442121688";
                break;
            case R.id.btn_lec05:
                LECTURE_URL = "https://m.blog.naver.com/unimedical777/222442123101";
                break;
            case R.id.btn_lec06:
                LECTURE_URL = "https://m.blog.naver.com/unimedical777/222442124836";
                break;
            case R.id.btn_lec07:
                LECTURE_URL = "https://m.blog.naver.com/unimedical777/222442126256";
                break;
            case R.id.btn_lec08:
                LECTURE_URL = "https://m.blog.naver.com/unimedical777/222442128139";
                break;
            case R.id.btn_lec09:
                LECTURE_URL = "https://m.blog.naver.com/unimedical777/222442130037";
                break;
            case R.id.btn_lec10:
                LECTURE_URL = "https://m.blog.naver.com/unimedical777/222465790458";
                break;
            case R.id.btn_lec11:
                LECTURE_URL = "https://m.blog.naver.com/unimedical777/222465792992";
                break;
        }

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LECTURE_URL)));
    };
}
