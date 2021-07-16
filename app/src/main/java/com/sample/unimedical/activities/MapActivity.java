package com.sample.unimedical.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.HospitalPoiBind;
import com.sample.unimedical.domain.hospital.Item;
import com.sample.unimedical.util.RequestSender;
import com.sample.unimedical.util.XMLParser;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener {

    private static final MapPoint CUSTOM_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.537229, 127.005515);
    private static final MapPoint DEFAULT_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.537229, 127.005515);

    private MapView mapView;
    private MapPOIItem mCustomMarker;
    EditText searchHospital;
    Button btnSearchHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nested_mapview);
        mapView = findViewById(R.id.map_view);
        mapView.setDaumMapApiKey("0cfe9165fbf7d7069b488e119b2e8d6c");
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);

        searchHospital = findViewById(R.id.search_hospital);
        btnSearchHospital = findViewById(R.id.btn_search_hospital);

        btnSearchHospital.setOnClickListener(view -> {
            new Thread(() -> {
                try {
                    addBoundObjects(searchHospital.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });

        mapView.removeAllPOIItems();
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        showAll();
    }


    private void addBoundObjects(String hospitalName) throws Exception {
        List<Item> items = XMLParser.processXML(RequestSender.sendHospitalRequest(hospitalName));
        List<MapPOIItem> newList = new ArrayList<>();
        for (Item i : items) {
            try {
                MapPOIItem mapPOIItem = new MapPOIItem();
                mapPOIItem.setItemName(i.getYadmNm() + "/" + i.getMdeptGdrCnt() + "/" + i.getTelno());
                mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(i.getYPos()), Double.parseDouble(i.getXPos())));
                mapPOIItem.setMarkerType(MapPOIItem.MarkerType.BluePin);
                mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);
                newList.add(mapPOIItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (MapPOIItem mpi : newList) {
            mapView.addPOIItem(mpi);
            mapView.selectPOIItem(mpi, true);
            mapView.setMapCenterPoint(CUSTOM_MARKER_POINT, false);
        }
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
                Log.d("tester", poiItem.getItemName());
                ((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.round);
                ((TextView) mCalloutBalloon.findViewById(R.id.bal_hospital_name)).setText(poiItem.getItemName().split("/")[0]);
                ((TextView) mCalloutBalloon.findViewById(R.id.bal_doctor_count)).setText(poiItem.getItemName().split("/")[1]);
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
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_DEFAULT_CALLOUT_BALLOON: {
                mapView.removeAllPOIItems();
                mapView.setCalloutBalloonAdapter(null);
                createDefaultMarker(mapView);
                createCustomMarker(mapView);
                showAll();
                return true;
            }
            case MENU_CUSTOM_CALLOUT_BALLOON: {
                mapView.removeAllPOIItems();
                mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
                createDefaultMarker(mapView);
                createCustomMarker(mapView);
                showAll();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }*/

    /*private void createDefaultMarker(MapView mapView) {
        mDefaultMarker = new MapPOIItem();
        String name = "Default Marker";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setTag(0);
        mDefaultMarker.setMapPoint(DEFAULT_MARKER_POINT);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(DEFAULT_MARKER_POINT, false);
    }*/

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
        Toast.makeText(this, "Clicked " + mapPOIItem.getItemName() + " Callout Balloon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}
