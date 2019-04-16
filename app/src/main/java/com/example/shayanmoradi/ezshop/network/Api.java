package com.example.shayanmoradi.ezshop.network;

import com.example.shayanmoradi.ezshop.Model.Category;
import com.example.shayanmoradi.ezshop.Model.Product;
import com.example.shayanmoradi.ezshop.Model.Review;
import com.example.shayanmoradi.ezshop.Model.Terms;
import com.example.shayanmoradi.ezshop.Model.orderingModels.Coupon;
import com.example.shayanmoradi.ezshop.Model.orderingModels.Customer;
import com.example.shayanmoradi.ezshop.Model.orderingModels.OrderJsonBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    public String AUTHENTICATION = "consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba";

    @GET("products?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f")
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
    Call<Product> getProductById(@Path("name") int productId);


    @GET("products/categories?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f&per_page=30")
    Call<List<Category>> getCatrgories();

    @GET("products?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f&per_page=100")
    Call<List<Product>> getCategoriesItemsByCategoryItem(@Query("category") int categoryId);

    @GET("products?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f&per_page=100")
    Call<List<Product>> sortCategoriesItemsByCategoryItem(@Query("category") int categoryId, @Query("orderby") String order, @Query("order") String orders);

    @GET("products?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f&per_page=100")
    Call<List<Product>> sortCategoriesItemsByCategoryItemSearched(@Query("orderby") String order, @Query("order") String orders, @Query("search") String name);

    @GET("products?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba&per_page=100")
    Call<List<Product>> getAllProducts();

    @GET("products?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f&per_page=100")
    Call<List<Product>> searchWithName(@Query("search") String name);


    @GET("  products/attributes/{id}/terms?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba&per_page=100")
    Call<List<Terms>> getTerms(@Path("id") int attributeId);

    ///
    @POST("customers?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    @FormUrlEncoded
    Call<Customer> createCustomer(@Field("first_name") String first_name,
                                  @Field("last_name") String last_name,
                                  @Field("username") String username,
                                  @Field("email") String email);

    @POST("orders?" + AUTHENTICATION)
    Call<Customer> sendOrder(@Body OrderJsonBody orderJsonBody);


    @GET(" products/reviews?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    Call<List<Review>> getProductReviewsById(@Query("product") int productId);


    @POST("products/reviews?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    @FormUrlEncoded
    Call<Customer> sendNewReview(@Field("product_id") int product_id,
                                 @Field("review") String review,
                                 @Field("reviewer") String reviewer_name,
                                 @Field("reviewer_email") String email);

    @DELETE("https://woocommerce.maktabsharif.ir/wp-json/wc/v3/products/reviews/{id}?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba&product=81&force=true")
    Call<Review> deleteReview(@Path("id") int reviewId);

    @PUT("https://woocommerce.maktabsharif.ir/wp-json/wc/v3/products/reviews/{id}?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba&product=81&force=true")
    Call<Review> updateReview(@Path("id") int revieId, @Query("review") String updatedText);

    @GET("coupons/{name}?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f")
    Call<Coupon> getCouponById(@Path("name") String couponId);
    @GET("coupons?consumer_key=ck_1e873ec76b5b531ed19a5458dcfa29f7cabf0fa6&consumer_secret=cs_4710a5d0b9657629f677eeff9cbe6a5ce8e9ca8f")
    Call <List<Coupon>> getCoupons();

}
