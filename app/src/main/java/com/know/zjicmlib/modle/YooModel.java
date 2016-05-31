package com.know.zjicmlib.modle;

import com.know.zjicmlib.modle.bean.Boo;
import com.know.zjicmlib.modle.bean.Yoo;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by know on 2016/4/16.
 */
public interface YooModel {


    Yoo parseHtml1(ResponseBody body);

    List<Boo> parseHtml2(ResponseBody body);

    void storeYoo(Yoo yoo);

}
