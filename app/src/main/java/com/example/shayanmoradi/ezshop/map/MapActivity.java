package com.example.shayanmoradi.ezshop.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.shayanmoradi.ezshop.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

public class MapActivity extends FragmentActivity implements LocationListener, OnMapReadyCallback {
    GoogleMap map;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MapActivity.class);

        return intent;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
//        MapFragment fragment1 = MapFragment.newInstance();
//        getSupportFragmentManager()
//                .beginTransaction()
//
//                .replace(R.id.map_container,fragment1,"test")
//                .commit();

        GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getApplicationContext());
//        map = ((SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map_container)).getMapAsync(this);
//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.ma);
//        mapFragment.getMapAsync(this);



        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker")
                .draggable(true)
                .snippet("Hello")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {
                // TODO Auto-generated method stub
                // Here your code
                Toast.makeText(MapActivity.this, "Dragging Start",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        MapActivity.this,
                        "Lat " + map.getMyLocation().getLatitude() + " "
                                + "Long " + map.getMyLocation().getLongitude(),
                        Toast.LENGTH_LONG).show();
                System.out.println("yalla b2a "
                        + map.getMyLocation().getLatitude());
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                // TODO Auto-generated method stub
                // Toast.makeText(MainActivity.this, "Dragging",
                // Toast.LENGTH_SHORT).show();
                System.out.println("Draagging");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(map)) {
            // handle click here
            // map.getMyLocation();
            System.out.println("Clicked");
            double lat = map.getMyLocation().getLatitude();
            System.out.println("Lat" + lat);
            Toast.makeText(MapActivity.this,
                    "Current location " + map.getMyLocation().getLatitude(),
                    Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }

}
