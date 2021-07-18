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

    public Device getItem(int position) {
        return devices.get(position);
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
                primaryCode.setText("코드 : "+ device.getPrimaryCode());
                midClass.setText("중분류 : "+ device.getMidClass());
                midCode.setText("중분류코드 : "+ device.getMidCode() + "");
                size.setText("규격 : "+ device.getSize());
                unit.setText("단위 : "+ device.getUnit());
                maker.setText("제조회사 : "+ device.getMaker());
                material.setText("재질 : "+ device.getMaterial());
                vendor.setText("수입업소 : "+ device.getVendor());
                priceMax.setText("상한금액 : "+ device.getPriceMax());
                update.setText("최초등재일 : "+ device.getUpdate());
                validFrom.setText("적용일자 : "+ device.getValidFrom());
                remark.setText("비고 : "+ device.getRemark());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
