package com.read.fragment.readfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.read.activity.AuthorInfoActivity;
import com.news.activity.R;
import com.read.activity.ReadContentActivity;
import com.read.adapter.ReadSubscriberAdapter;
import com.read.bean.ReadSubscriberBean;
import com.read.itfc.LongRetrofitInterface;
import com.read.utils.UrlUtil;
import com.read.view.HeightSumListView;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 2016-11-18.
 */
public class ReadSubscriberFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rcv_myread;
    private HeightSumListView lv_subscriber_read;
    private CardView btn_viewmore_readminefrgmt;
    private ArrayList<ReadSubscriberBean.RecommendlistBean> recommendList = new ArrayList<>();
    private Context context;
    private ReadSubscriberAdapter subscriberAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_subscriber, null);
        context = getContext();
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();

        initAdapter();

    }

    private void initAdapter() {
        //设置我的订阅Adapter


        //设置推荐订阅Adapter
        subscriberAdapter = new ReadSubscriberAdapter(recommendList,context);
        lv_subscriber_read.setAdapter(subscriberAdapter);
        //设置adapter点击事件，即item的子控件的点击事件
        subscriberAdapter.setOnItemChildrenClickListener(new ReadSubscriberAdapter.OnItemChildrenClickListener() {
            @Override
            public void onAuthorInfoClick(String eName) {
                Log.i("MSL", "onAuthorInfoClick: 点击作者信息");
                Intent intent = new Intent(context,AuthorInfoActivity.class);
                intent.putExtra("pid",eName);
                startActivity(intent);
            }

            @Override
            public void onSubscriberBtnClick(View view, String eName) {
                Log.i("MSL", "onSubscriberBtnClick: 点击收藏");

            }

            @Override
            public void onHotArticleClick(String docId) {
                Log.i("MSL", "onHotArticleClick: 点击文章ID");
                Intent intent = new Intent(context, ReadContentActivity.class);
                intent.putExtra("docId",docId);
                startActivity(intent);
            }
        });

    }

    private void initData() {
        //获取我的订阅数据


        //获取推荐订阅数据
        Retrofit getSubscriber = new Retrofit.Builder()
                .baseUrl(UrlUtil.URL_HEAD)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getSubscriber.create(LongRetrofitInterface.class).getSubscriberData().enqueue(new Callback<ReadSubscriberBean>() {
            @Override
            public void onResponse(Call<ReadSubscriberBean> call, Response<ReadSubscriberBean> response) {
                try {
                    recommendList.addAll(response.body().getRecommendlist());
                    Log.i("MSL", "onResponse: " + recommendList.size());
                    subscriberAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ReadSubscriberBean> call, Throwable t) {
                Log.i("MSL", "onFailure: ");
            }
        });
    }

    private void initView(View view) {
        rcv_myread = (RecyclerView) view.findViewById(R.id.rcv_myread);
        lv_subscriber_read = (HeightSumListView) view.findViewById(R.id.rcv_subscriber_read);
        btn_viewmore_readminefrgmt = (CardView) view.findViewById(R.id.btn_viewmore_readminefrgmt);

        btn_viewmore_readminefrgmt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_viewmore_readminefrgmt:

                break;
        }
    }
}
