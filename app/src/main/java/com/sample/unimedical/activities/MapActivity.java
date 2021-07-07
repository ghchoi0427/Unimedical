package com.sample.unimedical.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

import net.daum.android.map.MapView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MapActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MapActivity";
    private MapView mMapView;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        RelativeLayout mapViewContainer = findViewById(R.id.map_view);

        try {
            getHospitalRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getHospitalRequest() throws Exception {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551182/hospInfoService1/getHospBasisList1"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=서비스키"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("uhPZ+yjcUrJD5qN1Q6Wf1+o63BmTtVFSTTKYCRPT0JY7HN934bPpj4S5f2QQng+LHjCADIGxjrHTUE0pGXJfGA==", "UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*서비스키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("sidoCd", "UTF-8") + "=" + URLEncoder.encode("110000", "UTF-8")); /*시도코드*/
        urlBuilder.append("&" + URLEncoder.encode("sgguCd", "UTF-8") + "=" + URLEncoder.encode("110019", "UTF-8")); /*시군구코드*/
        urlBuilder.append("&" + URLEncoder.encode("emdongNm", "UTF-8") + "=" + URLEncoder.encode("신내동", "UTF-8")); /*읍면동명*/
        urlBuilder.append("&" + URLEncoder.encode("yadmNm", "UTF-8") + "=" + URLEncoder.encode("서울의료원", "UTF-8")); /*병원명(UTF-8 인코딩 필요)*/
        urlBuilder.append("&" + URLEncoder.encode("zipCd", "UTF-8") + "=" + URLEncoder.encode("2010", "UTF-8")); /*분류코드(활용가이드 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("clCd", "UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*종별코드(활용가이드 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("dgsbjtCd", "UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); /*진료과목코드(활용가이드 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("xPos", "UTF-8") + "=" + URLEncoder.encode("127.09854004628151", "UTF-8")); /*x좌표(소수점 15)*/
        urlBuilder.append("&" + URLEncoder.encode("yPos", "UTF-8") + "=" + URLEncoder.encode("37.6132113197367", "UTF-8")); /*y좌표(소수점 15)*/
        urlBuilder.append("&" + URLEncoder.encode("radius", "UTF-8") + "=" + URLEncoder.encode("3000", "UTF-8")); /*단위 : 미터(m)*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        //System.out.println(sb.toString());
        Log.d("test", sb.toString());
    }
}
