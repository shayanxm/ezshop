package com.example.shayanmoradi.ezshop.category;

import android.os.Bundle;

import com.example.shayanmoradi.ezshop.R;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
       // int productID = (int) getIntent().getSerializableExtra(PRODUCT_ID);
        CategoryFragment fragment1 = CategoryFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()

                .replace(R.id.category_continer,fragment1)
                .commit();
    }
}
