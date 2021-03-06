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
        private final TextView itemName;
        private final TextView primaryCode;
        private final TextView midClass;
        private final TextView midCode;
        private final TextView size;
        private final TextView unit;
        private final TextView maker;
        private final TextView material;
        private final TextView vendor;
        private final TextView priceMax;
        private final TextView update;
        private final TextView validFrom;
        private final TextView remark;

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
                primaryCode.setText(String.format("?????? : %s", device.getPrimaryCode()));
                midClass.setText(String.format("????????? : %s", device.getMidClass()));
                midCode.setText(String.format("??????????????? : %d", device.getMidCode()));
                size.setText(String.format("?????? : %s", device.getSize()));
                unit.setText(String.format("?????? : %s", device.getUnit()));
                maker.setText(String.format("???????????? : %s", device.getMaker()));
                material.setText(String.format("?????? : %s", device.getMaterial()));
                vendor.setText(String.format("???????????? : %s", device.getVendor()));
                priceMax.setText(String.format("???????????? : %s", device.getPriceMax()));
                update.setText(String.format("??????????????? : %s", device.getUpdate()));
                validFrom.setText(String.format("???????????? : %s", device.getValidFrom()));
                remark.setText(String.format("?????? : %s", device.getRemark()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
