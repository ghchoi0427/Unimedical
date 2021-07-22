package com.sample.unimedical.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonFactory {

    private static final String ECOUNT_API_KEY = "3e533429870154db68acac0e662825e193";
    private static final String WH_CD = "00001";

    public static JSONObject createLoginJSONObject(String comCode, String userID) throws JSONException {
        return new JSONObject()
                .put("COM_CODE", comCode)
                .put("USER_ID", userID)
                .put("API_CERT_KEY", ECOUNT_API_KEY)
                .put("LAN_TYPE", "ko-KR")
                .put("ZONE", "BA");
    }

    public static JSONObject createInputSaleJSONObject(String SESSION_ID, String UPLOAD_SER_NO, String PROD_CD, String QTY) throws JSONException {
        return new JSONObject()
                .put("SESSION_ID", SESSION_ID)
                .put("UPLOAD_SER_NO", UPLOAD_SER_NO)
                .put("WH_CD", WH_CD)
                .put("PROD_CD", PROD_CD)
                .put("QTY", QTY);
    }
}
