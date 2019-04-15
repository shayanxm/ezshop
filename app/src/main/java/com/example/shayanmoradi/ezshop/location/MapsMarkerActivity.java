package com.example.shayanmoradi.ezshop.location;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.order.OrderInfoActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MapsMarkerActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, LocationListener {
    GoogleMap mGoogleMap;
    LocationManager locationManager;
    private double lat;
    private double lon;
    public static final String LONG_INT="com.example.shayanmoradi.ezshop.location.long";
    public static final String LAT_INT=  "com.example.shayanmoradi.ezshop.location.lat";
    public static final String ADDRESS_STRING=  "com.example.shayanmoradi.ezshop.location.lat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_marker);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /////////////
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        getLocation();


//        getLocationBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getLocation();
//            }
//        });
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //  locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        lat = location.getLatitude();
        lon = location.getLongitude();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//         locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
//                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));

        } catch (Exception e) {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        //  Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    ///////


//    public void onMapReady(GoogleMap googleMap) {
//        // Add a marker in Sydney, Australia,
//        // and move the map's camera to the same location.
//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions().position(sydney)
//                .draggable(true)
//                .title("Marker in Sydney"));
//
//
//    }

    //
//    @Override
//    public void onMarkerDragStart(Marker marker) {
//        Toast.makeText(this, marker.getPosition() + "", Toast.LENGTH_SHORT).show();
//        Intent intent = OrderInfoActivity.newIntent(MapsMarkerActivity.this);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onMarkerDrag(Marker marker) {
//        Toast.makeText(this, marker.getPosition() + "", Toast.LENGTH_SHORT).show();
//        Intent intent = OrderInfoActivity.newIntent(MapsMarkerActivity.this);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onMarkerDragEnd(Marker marker) {
//        Toast.makeText(this, marker.getPosition() + "", Toast.LENGTH_SHORT).show();
//        Intent intent = OrderInfoActivity.newIntent(MapsMarkerActivity.this);
//        startActivity(intent);
//
//    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.d("test", "onMapReady()");
        getLocation();
        mGoogleMap = googleMap;
        float zoomLevel = 10.0f; //This goes up to 21
        // LatLng latLng= new LatLng(35.689,35.689);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.69439, 51.42151), 10.0f));
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        // mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng sydney = new LatLng(35.689, 51.3890);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .draggable(true)

                .title("Marker in Sydney"));
        mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("test out", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub

                Log.e("test", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
                Intent intent = OrderInfoActivity.newIntent(MapsMarkerActivity.this);
                LatLng sydney = new LatLng(arg0.getPosition().latitude, arg0.getPosition().longitude);


                getUserAddress(sydney);
                Intent intent1 = new Intent(MapsMarkerActivity.this, OrderInfoActivity.class);
                intent.putExtra(LAT_INT,arg0.getPosition().latitude);
                intent.putExtra(LONG_INT,arg0.getPosition().longitude);
                intent.putExtra(ADDRESS_STRING,  getUserAddress(sydney));
                setResult(1,intent1);

//                startActivity(intent1);
                finish();
                // Log.e("test","address is"+ getUserAddress(sydney));

//            try {
//                Geocoder geo = new Geocoder(MapsMarkerActivity.this.getApplicationContext(), Locale.getDefault());
//                List<Address> addresses = geo.getFromLocation(arg0.getPosition().latitude, arg0.getPosition().longitude, 1);
//                if (addresses.isEmpty()) {
//                  //  yourtextfieldname.setText("Waiting for Location");
//                    Log.e("test","fail");
//                }
//                else {
//                    if (addresses.size() > 0) {
//                        Log.e("test",addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
//                       // yourtextfieldname.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
//                        //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//            catch (Exception e) {
//                e.printStackTrace(); // getFromLocation() may sometimes fail
//                Log.e("test","fail");
//            }

                // startActivity(intent);

            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("test", "onMarkerDrag...");
            }

        });

        mGoogleMap.setOnMarkerClickListener(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private String getUserAddress(LatLng userLocation) {
        String userAddress = "not set yet";

        try {
            double latitude = userLocation.latitude;
            double longitude = userLocation.longitude;
            Geocoder geo = new Geocoder(MapsMarkerActivity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                Log.i("test", "Waiting for location");
            } else {
                userAddress = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality()
                        + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("test", e.getMessage());// getFromLocation() may sometimes fail
        } finally {
            return userAddress;
        }

    }

}
