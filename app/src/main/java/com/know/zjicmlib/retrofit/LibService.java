package com.know.zjicmlib.retrofit;

import com.know.zjicmlib.modle.bean.Douban;
import com.know.zjicmlib.modle.bean.Version;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by know on 2016/4/13.
 */
public interface LibService {

    @GET("opac/openlink.php?match_flag=forward&displaypg=30")
    Call<ResponseBody> searchBoos(@Query("title")String booN,@Query("page")int page);

    @GET("opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&doctype=ALL" +
            "&displaypg=30&showmode=list&sort=CATA_DATE&orderby=desc&dept=ALL")
    Observable<ResponseBody> searchBooss(@Query("strText")String booN,@Query("page")int page);

    @GET("opac/item.php")
    Observable<ResponseBody> getOneBoos(@Query("marc_no")String marc_no);

    @GET("opac/ajax_douban.php")
    Observable<Douban> getDouban(@Query("isbn") String isbn);

    @FormUrlEncoded
    @POST("reader/redr_verify.php")
    Observable<ResponseBody> login(@Field("number")String id,
    @Field("passwd")String password,@Field("select")String certNo);


    @GET("reader/book_lst.php")
    Observable<ResponseBody> getMyBoos();

    @GET("reader/ajax_renew.php")
    Observable<ResponseBody> renewMyBoo(@Query("bar_code")String bar_code,
    @Query("check")String check, @Query("time")String time);

    @GET("http://218.75.124.139:8091/sms/opac/news/showNewsList.action?type=1&xc=4&pageSize=20")
    Observable<ResponseBody> getHome();

    @GET("http://10.2.8.163:8083/opac/top100.php")
    Observable<ResponseBody> getRank();

    @GET("http://7xr6e8.com1.z0.glb.clouddn.com/LibJson/Version2.txt")
    Observable<Version> getLatestVersion(@Query("time")Long time);


    @FormUrlEncoded
    @POST("http://mrknow.sinaapp.com/postMe.php")
    Observable<ResponseBody> postMe(@Field("version")int version,@Field("id")String id,@Field("major")String major,
                                    @Field("sex")String sex);
    @FormUrlEncoded
    @POST("http://mrknow.sinaapp.com/myapp/sugg.php")
    Observable<ResponseBody> postSug(@Field("id")String id,@Field("sug")String sug);

}
