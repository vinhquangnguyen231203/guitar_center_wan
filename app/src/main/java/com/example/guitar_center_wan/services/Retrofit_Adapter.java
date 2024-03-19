package com.example.guitar_center_wan.services;

import com.example.guitar_center_wan.entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Adapter {
    private static final String BASE_URL = "http://10.0.2.2:8080/guitar_center/rest/";

    public static Retrofit getRetrofitInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return  retrofit;
    }

}