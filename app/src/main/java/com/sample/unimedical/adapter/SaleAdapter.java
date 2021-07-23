package com.sample.unimedical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.sale.SaleItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {
    ArrayList<SaleItem> saleItems = new ArrayList<>();

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
        SaleItem saleItem = saleItems.get(position);
        holder.setItem(saleItem);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void addItem(SaleItem saleItem) {
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

        public void setItem(SaleItem saleItem) {
            try {
                textSaleItemCode.setText("");
                textSaleItemQuantity.setText("");
            } catch (Exception e) {

            }
        }
    }


}
