package com.example.shayanmoradi.ezshop.review;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shayanmoradi.ezshop.R;

import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {
    private static final String PRODUCT_ID="com.example.shayanmoradi.ezshop.review";
    public static Intent newIntent(Context context, int productId) {
        Intent intent = new Intent(context, ReviewActivity.class);
        intent.putExtra(PRODUCT_ID, productId);

        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        int productID = (int) getIntent().getSerializableExtra(PRODUCT_ID);
        ReViewFragment fragment1 = ReViewFragment.newInstance(productID);
        getSupportFragmentManager()
                .beginTransaction()

                .replace(R.id.review_container,fragment1,"review_tag")
                .commit();
    }
}
