package com.song.news.service;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.song.news.api.API;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by song on 2017/3/17.
 * Email：815464927@qq.com
 */

public class ReterfitHelper {
    private Context mContext;
    private Retrofit mRetrofit = null;
    OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static ReterfitHelper instance = null;
    public static ReterfitHelper getInstance(Context context){
        if(null == (instance)){
            instance = new ReterfitHelper(context);
        }
        return instance;
    }
    public ReterfitHelper(Context context){
        mContext = context;
        resetApp();
    }

    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(API.API)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
    public RetrofitService getService(){
        return mRetrofit.create(RetrofitService.class);
    }
}
