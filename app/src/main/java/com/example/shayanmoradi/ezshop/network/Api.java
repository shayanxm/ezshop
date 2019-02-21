package com.example.shayanmoradi.ezshop.network;

import com.example.shayanmoradi.ezshop.Model.Category;
import com.example.shayanmoradi.ezshop.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("products?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0" +
            "fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f")
    Call<List<Product>> getRot();


    @GET("products?orders?=date_created&consumer_key=" +
            "ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=" +
            "cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f")
    Call<List<Product>> getNewest();

    @GET("products?&consumer_key=" +
            "ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=" +
            "cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f")
    Call<List<Product>> getCommitsByName(@Query("orderby") String order);

    @GET("products/{name}?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f")
    Call<Product> getProductById(@Path("name") int  productId);


    @GET("products/categories?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f&per_page=30")
    Call<List<Category>> getCatrgories();

    @GET("products?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f&per_page=100")
    Call<List<Product>> getCategoriesItemsByCategoryItem(@Query("category") int  categoryId);



    @GET("products?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba&per_page=100")
    Call<List<Product>> getAllProducts();
}
