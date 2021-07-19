package com.sample.unimedical.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.hospital.Hospital;
import com.sample.unimedical.util.RequestSender;
import com.sample.unimedical.util.XMLParser;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private static final int SEARCH_RADIUS = 1500;
    private MapView mapView;
    EditText searchHospital;
    Button btnSearchHospital;
    ImageButton btnGPS;
    Button btnZoomIn;
    Button btnZoomOut;
    Button btnSearchFromMap;


    private int GPS_MODE = 0;
    private int CURRENT_ZOOMLEVEL = 7;

    MapPoint currentMapPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nested_mapview);
        mapView = findViewById(R.id.map_view);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setCurrentLocationEventListener(this);

        searchHospital = findViewById(R.id.edit_search_hospital);

        btnSearchHospital = findViewById(R.id.btn_search_hospital);
        btnSearchFromMap = findViewById(R.id.btn_search_from_map);
        btnGPS = findViewById(R.id.button_gps);
        btnZoomIn = findViewById(R.id.btn_zoom_in);
        btnZoomOut = findViewById(R.id.btn_zoom_out);


        btnSearchHospital.setOnClickListener(view -> new Thread(() -> {
            try {
                bindSearchItems(searchHospital.getText().toString().trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start());

        searchHospital.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                btnSearchHospital.callOnClick();
                return true;
            }
            return false;
        });

        btnSearchFromMap.setOnClickListener(v -> new Thread(() -> {
            try {
                bindLocationSearchItems(currentMapPoint.getMapPointGeoCoord().latitude, currentMapPoint.getMapPointGeoCoord().longitude, SEARCH_RADIUS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start());

        btnGPS.setOnClickListener(v -> {
            askForPermission();

            try {
                if (GPS_MODE == 2) {
                    GPS_MODE = 0;
                } else {
                    GPS_MODE += 1;
                }
                userLocationModeSelector(GPS_MODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnZoomIn.setOnClickListener(v -> zoomIn());

        btnZoomOut.setOnClickListener(v -> zoomOut());


        mapView.removeAllPOIItems();
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.setShowCurrentLocationMarker(false);
    }

    private void askForPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) { //위치 권한 확인

            //위치 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    private void userLocationModeSelector(int GPS_MODE) {
        switch (GPS_MODE) {
            case 0: // Off
            {
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                mapView.setShowCurrentLocationMarker(false);
            }
            break;
            case 1: // User Location On
            {
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
            }
            break;
            case 2: // User Location+Heading On
            {
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            }
            break;
        }
    }

    private void zoomIn() {
        mapView.setZoomLevel(CURRENT_ZOOMLEVEL - 1, true);
    }

    private void zoomOut() {
        mapView.setZoomLevel(CURRENT_ZOOMLEVEL + 1, true);
    }


    private void bindSearchItems(String hospitalName) throws Exception {
        List<Hospital> hospitals = XMLParser.processXML(RequestSender.sendHospitalRequest(hospitalName));
        List<MapPOIItem> newList = new ArrayList<>();

        for (Hospital i : hospitals) {
            try {
                MapPOIItem mapPOIItem = new MapPOIItem();
                mapPOIItem.setItemName(i.getYadmNm() + "/" + i.getMdeptGdrCnt() + "/" + i.getTelno());
                mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(i.getYPos()), Double.parseDouble(i.getXPos())));

                MapPOIItem.MarkerType markerType = MapPOIItem.MarkerType.BluePin;
                switch (i.getClCd()) {
                    case "01":
                        markerType = MapPOIItem.MarkerType.YellowPin;
                        break;
                    case "11":
                    case "21":
                        markerType = MapPOIItem.MarkerType.RedPin;
                        break;
                    case "31":
                        markerType = MapPOIItem.MarkerType.BluePin;
                        break;
                }

                mapPOIItem.setMarkerType(markerType);
                mapPOIItem.setSelectedMarkerType(markerType);
                mapPOIItem.setShowAnimationType(MapPOIItem.ShowAnimationType.DropFromHeaven);
                newList.add(mapPOIItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        clearPOI();
        setPOIItems(newList);
    }

    private void bindLocationSearchItems(double Xpos, double Ypos, int radius) throws Exception {
        List<Hospital> hospitals = XMLParser.processXML(RequestSender.sendHospitalRequest(Xpos, Ypos, radius));
        List<MapPOIItem> newList = new ArrayList<>();

        for (Hospital i : hospitals) {
            try {
                MapPOIItem mapPOIItem = new MapPOIItem();
                mapPOIItem.setItemName(i.getYadmNm() + "/" + i.getMdeptGdrCnt() + "/" + i.getTelno());
                mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(i.getYPos()), Double.parseDouble(i.getXPos())));

                MapPOIItem.MarkerType markerType = MapPOIItem.MarkerType.BluePin;
                switch (i.getClCd()) {
                    case "01":
                        markerType = MapPOIItem.MarkerType.YellowPin;
                        break;
                    case "11":
                    case "21":
                        markerType = MapPOIItem.MarkerType.RedPin;
                        break;
                    case "31":
                        markerType = MapPOIItem.MarkerType.BluePin;
                        break;
                }

                mapPOIItem.setMarkerType(markerType);
                mapPOIItem.setSelectedMarkerType(markerType);
                mapPOIItem.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
                newList.add(mapPOIItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        clearPOI();
        setPOIItems(newList);
    }

    private void setPOIItems(List<MapPOIItem> list) {
        for (MapPOIItem item : list) {
            mapView.addPOIItem(item);
            mapView.selectPOIItem(item, true);
            mapView.setMapCenterPoint(item.getMapPoint(), false);
        }
    }

    private void clearPOI() {
        mapView.removeAllPOIItems();
    }

    //ReverseGeoCoder
    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }

    private void onFinishReverseGeoCoding(String result) {
        Toast.makeText(MapActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }

    //currentLocationListener
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
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

    // CalloutBalloonAdapter 인터페이스 구현
    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            try {
                ((TextView) mCalloutBalloon.findViewById(R.id.bal_hospital_name)).setText(poiItem.getItemName().split("/")[0]);
                ((TextView) mCalloutBalloon.findViewById(R.id.bal_doctor_count)).setText("일반의: " + poiItem.getItemName().split("/")[1] + "명");
                ((TextView) mCalloutBalloon.findViewById(R.id.bal_tel_number)).setText(poiItem.getItemName().split("/")[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    private void toggleViewVisibility() {

        if (btnZoomIn.getVisibility() == View.VISIBLE) {
            setInvisible();
        } else {
            setVisible();
        }
    }

    private void setVisible() {
        searchHospital.setVisibility(View.VISIBLE);
        btnSearchHospital.setVisibility(View.VISIBLE);
        btnGPS.setVisibility(View.VISIBLE);
        btnZoomIn.setVisibility(View.VISIBLE);
        btnZoomOut.setVisibility(View.VISIBLE);
        btnSearchFromMap.setVisibility(View.VISIBLE);
    }

    private void setInvisible() {
        searchHospital.setVisibility(View.INVISIBLE);
        btnSearchHospital.setVisibility(View.INVISIBLE);
        btnGPS.setVisibility(View.INVISIBLE);
        btnZoomIn.setVisibility(View.INVISIBLE);
        btnZoomOut.setVisibility(View.INVISIBLE);
        btnSearchFromMap.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        currentMapPoint = mapView.getMapCenterPoint();
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        currentMapPoint = mapPoint;
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
        CURRENT_ZOOMLEVEL = i;
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        toggleViewVisibility();
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        Toast.makeText(this, mapPOIItem.getItemName().split("/")[0], Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}
