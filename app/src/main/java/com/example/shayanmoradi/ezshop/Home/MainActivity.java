package com.example.shayanmoradi.ezshop.Home;

import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.SingleFragmentActivity;

import androidx.fragment.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }
}

