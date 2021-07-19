package com.sample.unimedical.domain.device;

import java.util.ArrayList;

public class ItemList {
    ArrayList<Device> items = new ArrayList<>();

    public ArrayList<Device> getItems() {
        return items;
    }

    public Device getItem(int index) {
        return items.get(index);
    }

    public Device getLastElement() {
        return items.get(items.size() - 1);
    }
}