package com.sample.unimedical.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.hospital.Item;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MapActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.OpenAPIKeyAuthenticationResultListener, MapView.MapViewEventListener {

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    MapView mapView;
    RelativeLayout mapViewContainer;
    List<Item> hospitalItems;

    private final String LOG_TAG = "test2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = new MapView(this);

        mapView.setDaumMapApiKey("0cfe9165fbf7d7069b488e119b2e8d6c");
        mapView.setOpenAPIKeyAuthenticationResultListener(this);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationEventListener(this);

        mapViewContainer = findViewById(R.id.mapView);
        mapViewContainer.addView(mapView);
        hospitalItems = new ArrayList<>();

        currentLocation();


        /*new Thread(() -> {
            try {
                processXML(getHospitalRequest());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();*/


        setMarker();
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

    protected void processXML(String str) throws ParserConfigurationException, IOException, SAXException {

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

            } catch (Exception e) {

            }
        }

    }

    private void currentLocation() {
        mapView.getCurrentLocationTrackingMode();
    }


    private void setMarker() {
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);

        MapPoint MARKER_POINT = MapPoint.mapPointWithCONGCoord(37.6, 127.0);
        //MapPoint MARKER_POINT = MapPoint.mapPointWithCONGCoord(127.0, 37.6);
        marker.setMapPoint(MARKER_POINT);

        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        marker.setCustomImageAnchor(0.5f, 1.0f);

        mapView.addPOIItem(marker);
    }


    private void setCustomMarker() {
        MapPOIItem customMarker = new MapPOIItem();
        MapPoint MARKER_POINT = MapPoint.mapPointWithCONGCoord(37.6132762, 127.0978309);
        customMarker.setItemName("Custom Marker");
        customMarker.setTag(1);
        customMarker.setMapPoint(MARKER_POINT);
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        customMarker.setCustomImageResourceId(R.drawable.gradation); // 마커 이미지.
        customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

        mapView.addPOIItem(customMarker);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.d("test", String.format("mapview oncurrentLocationUpdate (%f, %f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, v));

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    //MapViewEventListener

    @Override
    public void onMapViewInitialized(MapView mapView) {
        Log.i(LOG_TAG, "MapView had loaded. Now, MapView APIs could be called safely");
        //mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.537229, 127.005515), 2, true);
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onMapViewCenterPointMoved (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
        Log.i(LOG_TAG, String.format("MapView onMapViewZoomLevelChanged (%d)", i));
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onMapViewSingleTapped (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("DaumMapLibrarySample");
        alertDialog.setMessage(String.format("Double-Tap on (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
        alertDialog.setPositiveButton("OK", null);
        alertDialog.show();
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("DaumMapLibrarySample");
        alertDialog.setMessage(String.format("Long-Press on (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
        alertDialog.setPositiveButton("OK", null);
        alertDialog.show();
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onMapViewDragStarted (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onMapViewDragEnded (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onMapViewMoveFinished (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    //OpenAPIKeyAuthenticationResultListener

    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int resultCode, String resultMessage) {
        Log.i("test", String.format("Open API Key Authentication Result : code=%d, message=%s", resultCode, resultMessage));
    }
}


