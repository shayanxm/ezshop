package com.example.shayanmoradi.ezshop.itemDetail;

import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.SingleFragmentActivity;

import androidx.fragment.app.Fragment;

public class ItemDetailActivity extends SingleFragmentActivity {


    @Override
    public Fragment createFragment() {
        return new ItemDetailFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_item_detail;
    }
}
