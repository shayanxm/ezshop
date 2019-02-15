package com.example.shayanmoradi.ezshop.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofitInstance;
    private static String BASE_URL="https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static Retrofit getRetrofitInstance(){
        if (retrofitInstance==null) {
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofitInstance;
    }
}
