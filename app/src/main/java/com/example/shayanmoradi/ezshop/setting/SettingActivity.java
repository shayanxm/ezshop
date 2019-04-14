package com.example.shayanmoradi.ezshop.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shayanmoradi.ezshop.R;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {


        public static Intent newIntent(Context context) {
            Intent intent = new Intent(context,SettingActivity.class);



            return intent;
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SettingFragment fragment1 = SettingFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()

                .replace(R.id.setting_container,fragment1,"test")
                .commit();
    }
}
