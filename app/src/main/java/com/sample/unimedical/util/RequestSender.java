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

    private static final String API_KEY = "uhPZ+yjcUrJD5qN1Q6Wf1+o63BmTtVFSTTKYCRPT0JY7HN934bPpj4S5f2QQng+LHjCADIGxjrHTUE0pGXJfGA==";
    private static final String DIAGNOSE_CODE = "05";

    private static final String TEST_URL_PREFIX = "https://sboapi";
    private static final String URL_PREFIX = "https://oapi";
    private static final String URL_SUFFIX = ".ecount.com/OAPI/V2/";
    private static final String URL_ZONE = "ZONE";
    private static final String URL_LOGIN = "OAPILogin";
    private static final String URL_INPUTSALE = "Sale/SaveSale?SESSION_ID=";
    private static final String URL_STOCK = "InventoryBalance/GetListInventoryBalanceStatus?SESSION_ID=";

    public static String sendHospitalRequest(String hospitalName) throws Exception {

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551182/hospInfoService1/getHospBasisList1"); /*URL*/
        urlBuilder.append("?").append(URLEncoder.encode("ServiceKey", "UTF-8")); /*Service Key*/
        urlBuilder.append("=").append(URLEncoder.encode(API_KEY, "UTF-8")).append("&");
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", "UTF-8")).append("=").append(URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&").append(URLEncoder.encode("numOfRows", "UTF-8")).append("=").append(URLEncoder.encode("500", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&").append(URLEncoder.encode("sidoCd", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*시도코드*/
        urlBuilder.append("&").append(URLEncoder.encode("sgguCd", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*시군구코드*/
        urlBuilder.append("&").append(URLEncoder.encode("emdongNm", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*읍면동명*/
        urlBuilder.append("&").append(URLEncoder.encode("yadmNm", "UTF-8")).append("=").append(URLEncoder.encode(hospitalName, "UTF-8")); /*병원명(UTF-8 인코딩 필요)*/
        urlBuilder.append("&").append(URLEncoder.encode("zipCd", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*분류코드(활용가이드 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("clCd", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*종별코드(활용가이드 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("dgsbjtCd", "UTF-8")).append("=").append(URLEncoder.encode(DIAGNOSE_CODE, "UTF-8")); /*진료과목코드(활용가이드 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("xPos", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*x좌표(소수점 15)*/
        urlBuilder.append("&").append(URLEncoder.encode("yPos", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*y좌표(소수점 15)*/
        urlBuilder.append("&").append(URLEncoder.encode("radius", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*단위 : 미터(m)*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb.toString();
    }

    public static String sendHospitalRequest(double xPos, double yPos, int radius) throws Exception {

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551182/hospInfoService1/getHospBasisList1"); /*URL*/
        urlBuilder.append("?").append(URLEncoder.encode("ServiceKey", "UTF-8")); /*Service Key*/
        urlBuilder.append("=").append(URLEncoder.encode(API_KEY, "UTF-8")).append("&");
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", "UTF-8")).append("=").append(URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&").append(URLEncoder.encode("numOfRows", "UTF-8")).append("=").append(URLEncoder.encode("11480", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&").append(URLEncoder.encode("sidoCd", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*시도코드*/
        urlBuilder.append("&").append(URLEncoder.encode("sgguCd", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*시군구코드*/
        urlBuilder.append("&").append(URLEncoder.encode("emdongNm", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*읍면동명*/
        urlBuilder.append("&").append(URLEncoder.encode("yadmNm", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*병원명(UTF-8 인코딩 필요)*/
        urlBuilder.append("&").append(URLEncoder.encode("zipCd", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*분류코드(활용가이드 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("clCd", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*종별코드(활용가이드 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("dgsbjtCd", "UTF-8")).append("=").append(URLEncoder.encode(DIAGNOSE_CODE, "UTF-8")); /*진료과목코드(활용가이드 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("xPos", "UTF-8")).append("=").append(URLEncoder.encode(String.valueOf(yPos), "UTF-8")); /*x좌표(소수점 15)*/
        urlBuilder.append("&").append(URLEncoder.encode("yPos", "UTF-8")).append("=").append(URLEncoder.encode(String.valueOf(xPos), "UTF-8")); /*y좌표(소수점 15)*/
        urlBuilder.append("&").append(URLEncoder.encode("radius", "UTF-8")).append("=").append(URLEncoder.encode(String.valueOf(radius), "UTF-8")); /*단위 : 미터(m)*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        return sb.toString();
    }


    public static String sendEcountZoneRequest(String comCode) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(TEST_URL_PREFIX + URL_SUFFIX + URL_ZONE); /*URL*/
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
        StringBuilder urlBuilder = new StringBuilder(TEST_URL_PREFIX + zoneCode + URL_SUFFIX + URL_LOGIN); /*URL*/
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
        StringBuilder urlBuilder = new StringBuilder(TEST_URL_PREFIX + zoneCode + URL_SUFFIX + URL_INPUTSALE + SESSION_ID); /*URL*/
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
        StringBuilder urlBuilder = new StringBuilder(TEST_URL_PREFIX + zoneCode + URL_SUFFIX + URL_STOCK + SESSION_ID); /*URL*/
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
