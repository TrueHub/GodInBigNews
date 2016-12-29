package com.bignews9527.vidio.video;

import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import com.news.activity.R;
import com.bignews9527.vidio.Adapter.MengWuXRecyclerViewAdapter;
import com.bignews9527.vidio.beans.MengWuBean;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by 1945374040 on 2016/11/14.
 */
public class MengWuActivity extends FragmentActivity {


    private XRecyclerView video_qipa_xrecyclerview;
    private OkHttpClient client;
    private LayoutInflater inflater;
    private int page;
    private RecyclerView.Adapter video_qipa_xrecyclerview_adapter;
    private ArrayList<MengWuBean.VAP4BFR16Bean> list = new ArrayList<>();
    private String BASEURL = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qipa);

        BASEURL = getIntent().getStringExtra("url");

        inflater = LayoutInflater.from(this);

        client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.HOURS)
                .readTimeout(1, TimeUnit.HOURS)
                .build();

        initView();

        initData();

        initAdapter();

        initListener();
    }

    private void initListener() {

        video_qipa_xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                page = 0;
                initData();
            }

            @Override
            public void onLoadMore() {

                page += 10;
                initData();
            }
        });
    }

    private void initAdapter() {

        video_qipa_xrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        video_qipa_xrecyclerview.setAdapter(video_qipa_xrecyclerview_adapter = new MengWuXRecyclerViewAdapter(list, inflater, MengWuActivity.this));

    }

    private void initData() {
        //创建新的Retrofit下载json数据并封装到VideoBean中
//        new Retrofit.Builder()
//                .baseUrl(BASEURL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create()) //添加支持gson解析的能力
//                .build()
//                .create(VideoRetrofit.class)
//                .getQiPaBasic("video/home/0-10.html")
//                .enqueue(new Callback<MengWuBean>() {
//                    @Override
//                    public void onResponse(Call<MengWuBean> call, Response<MengWuBean> response) {
//
//                        list.addAll(response.body().getVAP4BFE3U());
//                        Log.i("tmd", "onResponse: "+ list);
//                        video_qipa_xrecyclerview_adapter.notifyDataSetChanged();
//                        video_qipa_xrecyclerview.loadMoreComplete();
//                        video_qipa_xrecyclerview.refreshComplete();
//                    }
//
//                    @Override
//                    public void onFailure(Call<MengWuBean> call, Throwable t) {
//
//                    }
//                });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(BASEURL).openConnection();
                    conn.connect();
                    if (conn.getResponseCode() == 200) {
                        Log.i("tmd", "initData: successful");
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        for (String line = null; (line = br.readLine()) != null; ) {
                            sb.append(line);
                        }
                        Log.i("tmd", "initData: " + sb);
                        MengWuBean bean = new Gson().fromJson(sb.toString(), MengWuBean.class);
                        list.addAll(bean.getVAP4BFR16());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                video_qipa_xrecyclerview_adapter.notifyDataSetChanged();
                                video_qipa_xrecyclerview.loadMoreComplete();
                                video_qipa_xrecyclerview.refreshComplete();
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
//        client.newCall(new Request.Builder()
//                .get()
//                .url(BASEURL)
//                .build())
//                .enqueue(new okhttp3.Callback() {
//                    @Override
//                    public void onFailure(okhttp3.Call call, IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
//
//                        ResponseBody rb = response.body();
//                        Log.i("tmd", "onResponse    bean:1111 "+rb.toString()+"   "+rb.contentLength());
//                        String info = rb.string();
//                        Log.i("tmd", "onResponse    bean2222: "+info);
//                        MengWuBean bean = new Gson().fromJson(info, MengWuBean.class);
//                        list.addAll(bean.getVAP4BFE3U());
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                video_qipa_xrecyclerview_adapter.notifyDataSetChanged();
//                                video_qipa_xrecyclerview.loadMoreComplete();
//                                video_qipa_xrecyclerview.refreshComplete();
//                            }
//                        });
//                    }
//                });
    }

    private void initView() {
        video_qipa_xrecyclerview = (XRecyclerView) findViewById(R.id.video_qipa_xrecyclerview);
        video_qipa_xrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        video_qipa_xrecyclerview.setPullRefreshEnabled(true);
        video_qipa_xrecyclerview.setLoadingMoreEnabled(true);
    }
}
