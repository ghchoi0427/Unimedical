package com.sample.unimedical.domain.hospital;

public class Body {

    private Hospitals hospitals;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    public Hospitals getItems() {
        return hospitals;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
