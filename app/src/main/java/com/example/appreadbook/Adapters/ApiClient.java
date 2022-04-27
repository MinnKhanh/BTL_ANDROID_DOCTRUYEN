package com.example.appreadbook.Adapters;

import com.example.appreadbook.API.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().addNetworkInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.194.118/").addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        return retrofit;
    }
    public static ApiService getUserServicd(){
        ApiService userService=getRetrofit().create(ApiService.class);
        return userService;
    }

}
