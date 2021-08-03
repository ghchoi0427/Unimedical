package com.sample.unimedical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.stock.ProductCode;
import com.sample.unimedical.domain.stock.Stock;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {
    public ArrayList<Stock> stockArrayList = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public StockAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View stockView = layoutInflater.inflate(R.layout.stock_item, parent, false);

        return new ViewHolder(stockView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StockAdapter.ViewHolder holder, int position) {
        Stock stock = stockArrayList.get(position);
        holder.setItem(stock);
    }

    @Override
    public int getItemCount() {
        return stockArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textStockName;
        TextView textStockCount;

        public ViewHolder(@NonNull @NotNull View stockView) {
            super(stockView);
            textStockCount = stockView.findViewById(R.id.text_stock_name);
            textStockCount = stockView.findViewById(R.id.text_stock_count);
        }

        public void setItem(Stock stock) {

            textStockName.setText(mapper(stock.getPROD_CD()));
            textStockName.setText(stock.getBAL_QTY());
        }

        private String mapper(String key) {
            ProductCode productCode = new ProductCode();
            return productCode.getProductCodeMap().get(key);
        }
    }
}
