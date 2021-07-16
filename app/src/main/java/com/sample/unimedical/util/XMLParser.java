package com.sample.unimedical.util;

import android.util.Log;

import com.sample.unimedical.domain.hospital.Item;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {

    public static List<Item> processXML(String str) throws ParserConfigurationException, IOException, SAXException {    //비거래처

        List<Item> newList = new ArrayList<>();

        InputSource is = new InputSource(new StringReader(str));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);


        String s = "";
        NodeList nodeList = document.getElementsByTagName("item");

        for (int i = 0; i < nodeList.getLength(); i++) {

            Element nodeElement = (Element) nodeList.item(i);
            Item item = new Item();
            try {

                NodeList yadmNm = nodeElement.getElementsByTagName("yadmNm");   //병원명
                item.setYadmNm(yadmNm.item(0).getChildNodes().item(0).getNodeValue());

                NodeList mdeptGdrCnt = nodeElement.getElementsByTagName("mdeptGdrCnt");   //의과일반의 총 수
                item.setMdeptGdrCnt(mdeptGdrCnt.item(0).getChildNodes().item(0).getNodeValue());

                NodeList telno = nodeElement.getElementsByTagName("telno");
                item.setTelno(telno.item(0).getChildNodes().item(0).getNodeValue());

                NodeList XPos = nodeElement.getElementsByTagName("XPos");
                item.setXPos(XPos.item(0).getChildNodes().item(0).getNodeValue());

                NodeList YPos = nodeElement.getElementsByTagName("YPos");
                item.setYPos(YPos.item(0).getChildNodes().item(0).getNodeValue());

                NodeList clCd = nodeElement.getElementsByTagName("clCd");
                item.setClCd(clCd.item(0).getChildNodes().item(0).getNodeValue());  // [01 상급종합: 노랑 ] [11 종합 21 병원 : 초록] [31 의원: 파랑]


            } catch (Exception e) {
                e.printStackTrace();
            }

            newList.add(item);

        }

        return newList;

    }
}
