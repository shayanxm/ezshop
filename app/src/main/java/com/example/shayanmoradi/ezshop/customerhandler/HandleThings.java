package com.example.shayanmoradi.ezshop.customerhandler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.shayanmoradi.ezshop.DissConectedFragment;

import androidx.fragment.app.FragmentManager;

public class HandleThings {

    public static boolean isOnline(Context context, FragmentManager fragmentManager) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            //  Toast.makeText(context, "No Internet connection!", Toast.LENGTH_LONG).show();
            DissConectedFragment datePickerFragment = new DissConectedFragment();
//            datePickerFragment.setTargetFragment(Dis.this,
//                    0);
            datePickerFragment.show(fragmentManager, "MyDialog");
            return false;

        }
        return true;
    }



}
