package com.sample.unimedical.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sample.unimedical.R;
import com.sample.unimedical.adapter.DeviceAdapter;
import com.sample.unimedical.device.Item;
import com.sample.unimedical.device.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SearchActivity extends AppCompatActivity {
    EditText edit;
    Button search;
    RecyclerView recyclerView;
    DeviceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edit = findViewById(R.id.edit);
        search = findViewById(R.id.btn_search_result);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new DeviceAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        search.setOnClickListener(v -> new Thread(() -> {
            try {
                startSearch();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start());

    }

    private void startSearch() throws Exception {
        String responseJson = request();
        processResponse(responseJson);
    }

    public void processResponse(String response) {
        Gson gson = new Gson();
        Response fromJson = gson.fromJson(response, Response.class);

        clearItemList(adapter);

        for(Item i : fromJson.getBody().getItems()){
            adapter.addItem(i);
        }

        notifyDataSetChanged(adapter);
    }

    private void clearItemList(DeviceAdapter adapter) {
        adapter.clearItem();
    }

    private void notifyDataSetChanged(DeviceAdapter adapter) {
        runOnUiThread(() -> adapter.notifyDataSetChanged());
    }

    private String request() throws Exception {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/MdeqPrdlstInfoService/getMdeqPrdlstInfoInq"); /*URL*/
        urlBuilder.append("?").append(URLEncoder.encode("serviceKey", "UTF-8")).append("=").append(URLEncoder.encode("uhPZ+yjcUrJD5qN1Q6Wf1+o63BmTtVFSTTKYCRPT0JY7HN934bPpj4S5f2QQng+LHjCADIGxjrHTUE0pGXJfGA==", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", "UTF-8")).append("=").append(URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&").append(URLEncoder.encode("numOfRows", "UTF-8")).append("=").append(URLEncoder.encode("3", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&").append(URLEncoder.encode("PRDLST_NM", "UTF-8")).append("=").append(URLEncoder.encode(String.valueOf(edit.getText()), "UTF-8")); /*품목명*/
        urlBuilder.append("&").append(URLEncoder.encode("type", "UTF-8")).append("=").append(URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default: xml*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();

        return sb.toString();
    }

}

