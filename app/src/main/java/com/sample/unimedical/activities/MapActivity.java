package com.sample.unimedical.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.hospital.Item;
import com.sample.unimedical.util.RequestSender;
import com.sample.unimedical.util.XMLParser;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private static final MapPoint CUSTOM_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.537229, 127.005515);
    private static final MapPoint DEFAULT_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.537229, 127.005515);

    private MapView mapView;
    private MapPOIItem mCustomMarker;
    EditText searchHospital;
    Button btnSearchHospital;
    Button btnGPS;

    private int GPS_MODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nested_mapview);
        mapView = findViewById(R.id.map_view);
        mapView.setDaumMapApiKey("0cfe9165fbf7d7069b488e119b2e8d6c");

        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setCurrentLocationEventListener(this);

        searchHospital = findViewById(R.id.search_hospital);
        btnSearchHospital = findViewById(R.id.btn_search_hospital);
        btnGPS = findViewById(R.id.button_gps);

        btnSearchHospital.setOnClickListener(view -> {
            new Thread(() -> {
                try {
                    addBoundObjects(searchHospital.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });

        searchHospital.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                btnSearchHospital.callOnClick();
                return true;
            }
            return false;
        });

        btnGPS.setOnClickListener(v -> {
            if (GPS_MODE == 2) {
                GPS_MODE = 0;
            } else {
                GPS_MODE += 1;
            }
            userLocationMode(GPS_MODE);
        });


        mapView.removeAllPOIItems();
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        showAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setShowCurrentLocationMarker(false);
    }

    private void userLocationMode(int GPS_MODE) {
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


    private void addBoundObjects(String hospitalName) throws Exception {
        List<Item> items = XMLParser.processXML(RequestSender.sendHospitalRequest(hospitalName));
        List<MapPOIItem> newList = new ArrayList<>();

        for (Item i : items) {
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
                newList.add(mapPOIItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mapView.removeAllPOIItems();
        for (MapPOIItem mpi : newList) {
            mapView.addPOIItem(mpi);
            mapView.selectPOIItem(mpi, true);
            mapView.setMapCenterPoint(CUSTOM_MARKER_POINT, false);
        }
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
        //menu.add(0, MENU_DEFAULT_CALLOUT_BALLOON, Menu.NONE, "Default CalloutBalloon");
        // menu.add(0, MENU_CUSTOM_CALLOUT_BALLOON, Menu.NONE, "Custom CalloutBalloon");

        return true;
    }

    private void createCustomMarker(MapView mapView) {
        mCustomMarker = new MapPOIItem();
        String name = "Custom Marker";
        mCustomMarker.setItemName(name);
        mCustomMarker.setTag(1);
        mCustomMarker.setMapPoint(CUSTOM_MARKER_POINT);

        mCustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);

        mCustomMarker.setCustomImageResourceId(R.drawable.round);
        mCustomMarker.setCustomImageAutoscale(false);
        mCustomMarker.setCustomImageAnchor(0.5f, 1.0f);

        mapView.addPOIItem(mCustomMarker);
        mapView.selectPOIItem(mCustomMarker, true);
        mapView.setMapCenterPoint(CUSTOM_MARKER_POINT, false);
    }

    private void showAll() {
        int padding = 20;
        float minZoomLevel = 7;
        float maxZoomLevel = 10;
        MapPointBounds bounds = new MapPointBounds(CUSTOM_MARKER_POINT, DEFAULT_MARKER_POINT);
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, padding, minZoomLevel, maxZoomLevel));
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

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
