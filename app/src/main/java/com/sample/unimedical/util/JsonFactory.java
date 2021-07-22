package com.sample.unimedical.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonFactory {
    private static final String ECOUNT_API_KEY = "3e533429870154db68acac0e662825e193";

    public static JSONObject createJSONObject(String comCode, String userID) throws JSONException {
        return new JSONObject()
                .put("COM_CODE", comCode)
                .put("USER_ID", userID)
                .put("API_CERT_KEY", ECOUNT_API_KEY)
                .put("LAN_TYPE", "ko-KR")
                .put("ZONE", "BA");
    }
}
