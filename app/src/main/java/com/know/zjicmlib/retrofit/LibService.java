package com.know.zjicmlib.retrofit;

import com.know.zjicmlib.modle.bean.Douban;

import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by know on 2016/4/13.
 */
public interface LibService {

    @GET("opac/openlink.php?match_flag=forward&displaypg=30")
    Call<ResponseBody> searchBoos(@Query("title")String booN,@Query("page")int page);

    @GET("opac/openlink.php?match_flag=forward&displaypg=30")
    Observable<ResponseBody> searchBooss(@Query("title")String booN,@Query("page")int page);

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
}
