package com.sample.unimedical.request;

import java.util.ArrayList;

public class Body {

    private String pageNo;
    private String totalCount;
    private String numOfRows;
    private ArrayList<Item> items = new ArrayList<>();

    public String getPageNo() {
        return pageNo;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public String getNumOfRows() {
        return numOfRows;
    }

    public ArrayList<Item> getItems() {
        return items;
    }


}
