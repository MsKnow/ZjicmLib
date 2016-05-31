package com.know.zjicmlib.util;

import android.widget.Toast;

import com.know.zjicmlib.APP;

/**
 * Created by know on 2016/4/14.
 */
public class ToastUtil {

    public static void tShort(CharSequence str){
        Toast.makeText(APP.aContext,str,Toast.LENGTH_SHORT).show();
    }



}
