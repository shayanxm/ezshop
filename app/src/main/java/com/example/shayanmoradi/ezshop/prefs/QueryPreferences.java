package com.example.shayanmoradi.ezshop.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class QueryPreferences {
    private static final String LEATEST_ITEM_ID = "leatesItemId";
    private static final String IS_LOGIN = "islogin";
    private static final String CUSTOMER_NAME = "customername";
    private static final String CUSTOMER_EMAIL = "customeremail";
    private static final String CUSTOMER_ID = "customerId";
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

    //////////////
    public static void setCustomerId(Context context, int qurey) {
        // context.getSharedPreferences("ezShopPrefs", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences
                .edit()
                .putInt(CUSTOMER_ID, qurey)
                .apply();

    }

    public static int getCustomerId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(CUSTOMER_ID, 0);
    }





/////
    public static void setIsLogin(Context context, boolean isLogin) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putBoolean(IS_LOGIN, isLogin)
                .apply();
    }

    public static boolean getIsLogin(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public static void setCustomerEmail(Context context, String customerEmail) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()

                .putString(CUSTOMER_EMAIL, customerEmail)
                .apply();
    }

    public static String getCustomerEmail(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(CUSTOMER_EMAIL, null);
    }
    public static void setCustomerName(Context context, String customerEmail) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()

                .putString(CUSTOMER_NAME, customerEmail)
                .apply();
    }

    public static String getCustomerName(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(CUSTOMER_NAME, null);
    }


}
