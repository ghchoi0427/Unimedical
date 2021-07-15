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
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {

    public void processXML(String str, List<Item> hospitalItems) throws ParserConfigurationException, IOException, SAXException {

        InputSource is = new InputSource(new StringReader(str));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);


        String s = "";
        NodeList nodeList = document.getElementsByTagName("item");

        for (int i = 0; i < nodeList.getLength(); i++) {

            Element nodeElement = (Element) nodeList.item(i);
            Item item = new Item();
            try {

                NodeList addr = nodeElement.getElementsByTagName("addr");
                item.setAddr(addr.item(0).getChildNodes().item(0).getNodeValue());

                NodeList yadmNm = nodeElement.getElementsByTagName("yadmNm");
                item.setYadmNm(yadmNm.item(0).getChildNodes().item(0).getNodeValue());

                NodeList estbDd = nodeElement.getElementsByTagName("estbDd");
                item.setEstbDd(estbDd.item(0).getChildNodes().item(0).getNodeValue());

                NodeList hospUrl = nodeElement.getElementsByTagName("hospUrl");
                item.setHospUrl(hospUrl.item(0).getChildNodes().item(0).getNodeValue());

                NodeList telno = nodeElement.getElementsByTagName("telno");
                item.setTelno(telno.item(0).getChildNodes().item(0).getNodeValue());

                NodeList XPos = nodeElement.getElementsByTagName("XPos");
                item.setXPos(XPos.item(0).getChildNodes().item(0).getNodeValue());

                NodeList YPos = nodeElement.getElementsByTagName("YPos");
                item.setYPos(YPos.item(0).getChildNodes().item(0).getNodeValue());

            } catch (Exception e) {
                e.printStackTrace();
            }

            hospitalItems.add(item);

        }

        for (Item i : hospitalItems) {
            try {
                Log.d("test", i.getYadmNm());
            } catch (Exception e) {

            }
        }

    }
}
