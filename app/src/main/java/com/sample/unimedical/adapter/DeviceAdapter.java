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
    static final String textSerialNumber = "시리얼번호 : ";
    static final String textInstitution = "기관지역구분 : ";
    static final String textItemNumber = "의료기기품목허가번호 : ";

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
        TextView itemPurpose;
        TextView serialNumber;
        TextView institution;
        TextView itemNumber;

        public ViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.text_item_name);
            itemPurpose = itemView.findViewById(R.id.text_item_purpose);
            serialNumber = itemView.findViewById(R.id.text_serial_number);
            institution = itemView.findViewById(R.id.text_inst_div);
            itemNumber = itemView.findViewById(R.id.text_item_number);
        }

        @SuppressLint("SetTextI18n")
        public void setItem(Item item) {
            itemName.setText(item.getPRDLST_NM());
            itemPurpose.setText(item.getUSE_PURPS_CONT());
            serialNumber.setText(textSerialNumber + item.getMDEQ_PRDLST_SN());
            serialNumber.setText(textInstitution + item.getINST_AREA_DIVS_NM());
            itemNumber.setText(textItemNumber + item.getMEDDEV_ITEM_NO());
        }
    }
}
