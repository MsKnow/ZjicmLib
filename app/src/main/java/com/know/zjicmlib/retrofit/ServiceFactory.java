package com.know.zjicmlib.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.know.zjicmlib.util.OkHttpUtil;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by know on 2016/4/13.
 */
public class ServiceFactory {

    private static LibService service = null;

    public static  LibService getService(){

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.2.8.163:8083/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(OkHttpUtil.getClient())
                .build();
        service = retrofit.create(LibService.class);
        return service;
    }

}
