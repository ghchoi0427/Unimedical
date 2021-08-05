package com.sample.unimedical.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.hospital.Hospital;
import com.sample.unimedical.util.ExcelHandler;
import com.sample.unimedical.util.XMLHandler;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

import static com.sample.unimedical.util.AnimationHandler.viewAnimationGetBack;
import static com.sample.unimedical.util.AnimationHandler.viewAnimationLeft;
import static com.sample.unimedical.util.AnimationHandler.viewAnimationRight;
import static com.sample.unimedical.util.AnimationHandler.viewAnimationUp;
import static com.sample.unimedical.util.XMLHandler.readHospitalList;
import static net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeadingWithoutMapMoving;
import static net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading;
import static net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving;

public class MapActivity extends FragmentActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private static final int INDEX_HOSPITAL_NAME = 0;
    private static final int UNIVERSITY_HOSPITAL = 1;
    private static final int MIDDLE_HOSPITAL = 2;
    private static final int SMALL_HOSPITAL = 3;
    private static int HOSPITAL_SCALE = 1;

    private MapView mapView;
    private EditText searchHospital;
    private Button btnSearchHospital;
    private ImageButton btnGPS;
    private Button btnSearchFromMap;
    private ProgressBar progressBar;

    private int GPS_MODE = 0;
    private int CURRENT_ZOOM_LEVEL = 7;
    private boolean isViewVisible = true;

    private long backKeyPressedTime = 0;

    private MapPointBounds mapPointBounds;


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

        ImageButton btnZoomIn = findViewById(R.id.btn_zoom_in);
        ImageButton btnZoomOut = findViewById(R.id.btn_zoom_out);

        progressBar = findViewById(R.id.progress_bar_search);

        btnSearchHospital.setOnClickListener(view -> new Thread(() -> {
            try {
                runOnUiThread(() -> progressBarController(true));
                bindSearchItems(searchHospital.getText().toString().trim());
                runOnUiThread(() -> progressBarController(false));
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
                runOnUiThread(() -> progressBarController(true));
                bindLocationSearchItems(mapPointBounds);
                runOnUiThread(() -> progressBarController(false));
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

    private void progressBarController(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private boolean checkEmptySearch(String keyword) {
        if (keyword.isEmpty()) {
            showAlarm("검색어를 입력해주세요");
        }
        return keyword.isEmpty();

    }

    private void getMapBoundary() {
        mapPointBounds = mapView.getMapPointBounds();
    }


    private void bindSearchItems(String hospitalName) throws Exception {

        if (checkEmptySearch(hospitalName)) {
            return;
        }
        List<Hospital> hospitals = XMLHandler.parseSelectiveXML((readHospitalList(getApplicationContext())), hospitalName);
        List<MapPOIItem> newList = new ArrayList<>();

        if (checkNoResult(hospitals)) {
            showAlarm("검색결과가 없습니다.");
            return;
        }

        for (Hospital hospital : hospitals) {
            MapPOIItem mapPOIItem;
            setHospitalScale(hospital.getClCd());
            mapPOIItem = setNonClientMarker(hospital, Double.parseDouble(hospital.getXPos()), Double.parseDouble(hospital.getYPos()), HOSPITAL_SCALE);
            mapPOIItem = ExcelHandler.setContract(hospital, mapPOIItem, getApplicationContext());
            newList.add(mapPOIItem);
        }

        clearPOI();
        setPOIItems(newList);
    }

    private boolean checkNullString(String input) {
        return input.isEmpty();
    }


    private void bindLocationSearchItems(MapPointBounds mapPointBounds) throws Exception {

        String hospitalXml = readHospitalList(getApplicationContext());
        if (checkNullString(hospitalXml)) {
            showAlarm("병원정보를 업데이트 해주세요");
            startActivity(new Intent(this, UpdateActivity.class));
            return;
        }
        List<Hospital> hospitals = XMLHandler.parseSelectiveXML(hospitalXml, mapPointBounds);
        List<MapPOIItem> newList = new ArrayList<>();

        if (checkNoResult(hospitals)) {
            showAlarm("검색결과가 없습니다.");
            return;
        }

        for (Hospital hospital : hospitals) {
            MapPOIItem mapPOIItem;
            try {
                setHospitalScale(hospital.getClCd());

            } catch (Exception ignored) {

            }
            mapPOIItem = setNonClientMarker(hospital, Double.parseDouble(hospital.getXPos()), Double.parseDouble(hospital.getYPos()), HOSPITAL_SCALE);
            mapPOIItem = ExcelHandler.setContract(hospital, mapPOIItem, getApplicationContext());

            newList.add(mapPOIItem);
        }

        clearPOI();
        setPOIItems(newList);

    }

    private void setHospitalScale(String classCode) {
        switch (classCode) {
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
    }

    private boolean checkNoResult(List<Hospital> list) {
        return list.isEmpty();
    }

    private String getHospitalInfo(Hospital hospital) {
        return hospital.getYadmNm() + "/" + hospital.getTelno();
    }

    private MapPOIItem setNonClientMarker(Hospital hospital, double Xpos, double Ypos, int hospitalScale) {
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Ypos, Xpos);
        MapPOIItem mapPOIItem = new MapPOIItem();
        mapPOIItem.setItemName(getHospitalInfo(hospital));
        mapPOIItem.setMapPoint(mapPoint);

        switch (hospitalScale) {
            case UNIVERSITY_HOSPITAL:
                mapPOIItem.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin);
                break;
            case MIDDLE_HOSPITAL:
                mapPOIItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
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


    private void setPOIItems(List<MapPOIItem> list) {
        for (MapPOIItem item : list) {
            mapView.addPOIItem(item);
            mapView.selectPOIItem(item, true);
            //mapView.setMapCenterPoint(item.getMapPoint(), true);
        }
    }

    private void showAlarm(String message) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }

    private void clearPOI() {
        mapView.removeAllPOIItems();
    }

    //ReverseGeoCoder
    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {

    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

    }

    //currentLocationListener
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

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
        private final View calloutBalloon;

        public CustomCalloutBalloonAdapter() {
            calloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            try {

                if (poiItem.getItemName().split("/").length == 2) {

                    calloutBalloon.findViewById(R.id.bal_fourth).setVisibility(View.GONE);
                    ((TextView) calloutBalloon.findViewById(R.id.bal_first)).setText(poiItem.getItemName().split("/")[0]);
                    ((TextView) calloutBalloon.findViewById(R.id.bal_second)).setText(poiItem.getItemName().split("/")[1]);
                }
                if (poiItem.getItemName().split("/").length == 4) {
                    calloutBalloon.findViewById(R.id.bal_fourth).setVisibility(View.VISIBLE);
                    ((TextView) calloutBalloon.findViewById(R.id.bal_first)).setText(poiItem.getItemName().split("/")[0]);
                    ((TextView) calloutBalloon.findViewById(R.id.bal_second)).setText(poiItem.getItemName().split("/")[1]);
                    ((TextView) calloutBalloon.findViewById(R.id.bal_third)).setText(poiItem.getItemName().split("/")[2]);
                    ((TextView) calloutBalloon.findViewById(R.id.bal_fourth)).setText(poiItem.getItemName().split("/")[3]);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return calloutBalloon;
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
            CURRENT_ZOOM_LEVEL = mapView.getZoomLevel();
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
            getMapBoundary();
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
        getMapBoundary();
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        showAlarm(mapPOIItem.getItemName().split("/")[INDEX_HOSPITAL_NAME]);
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
            showAlarm(getString(R.string.msg_quit));
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }
}
