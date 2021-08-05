package com.sample.unimedical.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.sample.unimedical.util.JsonFactory.createLoginJSONObject;
import static com.sample.unimedical.util.JsonFactory.getZoneJSONObject;
import static com.sample.unimedical.util.JsonFactory.saleList;

public class RequestSender {

    private static final String TEST_URL_PREFIX = "https://sboapi";
    private static final String URL_PREFIX = "https://oapi";
    private static final String URL_SUFFIX = ".ecount.com/OAPI/V2/";
    private static final String URL_ZONE = "ZONE";
    private static final String URL_LOGIN = "OAPILogin";
    private static final String URL_INPUTSALE = "Sale/SaveSale?SESSION_ID=";
    private static final String URL_STOCK = "InventoryBalance/GetListInventoryBalanceStatus?SESSION_ID=";

    public static String sendEcountZoneRequest(String comCode) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(URL_PREFIX + URL_SUFFIX + URL_ZONE); /*URL*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");

        JSONObject jsonObject = getZoneJSONObject(comCode);

        conn.setDoOutput(true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(jsonObject.toString());
        bw.flush();
        bw.close();
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            conn.disconnect();
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendEcountLoginRequest(String zoneCode, String comCode, String userID) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(URL_PREFIX + zoneCode + URL_SUFFIX + URL_LOGIN); /*URL*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");

        JSONObject jsonObject = createLoginJSONObject(comCode, userID);

        conn.setDoOutput(true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(jsonObject.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        conn.disconnect();
        return sb.toString();
    }

    public static String sendEcountInputSaleRequest(String zoneCode, String SESSION_ID, JSONArray jsonArray) throws IOException, JSONException {
        StringBuilder urlBuilder = new StringBuilder(URL_PREFIX + zoneCode + URL_SUFFIX + URL_INPUTSALE + SESSION_ID); /*URL*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");

        JSONObject saleItem = saleList(jsonArray);

        conn.setDoOutput(true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(saleItem.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        conn.disconnect();

        return sb.toString();
    }

    private static String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static String sendEcountStockRequest(String zoneCode, String SESSION_ID) throws IOException, JSONException {
        StringBuilder urlBuilder = new StringBuilder(URL_PREFIX + zoneCode + URL_SUFFIX + URL_STOCK + SESSION_ID); /*URL*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");

        JSONObject parameter = new JSONObject()
                .put("PROD_CD", "")
                .put("WH_CD", "")
                .put("BASE_DATE", getFormattedDate());

        conn.setDoOutput(true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(parameter.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        conn.disconnect();

        return sb.toString();
    }
}
