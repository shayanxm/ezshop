package com.example.shayanmoradi.ezshop.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class QueryPreferences {
    private static final String LEATEST_ITEM_ID = "leatesItemId";

    public static void setStoredQuery(Context context, int qurey) {
        // context.getSharedPreferences("ezShopPrefs", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences
                .edit()
                .putInt(LEATEST_ITEM_ID, qurey)
                .apply();

    }

    public static int getStoredQurey(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(LEATEST_ITEM_ID, 0);
    }

}
