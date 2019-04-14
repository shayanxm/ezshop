package com.example.shayanmoradi.ezshop.enterinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shayanmoradi.ezshop.R;

import androidx.appcompat.app.AppCompatActivity;

public class EnterInfoActivity extends AppCompatActivity {
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EnterInfoActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_info);
        EnterInfoFragment fragment1 = EnterInfoFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()

                .replace(R.id.enter_info_container,fragment1,"test")
                .commit();
    }
}
