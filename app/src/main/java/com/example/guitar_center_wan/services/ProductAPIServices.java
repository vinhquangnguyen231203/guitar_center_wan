package com.example.guitar_center_wan.services;

import com.example.guitar_center_wan.entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductAPIServices {

    @GET("product")
    Call<List<Product>> getAllProduct();

    @POST("product")
    Call<Boolean> insertProduct(@Body Product product);

    @DELETE("product/{id}")
    Call<Boolean> deleteProduct(@Path("id") String id);

    @PUT("product")
    Call<Boolean> updateProduct(@Body Product product);

}
