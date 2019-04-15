package com.example.shayanmoradi.ezshop.itemDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shayanmoradi.ezshop.R;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {
    int productID;
    private static final String PRODUCT_ID = "com.example.shayanmoradi.ezshop.itemDetail.productid";

    public static Intent newIntent(Context context, int productId) {
        Intent intent = new Intent(context, ItemDetailActivity.class);
        intent.putExtra(PRODUCT_ID, productId);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        if ((int) getIntent().getSerializableExtra(PRODUCT_ID) != 0) {

        }else {
            Bundle extras = getIntent().getExtras();

            productID = (int) extras.getSerializable(PRODUCT_ID);
        }
        Log.e("test", "p id" + productID);
        ItemDetailFragment fragment1 = ItemDetailFragment.newInstance(productID);
        getSupportFragmentManager()
                .beginTransaction()

                .replace(R.id.item_detail_continer, fragment1, "test")
                .commit();


    }

    @Override
    public void onNewIntent(Intent intent) {

        setIntent(intent);
        productID = (int) getIntent().getSerializableExtra(PRODUCT_ID);

    }


}
