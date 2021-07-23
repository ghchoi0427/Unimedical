package com.sample.unimedical.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;
import com.sample.unimedical.adapter.SaleAdapter;

import org.json.JSONException;

import java.io.IOException;

import static com.sample.unimedical.util.RequestSender.sendEcountInputSaleRequest;

public class InputSaleActivity extends AppCompatActivity {

    EditText uploadSerialNumber;
    EditText productCode;
    EditText quantity;
    Button uploadSale;
    Button addSaleItem;
    SaleAdapter saleAdapter;
    RecyclerView recyclerViewSale;
    String SESSION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_sale);

        uploadSerialNumber = findViewById(R.id.edit_upload_ser_no);
        productCode = findViewById(R.id.edit_prod_cd);
        quantity = findViewById(R.id.edit_qty);
        uploadSale = findViewById(R.id.btn_upload_sale);
        addSaleItem = findViewById(R.id.btn_add_sale_item);
        saleAdapter = new SaleAdapter();
        recyclerViewSale = findViewById(R.id.recyclerview_sale);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewSale.setLayoutManager(layoutManager);
        recyclerViewSale.setAdapter(saleAdapter);

        SESSION_ID = getIntent().getStringExtra("SESSION_ID");

        uploadSale.setOnClickListener(view -> new Thread(() -> {
            try {

                String response = sendEcountInputSaleRequest(SESSION_ID, uploadSerialNumber.getText().toString(), productCode.getText().toString(), quantity.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start());

        addSaleItem.setOnClickListener(v -> {
            //JSONObject js = saleItem();
            //saleAdapter.addItem(new SaleItem());
            saleAdapter.notifyDataSetChanged();

            uploadSerialNumber.setText("");
            productCode.setText("");
            quantity.setText("");
        });

    }
}
