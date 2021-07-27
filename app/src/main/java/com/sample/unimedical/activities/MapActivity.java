package com.sample.unimedical.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.sample.unimedical.util.ExcelHandler;
import com.sample.unimedical.util.RequestSender;
import com.sample.unimedical.util.XMLParser;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sample.unimedical.util.AnimationHandler.viewAnimationGetBack;
import static com.sample.unimedical.util.AnimationHandler.viewAnimationLeft;
import static com.sample.unimedical.util.AnimationHandler.viewAnimationRight;
import static com.sample.unimedical.util.AnimationHandler.viewAnimationUp;
import static net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeadingWithoutMapMoving;
import static net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading;
import static net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving;

public class MapActivity extends FragmentActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private static final int SEARCH_RADIUS = 1500;
    private static final int UNIVERSITY_HOSPITAL = 1;
    private static final int MIDDLE_HOSPITAL = 2;
    private static final int SMALL_HOSPITAL = 3;
    private static int HOSPITAL_SCALE = 1;

    private MapView mapView;
    EditText searchHospital;
    Button btnSearchHospital;
    ImageButton btnGPS;
    ImageButton btnZoomIn;
    ImageButton btnZoomOut;
    Button btnSearchFromMap;


    private int GPS_MODE = 0;
    private int CURRENT_ZOOM_LEVEL = 7;
    private boolean isViewVisible = true;

    private long backKeyPressedTime = 0;

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
                MapPoint mapPoint = mapView.getMapCenterPoint();
                bindLocationSearchItems(mapPoint.getMapPointGeoCoord().latitude, mapPoint.getMapPointGeoCoord().longitude);
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

        clearPOI();
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
                mapView.setCurrentLocationTrackingMode(TrackingModeOnWithoutHeading);
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
        mapView.setZoomLevel(CURRENT_ZOOM_LEVEL - 1, true);
    }

    private void zoomOut() {
        mapView.setZoomLevel(CURRENT_ZOOM_LEVEL + 1, true);
    }


    private void bindSearchItems(String hospitalName) throws Exception {
        List<Hospital> hospitals = XMLParser.processXML(RequestSender.sendHospitalRequest(hospitalName.trim()));
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
                newList.add(mapPOIItem);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setMarkerAnimationType();
        clearPOI();
        setPOIItems(newList);
    }

    private void bindLocationSearchItems(double Xpos, double Ypos) throws Exception {
        List<Hospital> hospitals = XMLParser.processXML(RequestSender.sendHospitalRequest(Xpos, Ypos, SEARCH_RADIUS));
        List<MapPOIItem> newList = new ArrayList<>();

        for (Hospital hospital : hospitals) {
            MapPOIItem mapPOIItem;
            switch (hospital.getClCd()) {
                case "01":
                    HOSPITAL_SCALE = UNIVERSITY_HOSPITAL;
                    break;
                case "11":
                case "21":
                    HOSPITAL_SCALE = MIDDLE_HOSPITAL;
                    break;
                case "31":
                    HOSPITAL_SCALE = SMALL_HOSPITAL;
                    break;
            }
            mapPOIItem = setNonClientMarker(hospital, Double.parseDouble(hospital.getXPos()), Double.parseDouble(hospital.getYPos()), HOSPITAL_SCALE);
            mapPOIItem = ExcelHandler.setContract(hospital, mapPOIItem, getApplicationContext());
            newList.add(mapPOIItem);
        }

        setMarkerAnimationType();
        clearPOI();
        setPOIItems(newList);

    }

    private String setHospitalInfo(Hospital hospital, boolean isClient) {
        String result;
        if (isClient) {
            result = hospital.getYadmNm() + "/" + hospital.getManager() + "/" + hospital.getTelno() + "/" + hospital.getDevice();
        } else {
            result = hospital.getYadmNm() + "/" + hospital.getMdeptGdrCnt() + "/" + hospital.getTelno();
        }
        return result;
    }

    private MapPOIItem setCustomMarker(String hospitalInfo, MapPoint mapPoint) {
        MapPOIItem customMarker = new MapPOIItem();
        customMarker.setItemName(hospitalInfo);
        customMarker.setMapPoint(mapPoint);
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        customMarker.setCustomImageResourceId(R.drawable.marker_green);
        customMarker.setCustomImageAutoscale(true);
        customMarker.setCustomImageAnchor(0.5f, 1.0f);

        return customMarker;
    }

    private MapPOIItem setClientMarker(Hospital hospital, double Xpos, double Ypos) {
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Ypos, Xpos);
        MapPOIItem mapPOIItem = new MapPOIItem();
        mapPOIItem.setItemName(setHospitalInfo(hospital, true));
        mapPOIItem.setMapPoint(mapPoint);
        mapPOIItem.setMarkerType(MapPOIItem.MarkerType.RedPin);
        mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        return mapPOIItem;
    }

    private MapPOIItem setNonClientMarker(Hospital hospital, double Xpos, double Ypos, int hospitalScale) {
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Ypos, Xpos);
        MapPOIItem mapPOIItem = new MapPOIItem();
        mapPOIItem.setItemName(setHospitalInfo(hospital, false));
        mapPOIItem.setMapPoint(mapPoint);

        switch (hospitalScale) {
            case UNIVERSITY_HOSPITAL:
                mapPOIItem.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin);
                break;
            case MIDDLE_HOSPITAL:
                mapPOIItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
                mapPOIItem.setCustomImageResourceId(R.drawable.marker_green);
                mapPOIItem.setCustomImageAutoscale(true);
                mapPOIItem.setCustomImageAnchor(0.5f, 1.0f);
                break;
            case SMALL_HOSPITAL:
                mapPOIItem.setMarkerType(MapPOIItem.MarkerType.BluePin);
                mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);
                break;
        }

        return mapPOIItem;
    }

    private void setMarkerAnimationType() {
        Arrays.stream(mapView.getPOIItems()).forEach(e -> e.setShowAnimationType(MapPOIItem.ShowAnimationType.DropFromHeaven));
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
                ((TextView) mCalloutBalloon.findViewById(R.id.bal_doctor_count)).setText(String.format("일반의: %s명", poiItem.getItemName().split("/")[1]));
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

        View linearLayoutSearch = findViewById(R.id.linear_search);
        View linearLayoutZoom = findViewById(R.id.linear_zoom);
        View SearchFromMap = btnSearchFromMap;
        View GPSButton = btnGPS;

        if (isViewVisible) {

            viewAnimationUp(linearLayoutSearch);
            viewAnimationUp(SearchFromMap);
            viewAnimationUp(SearchFromMap);
            viewAnimationRight(linearLayoutZoom);
            viewAnimationLeft(GPSButton);
            isViewVisible = false;
        } else {
            viewAnimationGetBack(linearLayoutSearch);
            viewAnimationGetBack(SearchFromMap);
            viewAnimationGetBack(linearLayoutZoom);
            viewAnimationGetBack(GPSButton);
            isViewVisible = true;
        }
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        try {
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        } catch (Exception e) {
            askForPermission();
        }
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
        CURRENT_ZOOM_LEVEL = i;
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
        if (mapView.getCurrentLocationTrackingMode() == MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading) {
            mapView.setCurrentLocationTrackingMode(TrackingModeOnWithoutHeadingWithoutMapMoving);
        }
        if (mapView.getCurrentLocationTrackingMode() == MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading) {
            mapView.setCurrentLocationTrackingMode(TrackingModeOnWithHeadingWithoutMapMoving);
        }
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

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }
}
