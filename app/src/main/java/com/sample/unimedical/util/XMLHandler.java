package com.sample.unimedical.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;

import com.sample.unimedical.domain.hospital.Hospital;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLHandler {

    public static List<Hospital> parseXML(String str) throws ParserConfigurationException, IOException, SAXException {    //비거래처

        List<Hospital> newList = new ArrayList<>();

        InputSource is = new InputSource(new StringReader(str));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);


        String s = "";
        NodeList nodeList = document.getElementsByTagName("item");

        for (int i = 0; i < nodeList.getLength(); i++) {

            Element nodeElement = (Element) nodeList.item(i);
            Hospital hospital = new Hospital();
            try {

                NodeList yadmNm = nodeElement.getElementsByTagName("yadmNm");   //병원명
                hospital.setYadmNm(yadmNm.item(0).getChildNodes().item(0).getNodeValue());

                NodeList mdeptGdrCnt = nodeElement.getElementsByTagName("mdeptGdrCnt");   //의과일반의 총 수
                hospital.setMdeptGdrCnt(mdeptGdrCnt.item(0).getChildNodes().item(0).getNodeValue());

                NodeList telno = nodeElement.getElementsByTagName("telno");
                hospital.setTelno(telno.item(0).getChildNodes().item(0).getNodeValue());

                NodeList XPos = nodeElement.getElementsByTagName("XPos");
                hospital.setXPos(XPos.item(0).getChildNodes().item(0).getNodeValue());

                NodeList YPos = nodeElement.getElementsByTagName("YPos");
                hospital.setYPos(YPos.item(0).getChildNodes().item(0).getNodeValue());


                NodeList clCd = nodeElement.getElementsByTagName("clCd");
                hospital.setClCd(clCd.item(0).getChildNodes().item(0).getNodeValue());  // [01 상급종합: 노랑 ] [11 종합 21 병원 : 초록] [31 의원: 파랑]

                NodeList sgguCdNm = nodeElement.getElementsByTagName("sgguCdNm");
                hospital.setSgguCdNm(sgguCdNm.item(0).getChildNodes().item(0).getNodeValue());

            } catch (Exception e) {
                e.printStackTrace();
            }

            newList.add(hospital);

        }

        return newList;

    }

    public static String readHospitalList(String city, String sigungu, String dongeupmyun, Context context) throws Exception {
        return null;
    }

}
