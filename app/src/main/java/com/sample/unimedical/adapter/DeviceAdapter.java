package com.sample.unimedical.adapter;

import android.annotation.SuppressLint;
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

    public void clearItem() {
        items.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView primaryCode;
        TextView midClass;
        TextView midCode;
        TextView size;
        TextView unit;
        TextView maker;
        TextView material;
        TextView vendor;
        TextView priceMax;
        TextView update;
        TextView validFrom;
        TextView remark;

        public ViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.text_productname);
            primaryCode = itemView.findViewById(R.id.text_primary);
            midClass = itemView.findViewById(R.id.text_midclass);
            midCode = itemView.findViewById(R.id.text_midcode);
            size = itemView.findViewById(R.id.text_size);
            unit = itemView.findViewById(R.id.text_unit);
            maker = itemView.findViewById(R.id.text_maker);
            material = itemView.findViewById(R.id.text_material);
            vendor = itemView.findViewById(R.id.text_vendor);
            priceMax = itemView.findViewById(R.id.text_pricemax);
            update = itemView.findViewById(R.id.text_update);
            validFrom = itemView.findViewById(R.id.text_validfrom);
            remark = itemView.findViewById(R.id.text_remark);

        }

        public void setItem(Item item) {

            try {
                itemName.setText(item.getProductName());
                primaryCode.setText(item.getPrimaryCode());
                midClass.setText(item.getMidClass());
                midCode.setText(item.getMidCode() + "");
                size.setText(item.getSize());
                unit.setText(item.getUnit());
                maker.setText(item.getMaker());
                material.setText(item.getMaterial());
                vendor.setText(item.getVendor());
                priceMax.setText(item.getPriceMax());
                update.setText(item.getUpdate());
                validFrom.setText(item.getValidFrom());
                remark.setText(item.getRemark());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
