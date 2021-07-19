package com.sample.unimedical.domain;

import com.sample.unimedical.domain.hospital.Hospital;

import net.daum.mf.map.api.MapPOIItem;

public class HospitalPoiBind {
    Hospital hospitalItem;
    MapPOIItem mapPOIItem;

    public HospitalPoiBind() {
    }

    public HospitalPoiBind(Hospital hospitalItem, MapPOIItem mapPOIItem) {
        this.hospitalItem = hospitalItem;
        this.mapPOIItem = mapPOIItem;
    }

    public Hospital getHospitalItem() {
        return hospitalItem;
    }

    public void setHospitalItem(Hospital hospitalItem) {
        this.hospitalItem = hospitalItem;
    }

    public MapPOIItem getMapPOIItem() {
        return mapPOIItem;
    }

    public void setMapPOIItem(MapPOIItem mapPOIItem) {
        this.mapPOIItem = mapPOIItem;
    }
}
