package com.sample.unimedical.activities;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

import net.daum.mf.map.api.MapView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MapActivity extends AppCompatActivity {

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    MapView mapView;
    RelativeLayout mapViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        try {
            mapView = new MapView(this);
            mapViewContainer = findViewById(R.id.mapView);
            mapViewContainer.addView(mapView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                getHospitalRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }


    private String getHospitalRequest() throws Exception {

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551182/hospInfoService1/getHospBasisList1"); /*URL*/
        urlBuilder.append("?").append(URLEncoder.encode("ServiceKey", "UTF-8")); /*Service Key*/
        urlBuilder.append("=").append(URLEncoder.encode("uhPZ+yjcUrJD5qN1Q6Wf1+o63BmTtVFSTTKYCRPT0JY7HN934bPpj4S5f2QQng+LHjCADIGxjrHTUE0pGXJfGA==", "UTF-8")).append("&");
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", "UTF-8")).append("=").append(URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&").append(URLEncoder.encode("numOfRows", "UTF-8")).append("=").append(URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&").append(URLEncoder.encode("sidoCd", "UTF-8")).append("=").append(URLEncoder.encode("110000", "UTF-8")); /*시도코드*/
        urlBuilder.append("&").append(URLEncoder.encode("sgguCd", "UTF-8")).append("=").append(URLEncoder.encode("110019", "UTF-8")); /*시군구코드*/
        urlBuilder.append("&").append(URLEncoder.encode("emdongNm", "UTF-8")).append("=").append(URLEncoder.encode("신내동", "UTF-8")); /*읍면동명*/
        urlBuilder.append("&").append(URLEncoder.encode("yadmNm", "UTF-8")).append("=").append(URLEncoder.encode("서울의료원", "UTF-8")); /*병원명(UTF-8 인코딩 필요)*/
        urlBuilder.append("&").append(URLEncoder.encode("zipCd", "UTF-8")).append("=").append(URLEncoder.encode("2010", "UTF-8")); /*분류코드(활용가이드 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("clCd", "UTF-8")).append("=").append(URLEncoder.encode("11", "UTF-8")); /*종별코드(활용가이드 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("dgsbjtCd", "UTF-8")).append("=").append(URLEncoder.encode("01", "UTF-8")); /*진료과목코드(활용가이드 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("xPos", "UTF-8")).append("=").append(URLEncoder.encode("127.09854004628151", "UTF-8")); /*x좌표(소수점 15)*/
        urlBuilder.append("&").append(URLEncoder.encode("yPos", "UTF-8")).append("=").append(URLEncoder.encode("37.6132113197367", "UTF-8")); /*y좌표(소수점 15)*/
        urlBuilder.append("&").append(URLEncoder.encode("radius", "UTF-8")).append("=").append(URLEncoder.encode("3000", "UTF-8")); /*단위 : 미터(m)*/
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

    protected void onPostExecute(Document doc) {

        String s = "";
        NodeList nodeList = doc.getElementsByTagName("item");

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element firstElement = (Element) node;

            NodeList idx = firstElement.getElementsByTagName("idx");
            s += "idx = " + idx.item(0).getChildNodes().item(0).getNodeValue() + "\n";

            NodeList gugun = firstElement.getElementsByTagName("gugun");
            s += "gugun = " + gugun.item(0).getChildNodes().item(0).getNodeValue() + "\n";

            NodeList instt = firstElement.getElementsByTagName("instt");
            s += "instt = " + instt.item(0).getChildNodes().item(0).getNodeValue() + "\n";

            NodeList spot = firstElement.getElementsByTagName("spot");
            s += "spot = " + spot.item(0).getChildNodes().item(0).getNodeValue() + "\n";

            NodeList spotGubun = firstElement.getElementsByTagName("spotGubun");
            s += "spotGubun = " + spotGubun.item(0).getChildNodes().item(0).getNodeValue() + "\n";

            NodeList airPump = firstElement.getElementsByTagName("airPump");
            s += "airPump = " + airPump.item(0).getChildNodes().item(0).getNodeValue() + "\n";

            NodeList updtDate = firstElement.getElementsByTagName("updtDate");
            s += "updtDate = " + updtDate.item(0).getChildNodes().item(0).getNodeValue() + "\n";
        }
    }
}
