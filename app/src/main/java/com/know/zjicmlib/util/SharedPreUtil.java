package com.know.zjicmlib.util;

import android.content.SharedPreferences;

import com.know.zjicmlib.APP;

/**
 * Created by know on 2016/4/18.
 */
public class SharedPreUtil {

    private SharedPreUtil(){}

    static private SharedPreferences sharedPre ;
    SharedPreferences.Editor editor;

    public static SharedPreferences getSharedPre(){
        if(sharedPre==null){
            sharedPre = APP.aContext.getSharedPreferences("cookies",0);
        }
        return  sharedPre;
    }

    private static SharedPreferences.Editor getEdit(){
        return getSharedPre().edit();
    }

    public static void putString(String key,String str){

        SharedPreferences.Editor editor = getEdit();
        editor.putString(key,str);
        editor.commit();

    }

    public static void putBool(String key,Boolean bool){

        SharedPreferences.Editor editor = getEdit();
        editor.putBoolean(key,bool);
        editor.commit();

    }





}
