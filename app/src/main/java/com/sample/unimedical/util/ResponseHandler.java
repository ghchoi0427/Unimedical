package com.sample.unimedical.util;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseHandler {

    public static boolean validateJSON(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonChildObject = (JSONObject) jsonObject.get("Data");

        return jsonChildObject.getString("Code").equals("00");
    }

    public static String getErrorMessage(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonChildObject = (JSONObject) jsonObject.get("Data");

        return jsonChildObject.getString("Message");
    }

    public static String getSessionID(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject Data = (JSONObject) jsonObject.get("Data");
        JSONObject Datas = (JSONObject) Data.get("Datas");

        return Datas.getString("SESSION_ID");
    }
}
