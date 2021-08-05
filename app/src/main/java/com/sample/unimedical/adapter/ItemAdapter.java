package com.sample.unimedical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.device.Device;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    ArrayList<Device> devices = new ArrayList<>();

    public ArrayList<Device> getItems() {
        return devices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.device_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder viewHolder, int position) {
        Device device = devices.get(position);
        viewHolder.setItem(device);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void addItem(Device device) {
        devices.add(device);
    }

    public void setItems(ArrayList<Device> devices) {
        this.devices = devices;
    }

    public void clearItem() {
        devices.clear();
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

        public void setItem(Device device) {

            try {
                itemName.setText(device.getProductName());
                primaryCode.setText(String.format("코드 : %s", device.getPrimaryCode()));
                midClass.setText(String.format("중분류 : %s", device.getMidClass()));
                midCode.setText(String.format("중분류코드 : %d", device.getMidCode()));
                size.setText(String.format("규격 : %s", device.getSize()));
                unit.setText(String.format("단위 : %s", device.getUnit()));
                maker.setText(String.format("제조회사 : %s", device.getMaker()));
                material.setText(String.format("재질 : %s", device.getMaterial()));
                vendor.setText(String.format("수입업소 : %s", device.getVendor()));
                priceMax.setText(String.format("상한금액 : %s", device.getPriceMax()));
                update.setText(String.format("최초등재일 : %s", device.getUpdate()));
                validFrom.setText(String.format("적용일자 : %s", device.getValidFrom()));
                remark.setText(String.format("비고 : %s", device.getRemark()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
