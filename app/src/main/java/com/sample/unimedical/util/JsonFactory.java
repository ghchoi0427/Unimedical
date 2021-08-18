package com.sample.unimedical.util;

import com.sample.unimedical.domain.stock.Stock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonFactory {

    private static final String ECOUNT_API_KEY_TEST = "1c094684933044c359e060feed0db93b31";
    private static final String ECOUNT_API_KEY = "1952f97f0a12f48f690c708ff9b28fe0b1";

    private static final String WH_CD = "00001";
    private static final String UPLOAD_SER_NO = "1";


    public static JSONObject getZoneJSONObject(String comCode) throws JSONException {
        return new JSONObject()
                .put("COM_CODE", comCode);
    }

    public static JSONObject createLoginJSONObject(String comCode, String userID) throws JSONException {
        return new JSONObject()
                .put("COM_CODE", comCode)
                .put("USER_ID", userID)
                .put("API_CERT_KEY", ECOUNT_API_KEY)
                .put("LAN_TYPE", "ko-KR")
                .put("ZONE", "BA");
    }

    public static JSONObject createSaleItem(String UPLOAD_SER_NO, String PROD_CD, String REMARKS, String QTY, String PRICE) throws JSONException {
        JSONObject Sale = new JSONObject();
        JSONObject BulkDatas = new JSONObject();

        BulkDatas.put("UPLOAD_SER_NO", UPLOAD_SER_NO)
                .put("PROD_CD", PROD_CD)
                .put("REMARKS", REMARKS)
                .put("QTY", QTY)
                .put("PRICE", PRICE);

        Sale.put("Line", "0");
        Sale.put("BulkDatas", BulkDatas);

        return Sale;
    }

    public static JSONObject saleList(JSONArray salesItems) throws JSONException {
        return new JSONObject().put("SaleList", salesItems);
    }

    public static List<Stock> JsonArrayToStockList(JSONArray jsonArray) throws JSONException {
        List<Stock> newList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Stock stock = new Stock();
            stock.setPROD_CD(jsonArray.getJSONObject(i).getString("PROD_CD"));
            stock.setBAL_QTY(jsonArray.getJSONObject(i).getString("BAL_QTY"));
            newList.add(stock);
        }
        return newList;
    }

}
