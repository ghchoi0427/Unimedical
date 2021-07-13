package com.sample.unimedical.domain.device;

import java.util.ArrayList;

public class ItemList {
    ArrayList<Item> items = new ArrayList<>();

    public ArrayList<Item> getItems() {
        return items;
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public Item getLastElement() {
        return items.get(items.size() - 1);
    }
}