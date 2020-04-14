package com.example.commonicon.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repositorio {
    private static final String ROOTURL = "http://www.iesmurgi.org:86/~basso/";
    private static final long TIME_OUT = 10;

    private static Retrofit getRetrofitInstance(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder().writeTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit.SECONDS).connectTimeout(TIME_OUT, TimeUnit.SECONDS).build();

        return new Retrofit.Builder().baseUrl(ROOTURL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
    }

    public static RetrofitService getUserService(){
        return getRetrofitInstance().create(RetrofitService.class);
    }
}
