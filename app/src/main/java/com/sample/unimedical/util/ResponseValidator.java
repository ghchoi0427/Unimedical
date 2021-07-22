package com.sample.unimedical.util;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseValidator {

    public static boolean validateJSON(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonChildObject = (JSONObject) jsonObject.get("Data");

        return jsonChildObject.getString("Code").equals("00");
    }
}
