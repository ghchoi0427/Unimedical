package com.sample.unimedical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.stock.Stock;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.sample.unimedical.util.DataMapper.getProductNameByCode;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {
    public ArrayList<Stock> stocks = new ArrayList<>();
    private static final String PRODUCT_NAME = "품목명: ";
    private static final String PRODUCT_QUANTITY = "재고수량: ";

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

    public void clearItem() {
        stocks.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textStockName;
        TextView textStockCount;

        public ViewHolder(@NonNull View stockView) {
            super(stockView);
            textStockName = stockView.findViewById(R.id.text_stock_name);
            textStockCount = stockView.findViewById(R.id.text_stock_count);
        }

        public void setItem(Stock stock) {
            try {
                if (!stock.getPROD_CD().isEmpty()) {
                    textStockName.setText(String.format("%s%s", PRODUCT_NAME, mapper(stock.getPROD_CD())));
                    textStockCount.setText(String.format("%s%s", PRODUCT_QUANTITY, stock.getBAL_QTY()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String mapper(String key) {
            return getProductNameByCode().get(key);
        }
    }
}
