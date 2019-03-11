package com.example.shayanmoradi.ezshop.filter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shayanmoradi.ezshop.R;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.shayanmoradi.ezshop.result.ReulstFragment.CATEGORY_ID;

public class FilterActivity extends AppCompatActivity {
    public static Intent newIntent(Context context, int categoryId) {
        Intent intent = new Intent(context, FilterActivity.class);

        intent.putExtra(CATEGORY_ID, categoryId);

        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        int categoryID = (int) getIntent().getSerializableExtra(CATEGORY_ID);


        FilterFragment fragment1 = FilterFragment.newInstance(categoryID);
        getSupportFragmentManager()
                .beginTransaction()

                .replace(R.id.filter_continer,fragment1)
                .commit();
    }
}
