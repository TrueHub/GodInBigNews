package com.bignews9527.vidio.retrofit;


import com.bignews9527.vidio.beans.QiPaBean;
import com.bignews9527.vidio.beans.VideoBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by 1945374040 on 2016/11/11.
 */
public interface VideoRetrofit {

    @GET()
    Call<VideoBean> getVideoBasic(@Url String url);

    @GET()
    Call<QiPaBean> getQiPaBasic(@Url String url);
}
