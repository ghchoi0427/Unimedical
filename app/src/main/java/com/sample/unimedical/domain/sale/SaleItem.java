package com.sample.unimedical.domain.sale;

import org.json.JSONObject;

public class SaleItem {
    private JSONObject jsonObject;

    public SaleItem(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
