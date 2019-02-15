package com.example.shayanmoradi.ezshop.network;

import com.example.shayanmoradi.ezshop.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("products?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0" +
            "fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f")
    Call<List<Product>> getRot();


    @GET("products?orders?=date_created&consumer_key=" +
            "ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=" +
            "cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f")
    Call<List<Product>> getNewest();

}
