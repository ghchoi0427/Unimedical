package com.sample.unimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

import org.json.JSONException;

import java.io.IOException;

import static com.sample.unimedical.util.RequestSender.sendEcountInputSaleRequest;

public class InputSaleActivity extends AppCompatActivity {

    EditText uploadSerialNumber;
    EditText productCode;
    EditText quantity;
    Button uploadSale;
    TextView uploadSaleResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_sale);

        uploadSerialNumber = findViewById(R.id.edit_upload_ser_no);
        productCode = findViewById(R.id.edit_prod_cd);
        quantity = findViewById(R.id.edit_qty);
        uploadSale = findViewById(R.id.btn_upload_sale);
        uploadSaleResult = findViewById(R.id.text_ecount_sales_result);

        Intent secondIntent = getIntent();
        String SESSION_ID = secondIntent.getStringExtra("SESSION_ID").split(":")[1];
        Log.d("tester", SESSION_ID);

        uploadSale.setOnClickListener(view -> new Thread(() -> {
            try {
                String response = sendEcountInputSaleRequest(SESSION_ID, uploadSerialNumber.getText().toString(), productCode.getText().toString(), quantity.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start());

    }
}
