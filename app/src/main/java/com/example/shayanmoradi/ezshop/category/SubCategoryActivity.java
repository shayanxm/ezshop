package com.example.shayanmoradi.ezshop.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shayanmoradi.ezshop.R;

import androidx.appcompat.app.AppCompatActivity;

public class SubCategoryActivity extends AppCompatActivity {
    private static final String CATEGORY_ID="com.example.shayanmoradi.ezshop.subcategory.categoryid";
    public static Intent newIntent(Context context, int productId) {
        Intent intent = new Intent(context, SubCategoryActivity.class);
        intent.putExtra(CATEGORY_ID, productId);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        int productID = (int) getIntent().getSerializableExtra(CATEGORY_ID);
        SubCategoryFragment fragment1 = SubCategoryFragment.newInstance(productID);
        getSupportFragmentManager()
                .beginTransaction()

                .replace(R.id.sub_category_continer,fragment1)
                .commit();

    }
}
