package com.example.shayanmoradi.ezshop.bag;

import android.os.Bundle;

import com.example.shayanmoradi.ezshop.R;

import androidx.appcompat.app.AppCompatActivity;

public class BagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);
        BagFragment fragment1 = BagFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()

                .replace(R.id.bag_continer,fragment1,"test")
                .commit();

    }
}
