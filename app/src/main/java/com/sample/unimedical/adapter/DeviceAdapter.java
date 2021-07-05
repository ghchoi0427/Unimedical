package com.sample.unimedical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;
import com.sample.unimedical.device.Item;

import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    ArrayList<Item> items = new ArrayList<>();

    public ArrayList<Item> getItems() {
        return items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.device_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder viewHolder, int position) {
        Item item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public void clearItem(){
        items.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPurpose;

        public ViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.textItemName);
            itemPurpose = itemView.findViewById(R.id.textItemPurpose);
        }

        public void setItem(Item item) {
            itemName.setText(item.getPRDLST_NM());
            itemPurpose.setText(item.getUSE_PURPS_CONT());
        }
    }
}
