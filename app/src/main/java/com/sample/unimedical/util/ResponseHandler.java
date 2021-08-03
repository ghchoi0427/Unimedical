package com.sample.unimedical.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseHandler {

    public static boolean validateLoginJSON(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonChildObject = (JSONObject) jsonObject.get("Data");

        return jsonChildObject.getString("Code").equals("00");
    }

    public static String getZoneCode(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject Data = (JSONObject) jsonObject.get("Data");

        return Data.getString("ZONE");
    }

    public static String getErrorMessage(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonChildObject = (JSONObject) jsonObject.get("Data");

        return jsonChildObject.getString("Message");
    }

    public static JSONArray getProductResults(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonData = (JSONObject) jsonObject.get("Data");
        JSONArray result = (JSONArray) jsonData.get("Result");
        return result;
    }

    public static String getSessionID(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject Data = (JSONObject) jsonObject.get("Data");
        JSONObject Datas = (JSONObject) Data.get("Datas");

        return Datas.getString("SESSION_ID");
    }

    public static String getProductCode(JSONObject jsonObject) throws JSONException {
        JSONObject BulkDatas = (JSONObject) jsonObject.get("BulkDatas");
        return BulkDatas.getString("PROD_CD");
    }

    public static String getProductQuantity(JSONObject jsonObject) throws JSONException {
        JSONObject BulkDatas = (JSONObject) jsonObject.get("BulkDatas");
        return BulkDatas.getString("QTY");
    }
}
