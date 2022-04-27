package com.example.appreadbook.API;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitBuilder {
        private static Retrofit retrofit=null;
   public static Retrofit getClient(){
       if(retrofit==null){
           retrofit=new Retrofit.Builder()
                   .baseUrl("http://192.168.43.160/")
                   .addConverterFactory(ScalarsConverterFactory.create())
                   .build();

       }
       return retrofit;
     }


}
