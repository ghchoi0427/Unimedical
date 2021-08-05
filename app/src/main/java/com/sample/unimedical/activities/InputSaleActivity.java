package com.sample.unimedical.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;
import com.sample.unimedical.adapter.SaleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.sample.unimedical.util.JsonFactory.createSaleItem;
import static com.sample.unimedical.util.RequestSender.sendEcountInputSaleRequest;
import static com.sample.unimedical.util.ResponseHandler.isSuccess;

public class InputSaleActivity extends AppCompatActivity {

    EditText uploadDate;
    EditText employeeCode;
    EditText ioType;
    EditText uploadSerialNumber;
    EditText productCode;
    EditText quantity;
    EditText price;
    EditText remarks;

    Button uploadSale;
    Button addSaleItem;
    SaleAdapter saleAdapter;
    RecyclerView recyclerViewSale;
    String SESSION_ID;
    String ZONE_CODE = "";
    JSONArray jsonArray = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_sale);

        uploadDate = findViewById(R.id.edit_upload_date);
        employeeCode = findViewById(R.id.edit_employee_code);
        ioType = findViewById(R.id.edit_io_type);
        uploadSerialNumber = findViewById(R.id.edit_upload_ser_no);
        productCode = findViewById(R.id.edit_prod_cd);
        quantity = findViewById(R.id.edit_qty);
        price = findViewById(R.id.edit_price);
        remarks = findViewById(R.id.edit_remarks);


        uploadSale = findViewById(R.id.btn_upload_sale);
        addSaleItem = findViewById(R.id.btn_add_sale_item);
        saleAdapter = new SaleAdapter();
        recyclerViewSale = findViewById(R.id.recyclerview_sale);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewSale.setLayoutManager(layoutManager);
        recyclerViewSale.setAdapter(saleAdapter);

        SESSION_ID = getIntent().getStringExtra("SESSION_ID");
        ZONE_CODE = getIntent().getStringExtra("ZONE_CODE");

        addSaleItem.setOnClickListener(v -> {
            try {
                JSONObject individualSaleItem = createSaleItem(
                        uploadSerialNumber.getText().toString(),
                        productCode.getText().toString(),
                        remarks.getText().toString(),
                        quantity.getText().toString(),
                        price.getText().toString());

                saleAdapter.addItem(individualSaleItem);
                jsonArray.put(individualSaleItem);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            saleAdapter.notifyDataSetChanged();

            productCode.setText("");
            quantity.setText("");
        });

        uploadSale.setOnClickListener(view -> new Thread(() -> {
            try {
                JSONObject response = new JSONObject(sendEcountInputSaleRequest(ZONE_CODE, SESSION_ID, jsonArray));
                if (isSuccess((JSONObject) response)) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "입력 완료", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start());

    }
}
