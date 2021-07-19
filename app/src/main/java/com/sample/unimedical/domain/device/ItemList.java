package com.sample.unimedical.domain.device;

import java.util.ArrayList;

public class ItemList {
    ArrayList<Device> devices = new ArrayList<>();

    public ArrayList<Device> getItems() {
        return devices;
    }

    public Device getItem(int index) {
        return devices.get(index);
    }

    public Device getLastElement() {
        return devices.get(devices.size() - 1);
    }
}