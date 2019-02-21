package com.example.shayanmoradi.ezshop.itemsofcategory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shayanmoradi.ezshop.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ItemsOfActivity extends AppCompatActivity {
    private static final String CATRGORY_ID="com.example.shayanmoradi.ezshop.itemDetail.categoryid";
    private static final String CATEGORY_NAME = "com.example.shayanmoradi.ezshop.itemsofcategory.categoryname";
    public static Intent newIntent(Context context, int productId,String categoryName) {
        Intent intent = new Intent(context, ItemsOfActivity.class);
        intent.putExtra(CATRGORY_ID, productId);
        intent.putExtra(CATEGORY_NAME, categoryName);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_of);
        int productID = (int) getIntent().getSerializableExtra(CATRGORY_ID);
        String CategoryName= (String) getIntent().getSerializableExtra(CATEGORY_NAME);
        ItemsOfCategoryFragment fragment1 = ItemsOfCategoryFragment.newInstance(productID,CategoryName);
        getSupportFragmentManager()
                .beginTransaction()

        .replace(R.id.items_of_ctegory_continer,fragment1)
                .commit();

    }




}
