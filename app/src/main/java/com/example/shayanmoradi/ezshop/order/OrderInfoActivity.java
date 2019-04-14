package com.example.shayanmoradi.ezshop.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shayanmoradi.ezshop.R;

import androidx.appcompat.app.AppCompatActivity;

public class OrderInfoActivity extends AppCompatActivity {
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, OrderInfoActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        OrderInfoFragment fragment1 = OrderInfoFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()

                .replace(R.id.order_info_container,fragment1,"test")
                .commit();
    }
}
