package com.example.shayanmoradi.ezshop.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shayanmoradi.ezshop.R;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.shayanmoradi.ezshop.location.MapsMarkerActivity.ADDRESS_STRING;
import static com.example.shayanmoradi.ezshop.location.MapsMarkerActivity.LAT_INT;
import static com.example.shayanmoradi.ezshop.location.MapsMarkerActivity.LONG_INT;

public class OrderInfoActivity extends AppCompatActivity {
    private int lat;
    private int lon;
    private String address;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, OrderInfoActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        try {
            lat = (int) getIntent().getSerializableExtra(LAT_INT);
            lon = (int) getIntent().getSerializableExtra(LONG_INT);
            address = (String) getIntent().getSerializableExtra(ADDRESS_STRING);
        } catch (Exception e) {
            Log.e("test", "from bag");
        }
        if(lat!=0&&lon!=0){
            Bundle args = new Bundle();
            args.putInt(LAT_INT, lat);
            args.putInt(LONG_INT,lon);
            args.putString(ADDRESS_STRING,address);
            OrderInfoFragment fragment = new OrderInfoFragment();

            fragment.setArguments(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.order_info_container, fragment, "test")
                    .commit();

        }
        ////////

else {
      OrderInfoFragment fragment1 = OrderInfoFragment.newInstance();


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.order_info_container, fragment1, "test")
                .commit();
    }}
}
