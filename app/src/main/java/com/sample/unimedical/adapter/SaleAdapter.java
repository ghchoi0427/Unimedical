package com.sample.unimedical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {
    ArrayList<JSONObject> saleItems = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View saleView = inflater.inflate(R.layout.sale_item, parent, false);

        return new ViewHolder(saleView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SaleAdapter.ViewHolder holder, int position) {
        JSONObject saleItem = saleItems.get(position);
        holder.setItem(saleItem);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void addItem(JSONObject saleItem) {
        saleItems.add(saleItem);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textSaleItemCode;
        TextView textSaleItemQuantity;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            textSaleItemCode = itemView.findViewById(R.id.text_sale_item_code);
            textSaleItemQuantity = itemView.findViewById(R.id.text_sale_item_quantity);
        }

        public void setItem(JSONObject saleItem) {
            try {
                JSONObject bulkDatas = (JSONObject) saleItem.get("BulkDatas");
                textSaleItemCode.setText(bulkDatas.getString("PROD_CD"));
                textSaleItemQuantity.setText(bulkDatas.getString("QTY"));
            } catch (Exception e) {

            }
        }
    }


}
