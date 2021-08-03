package com.sample.unimedical.domain.stock;

public class Stock {

    private String PROD_CD;
    private String BAL_QTY;

    public String getPROD_CD() {
        return PROD_CD;
    }

    public void setPROD_CD(String PROD_CD) {
        this.PROD_CD = PROD_CD;
    }

    public String getBAL_QTY() {
        return BAL_QTY;
    }

    public void setBAL_QTY(String BAL_QTY) {
        this.BAL_QTY = BAL_QTY;
    }
}
