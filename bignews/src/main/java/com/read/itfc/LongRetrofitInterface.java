package com.read.itfc;

import com.read.bean.AuthorInfoBean;
import com.read.bean.ReadArticleBean;
import com.read.bean.ReadCommandBean;
import com.read.bean.ReadSubscriberBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dell on 2016-11-11.
 */
public interface LongRetrofitInterface {

    @GET("recommend/getSubDocPic?passport=&&version=5.3.1&from=yuedu")
    Call<ReadCommandBean> getData(@Query(value = "devID") String devID, @Query(value = "size") String page);

    @GET("/recommend/{long_path}?passport=&devId=285518037316772&size=20&version=5.3.1&from=yuedu")
    Call<ReadCommandBean> refreshOrLoadMore(@Path(value = "long_path") String path);

    @GET("nc/article/{docid}/full.html")
        Call<ReadArticleBean> getArticleData(@Path(value = "docid") String path);

    @GET("nc/topicset/subscribe/recommend.html")
        Call<ReadSubscriberBean> getSubscriberData();

    @GET("/nc/article/list/{eName}/0-20/.html")
    Call<AuthorInfoBean> getAuthorInfo(@Path(value = "eName") String eName);
}
