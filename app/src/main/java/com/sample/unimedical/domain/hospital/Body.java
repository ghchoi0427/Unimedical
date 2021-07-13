package com.sample.unimedical.domain.hospital;

public class Body {

    private Items items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    public Items getItems() {
        return items;
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
