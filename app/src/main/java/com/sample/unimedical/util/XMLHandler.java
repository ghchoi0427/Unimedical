package com.sample.unimedical.util;

import android.content.Context;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.hospital.Hospital;

import net.daum.mf.map.api.MapPointBounds;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLHandler {

    public static List<Hospital> parseSelectiveXML(String xml, String hospitalName) throws ParserConfigurationException, IOException, SAXException {
        List<Hospital> newList = new ArrayList<>();

        InputSource is = new InputSource(new StringReader(xml));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

        NodeList nodeList = document.getElementsByTagName("item");

        for (int i = 0; i < nodeList.getLength(); i++) {

            Element nodeElement = (Element) nodeList.item(i);
            Hospital hospital = new Hospital();
            try {
                if (nodeElement.getElementsByTagName("yadmNm").item(0).getChildNodes().item(0).getNodeValue().contains(hospitalName)) {
                    setHospitalFields(nodeElement, hospital);
                    newList.add(hospital);
                }
            } catch (Exception e) {

            }
        }
        return newList;
    }


    public static List<Hospital> parseSelectiveXML(String xml, MapPointBounds mapPointBounds) throws ParserConfigurationException, IOException, SAXException {

        List<Hospital> newList = new ArrayList<>();

        InputSource is = new InputSource(new StringReader(xml));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

        double boundTop = mapPointBounds.topRight.getMapPointGeoCoord().latitude;
        double boundBottom = mapPointBounds.bottomLeft.getMapPointGeoCoord().latitude;
        double boundLeft = mapPointBounds.bottomLeft.getMapPointGeoCoord().longitude;
        double boundRight = mapPointBounds.topRight.getMapPointGeoCoord().longitude;


        NodeList nodeList = document.getElementsByTagName("item");

        for (int i = 0; i < nodeList.getLength(); i++) {

            Element nodeElement = (Element) nodeList.item(i);
            Hospital hospital = new Hospital();
            try {
                if (validateCoordinate(nodeElement)) {
                    continue;
                }
                double Xpos = Double.parseDouble(nodeElement.getElementsByTagName("XPos").item(0).getChildNodes().item(0).getNodeValue());
                double Ypos = Double.parseDouble(nodeElement.getElementsByTagName("YPos").item(0).getChildNodes().item(0).getNodeValue());

                if (boundLeft <= Xpos && Xpos <= boundRight && boundBottom <= Ypos && Ypos <= boundTop) {
                    setHospitalFields(nodeElement, hospital);
                    newList.add(hospital);
                }

            } catch (Exception e) {

            }
        }

        return newList;

    }

    public static void setHospitalFields(Element nodeElement, Hospital hospital) {
        NodeList yadmNm = nodeElement.getElementsByTagName("yadmNm");   //?????????
        hospital.setYadmNm(yadmNm.item(0).getChildNodes().item(0).getNodeValue());

        NodeList mdeptGdrCnt = nodeElement.getElementsByTagName("mdeptGdrCnt");   //??????????????? ??? ???
        hospital.setMdeptGdrCnt(mdeptGdrCnt.item(0).getChildNodes().item(0).getNodeValue());

        NodeList telno = nodeElement.getElementsByTagName("telno");
        hospital.setTelno(telno.item(0).getChildNodes().item(0).getNodeValue());

        NodeList XPos = nodeElement.getElementsByTagName("XPos");
        hospital.setXPos(XPos.item(0).getChildNodes().item(0).getNodeValue());

        NodeList YPos = nodeElement.getElementsByTagName("YPos");
        hospital.setYPos(YPos.item(0).getChildNodes().item(0).getNodeValue());


        NodeList clCd = nodeElement.getElementsByTagName("clCd");
        hospital.setClCd(clCd.item(0).getChildNodes().item(0).getNodeValue());  // [01 ????????????: ?????? ] [11 ?????? 21 ?????? : ??????] [31 ??????: ??????]

        NodeList sidoCdNm = nodeElement.getElementsByTagName("sidoCdNm");
        hospital.setSidoCdNm(sidoCdNm.item(0).getChildNodes().item(0).getNodeValue());
    }


    public static String readHospitalList(Context context) {
        InputStream is;
        try {
            is = context.openFileInput(context.getString(R.string.local_hospital_file_name));
            return readTextFile(is);

        } catch (Exception e) {
            return "";
        }
    }

    public static String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buf = new byte[1024];
        int length;

        try {
            while ((length = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    private static boolean validateCoordinate(Element nodeElement) {
        return nodeElement.getElementsByTagName("XPos").item(0).getChildNodes().item(0).getNodeValue() == null || nodeElement.getElementsByTagName("YPos").item(0).getChildNodes().item(0).getNodeValue() == null;
    }

}
