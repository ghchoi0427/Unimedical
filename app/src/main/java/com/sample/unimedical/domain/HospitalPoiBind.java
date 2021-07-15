package com.sample.unimedical.domain;

import com.sample.unimedical.domain.hospital.Item;

import net.daum.mf.map.api.MapPOIItem;

public class HospitalPoiBind {
    Item hospitalItem;
    MapPOIItem mapPOIItem;

    public Item getHospitalItem() {
        return hospitalItem;
    }

    public void setHospitalItem(Item hospitalItem) {
        this.hospitalItem = hospitalItem;
    }

    public MapPOIItem getMapPOIItem() {
        return mapPOIItem;
    }

    public void setMapPOIItem(MapPOIItem mapPOIItem) {
        this.mapPOIItem = mapPOIItem;
    }
}
