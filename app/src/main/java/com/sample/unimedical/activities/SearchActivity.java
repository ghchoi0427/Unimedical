package com.sample.unimedical.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sample.unimedical.R;
import com.sample.unimedical.adapter.DeviceAdapter;
import com.sample.unimedical.device.Item;
import com.sample.unimedical.device.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.stream.Stream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class SearchActivity extends AppCompatActivity {
    EditText editText;
    Button searchButton;
    TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = findViewById(R.id.edit);
        searchButton = findViewById(R.id.btn_search_result);
        resultView = findViewById(R.id.imptextview);

        // readExcel();


        searchButton.setOnClickListener(v -> {
            readExcel(editText.getText().toString());
        });


    }


    private void readExcel() {
        try {
            InputStream is = getBaseContext().getResources().getAssets().open("device_data.xls");

            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);
                if (sheet != null) {
                    int colTotal = sheet.getColumns();
                    int rowIndexStart = 1;
                    int rowTotal = sheet.getColumn(colTotal - 1).length;

                    StringBuilder sb;

                    for (int row = rowIndexStart; row < rowTotal; row++) {
                        sb = new StringBuilder();

                        for (int col = 0; col < colTotal; col++) {
                            String contents = sheet.getCell(col, row).getContents();
                            Log.d("test", col + "번째: " + contents);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readExcel(String keyword) {
        try {
            print("keyword= " + keyword);
            print("flag1");
            InputStream is = getBaseContext().getResources().getAssets().open("meddev_list.xls");
            print("flag2");

            Workbook wb = Workbook.getWorkbook(is);
            print("flag3");

            if (wb != null) {
                print("flag4");
                Sheet sheet = wb.getSheet(0);
                print("flag5");
                if (sheet != null) {
                    print("flag6");
                    int colTotal = sheet.getColumns();
                    print("flag7");
                    int rowIndexStart = 1;
                    print("flag8");
                    int rowTotal = sheet.getColumn(colTotal - 1).length;
                    print("flag9");

                    StringBuilder sb = null;
                    print("flag10");

                    print(rowIndexStart+"");
                    print(rowTotal+"");
                    print(colTotal+"");

                    for (int row = rowIndexStart; row < rowTotal; row++) {
                        print("flag11");
                        sb = new StringBuilder();

                        for (int col = 0; col < colTotal; col++) {
                            String contents = sheet.getCell(col, row).getContents();

                            //Log.d("test2", col + " : " + contents);
                            //print("contents="+contents);

                            if (contents.contains(keyword)) {
                               for(Cell c :sheet.getRow(row)){
                                   printTextView(c.getContents());
                               }
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printTextView(String s){
        //resultView.setText("");
        resultView.append(s);
    }

    private void print(String s) {
        Log.d("test1", s);
    }

}