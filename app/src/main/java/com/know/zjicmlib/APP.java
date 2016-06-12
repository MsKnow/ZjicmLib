package com.know.zjicmlib;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.know.zjicmlib.modle.bean.Yoo;
import com.litesuits.orm.LiteOrm;

import java.util.List;

/**
 * Created by know on 2016/4/14.
 */
public class APP extends Application {
    public static int Version;
    public static Context aContext;
    public static Yoo yoo;
    public static LiteOrm mDb;
    public static boolean online = false;

    @Override
    public void onCreate() {
        super.onCreate();
        aContext = this;
        //Version = getPackageManager()
        //System.out.println("app---------------------------------oncreate");
        Log.e("version",getVersionCode(aContext)+"--------"+getVersionName(aContext));
        mDb = LiteOrm.newSingleInstance(aContext,"lib.db");
        mDb.setDebugged(false);
        //mDb.delete(Yoo.class);
        //
        yoo = new Yoo();
        yoo = getYoo();
        //pullYoo();
    }

    private void pullYoo(){
        //mDb.delete(Yoo.class);
        List<Yoo> yous = mDb.query(Yoo.class);
        if(yous.size()>0){
            for (Yoo you :yous){
                System.out.println(you.getPassword());
                yoo = you;
            }
            if (yoo.getPassword() != null){
                online = true;
            }
        }


    }

    public static Yoo getYoo(){
        Log.e("app.getyoo","调用");
        if(yoo == null){
            Log.e("you == null","后台未清理 yoo清空？");
            yoo = new Yoo();

        }else{
            List<Yoo> yous = mDb.query(Yoo.class);
            if(yous.size()>0){
                for (Yoo you :yous){
                    System.out.println("------------------"+you.getId());
                    System.out.println("------------------"+you.getPassword());
                    yoo = you;
                }
                if (yoo.getPassword() != null){
                    online = true;
                }
            }
            /*Log.e("已经有了","这不该是第一次启动时调用的");
            System.out.println(yoo.getId());
            System.out.println(yoo.getPassword());*/
        }
        return yoo;
    }

    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

}
