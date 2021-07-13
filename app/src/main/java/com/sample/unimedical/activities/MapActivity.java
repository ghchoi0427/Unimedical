package com.sample.unimedical.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;

import net.daum.mf.map.api.MapView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.IntStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MapActivity extends AppCompatActivity {

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    MapView mapView;
    RelativeLayout mapViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = new MapView(this);
        mapViewContainer = findViewById(R.id.mapView);
        mapViewContainer.addView(mapView);


        new Thread(() -> {
            try {
                processXML(getHospitalRequest());

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
        urlBuilder.append("&").append(URLEncoder.encode("yadmNm", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*병원명(UTF-8 인코딩 필요)*/
        urlBuilder.append("&").append(URLEncoder.encode("zipCd", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*분류코드(활용가이드 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("clCd", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*종별코드(활용가이드 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("dgsbjtCd", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*진료과목코드(활용가이드 참조)*/
        urlBuilder.append("&").append(URLEncoder.encode("xPos", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*x좌표(소수점 15)*/
        urlBuilder.append("&").append(URLEncoder.encode("yPos", "UTF-8")).append("=").append(URLEncoder.encode("", "UTF-8")); /*y좌표(소수점 15)*/
        urlBuilder.append("&").append(URLEncoder.encode("radius", "UTF-8")).append("=").append(URLEncoder.encode("3000", "UTF-8")); /*단위 : 미터(m)*/
        URL url = new URL(urlBuilder.toString());
        Log.d("test", url.toString());
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


        Log.d("test", sb.toString());
        return sb.toString();
    }

    protected void processXML(String str) throws ParserConfigurationException, IOException, SAXException {

        InputSource is = new InputSource(new StringReader(str));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);


        String s = "";
        NodeList nodeList = document.getElementsByTagName("item");

        for (int i = 0; i < nodeList.getLength(); i++) {

            Element nodeElement = (Element) nodeList.item(i);

            try {

                NodeList addr = nodeElement.getElementsByTagName("addr");
                s += "addr = " + addr.item(0).getChildNodes().item(0).getNodeValue() + "\n";

                NodeList yadmNm = nodeElement.getElementsByTagName("yadmNm");
                s += "yadmNm = " + yadmNm.item(0).getChildNodes().item(0).getNodeValue() + "\n";

                NodeList estbDd = nodeElement.getElementsByTagName("estbDd");
                s += "estbDd = " + estbDd.item(0).getChildNodes().item(0).getNodeValue() + "\n";

                NodeList hospUrl = nodeElement.getElementsByTagName("hospUrl");
                s += "hospUrl = " + hospUrl.item(0).getChildNodes().item(0).getNodeValue() + "\n";

                NodeList telno = nodeElement.getElementsByTagName("telno");
                s += "telno = " + telno.item(0).getChildNodes().item(0).getNodeValue() + "\n";

                NodeList XPos = nodeElement.getElementsByTagName("XPos");
                s += "XPos = " + XPos.item(0).getChildNodes().item(0).getNodeValue() + "\n";

                NodeList YPos = nodeElement.getElementsByTagName("YPos");
                s += "YPos = " + YPos.item(0).getChildNodes().item(0).getNodeValue() + "\n";
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Log.d("test", s);

        //IntStream.range(0, nodeList.getLength()).mapToObj(e->nodeList.item(e)).forEach(e->Log.d("test2",e.getC));
    }

}
