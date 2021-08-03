package com.sample.unimedical.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.stock.ProductCode;
import com.sample.unimedical.domain.stock.Stock;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {
    public ArrayList<Stock> stocks = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View stockView = layoutInflater.inflate(R.layout.stock_item, parent, false);

        return new ViewHolder(stockView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdapter.ViewHolder holder, int position) {
        Stock stock = stocks.get(position);
        holder.setItem(stock);
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public void addItem(Stock stock) {
        stocks.add(stock);
    }

    public void addItems(JSONArray jsonArray) throws JSONException {

        for (int i = 0; i < jsonArray.length(); i++) {
            Stock stock = new Stock();
            stock.setPROD_CD(jsonArray.getJSONObject(i).getString("PROD_CD"));
            stock.setBAL_QTY(jsonArray.getJSONObject(i).getString("BAL_QTY"));

            stocks.add(stock);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textStockName;
        TextView textStockCount;

        public ViewHolder(@NonNull View stockView) {
            super(stockView);
            textStockCount = stockView.findViewById(R.id.text_stock_name);
            textStockCount = stockView.findViewById(R.id.text_stock_count);
        }

        public void setItem(Stock stock) {

            textStockName.setText(mapper(stock.getPROD_CD()));
            textStockCount.setText(stock.getBAL_QTY());
        }

        private String mapper(String key) {
            ProductCode productCode = new ProductCode();
            return productCode.getProductCodeMap().get(key);
        }
    }
}
