package com.sample.unimedical.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonFactory {

    private static final String ECOUNT_API_KEY_TEST = "3e533429870154db68acac0e662825e193";
    private static final String ECOUNT_API_KEY = "1952f97f0a12f48f690c708ff9b28fe0b1";
    private static final String WH_CD = "00001";

    public static JSONObject getZoneJSONObject(String comCode) throws JSONException {
        return new JSONObject()
                .put("COM_CODE", comCode);
    }

    public static JSONObject createLoginJSONObject(String comCode, String userID) throws JSONException {
        return new JSONObject()
                .put("COM_CODE", comCode)
                .put("USER_ID", userID)
                .put("API_CERT_KEY", ECOUNT_API_KEY_TEST)
                .put("LAN_TYPE", "ko-KR")
                .put("ZONE", "BA");
    }

    public static JSONObject createInputSaleJSONObject(String UPLOAD_SER_NO, String PROD_CD, String QTY) throws JSONException {
        String str = "{\n" +
                "  \"SaleList\": [\n" +
                "    {\n" +
                "      \"Line\": \"0\",\n" +
                "      \"BulkDatas\": {\n" +
                "        \"UPLOAD_SER_NO\": \"" + UPLOAD_SER_NO + "\",\n" +
                "        \"PROD_CD\": \"" + PROD_CD + "\",\n" +
                "        \"QTY\": \"" + QTY + "\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return new JSONObject(str);
    }
}
