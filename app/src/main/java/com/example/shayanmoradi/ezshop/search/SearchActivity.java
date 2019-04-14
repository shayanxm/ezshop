package com.example.shayanmoradi.ezshop.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shayanmoradi.ezshop.R;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
       // int productID = (int) getIntent().getSerializableExtra(PRODUCT_ID);
        SearchFragment fragment1 = SearchFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()

                .replace(R.id.search_continer,fragment1,"test")
                .commit();
    }
}
