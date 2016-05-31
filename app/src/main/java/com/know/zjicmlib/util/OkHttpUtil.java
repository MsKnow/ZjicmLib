package com.know.zjicmlib.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.know.zjicmlib.APP;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by know on 2016/2/20.
 */
public class OkHttpUtil {
    //File httpCacheDirectory = new File(APP.aContext.getCacheDir(), "responses");
    //Cache cache = new Cache(httpCacheDirectory, 1024*1024);
    private static OkHttpClient clientt;

    public static OkHttpClient getClient(){
        if(clientt==null){

            clientt = new OkHttpClient.Builder()  //clientt.newBuilder()
                    //.cache(new Cache(new File(APP.aContext.getCacheDir(),"responses"),1024*512))
                    .addNetworkInterceptor(chain -> {
                        Request request = chain.request();
                        String host = request.url().host();
                        //Log.e("URL",host);
                        String cookie = SharedPreUtil.getSharedPre().getString(host, "null");

                        Request requestt = request.newBuilder()
                                //.addHeader("Cookie", "PHPSESSID=u23kj0jk8mgbbo143n6d06qsc2")
                                .addHeader("Cookie", cookie)
                                .build();

                        Response response = chain.proceed("null".equals(cookie)?request:requestt);
                        String session = response.header("Set-Cookie");
                        if(session!=null){
                            session = session.substring(0,session.indexOf(";"));
                            Log.e("session",session);

                            SharedPreUtil.putString(host, session);

                        }else{
                            Log.e("session","null");
                        }

                        return response;


                    })
                    .connectTimeout(15, TimeUnit.SECONDS).build();
        }
        return clientt;
    }



     /**
     * 不会开启异步线程，所以一般在子线程调用？
     * @param request 传入request
     * @return 返回response
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException{
        return getClient().newCall(request).execute();
    }

    /**
     * 开启异步线程 调用callback方法时阻塞该线程嗯。
     * @param request
     * @param callback
     */
    public static void enqueue(Request request, Callback callback){
        getClient().newCall(request).enqueue(callback);
    }

    public static void getStr(String url,Callback callback){
        Request request = new Request.Builder().url(url).build();
        getClient().newCall(request).enqueue(callback);

    }

    //// TODO: 2016/2/20 加入参数为String的回调函数

    public static String getString(String url) throws IOException{
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()){
            return response.body().string();
        }else {
            throw new IOException("Unexpected code "+ response);
        }
    }


}
