package com.bignews9527.vidio.video;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignews9527.vidio.Adapter.XRecyclerViewAdapter;
import com.bignews9527.vidio.beans.VideoBean;
import com.bignews9527.vidio.retrofit.VideoRetrofit;
import com.news.activity.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 1945374040 on 2016/11/10.
 */
public class Video extends Fragment {


    private int page = 0;

    private final String BASEURL = "http://c.3g.163.com/nc/";

    private OkHttpClient client;
    private VideoBean bean;
    private Context context;
    private LayoutInflater inflater;
    private XRecyclerView xRecyclerView;
    private RecyclerView.Adapter xRecyclerView_adapter;
    private ArrayList<VideoBean.VideoSidListBean> videoSidList = new ArrayList<>();
    private ArrayList<VideoBean.VideoListBean> videoList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;

        return inflater.inflate(R.layout.fragment_pager_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();

        client = new OkHttpClient();

        initView(view);

        initData();

        initAdapter();

        initListener();

    }

    private void initListener() {

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                page = 0;
                videoList.clear();
                videoSidList.clear();
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

        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        xRecyclerView.setAdapter(xRecyclerView_adapter = new XRecyclerViewAdapter(videoSidList, videoList, inflater, context));

    }

    private void initData() {

        //创建新的Retrofit下载json数据并封装到VideoBean中
        new Retrofit.Builder()
                .baseUrl(BASEURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) //添加支持gson解析的能力
                .build()
                .create(VideoRetrofit.class)
                .getVideoBasic("video/home/" + page + "-10.html")
                .enqueue(new Callback<VideoBean>() {
                    @Override
                    public void onResponse(Call<VideoBean> call, Response<VideoBean> response) {


                        bean = response.body();
                        if (bean != null) {
                            Log.i("tmd", "onResponse: " + bean);

//                        Log.i("tmd", "onResponse: "+ bean);
                            videoSidList.clear();
                            videoSidList.addAll(bean.getVideoSidList());
                            videoList.addAll(bean.getVideoList());
                            xRecyclerView_adapter.notifyDataSetChanged();
                        }
                        xRecyclerView.loadMoreComplete();
                        xRecyclerView.refreshComplete();

//                        Log.i("tmd", "onResponse: bean:" + bean);

                        //初始化RadioButton中的子控件
//                        initLinearLayout(bean.getVideoSidList());


                    }


                    @Override
                    public void onFailure(Call<VideoBean> call, Throwable t) {

                        Log.i("tmd", "onFailure: 失败的原因为：  " + t.getMessage());
                    }
                });

    }

//    private void initLinearLayout(List<VideoBean.VideoSidListBean> videoSidList) {
//
//        for (int i = 0; i < videoSidList.size(); i++) {
//            VideoBean.VideoSidListBean bean = videoSidList.get(i);
//            final RadioButton button = new RadioButton(context);
//            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(group.getWidth()/videoSidList.size(), ViewGroup.LayoutParams.WRAP_CONTENT);
////            params.setMargins(10,10,10,10);
//
//            //设置好params，id， 以及显示内容
//            button.setLayoutParams(params);
//            button.setId(i);
//            button.setText(bean.getTitle());
//
//            // 设置不同状态下button的背景色
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                button.setBackground(getResources().getDrawable(R.drawable.video_radiobutton_color));
//            }
//
//            // 设置button的显示图片
//            Glide.with(context).load(bean.getImgsrc()).asBitmap()
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                            button.setButtonDrawable(new BitmapDrawable());
//                            Drawable drawable = new BitmapDrawable(resource);
//                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                            button.setCompoundDrawables(null, drawable, null, null);
//                            button.setGravity(Gravity.CENTER_HORIZONTAL);
//                        }
//                    });
//
//            //给radiobutton设置点击事件，跳转页面
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Log.i("tmd", "onClick: "+view.getId());
//                }
//            });
//            group.addView(button);
//        }
//    }

    private void initView(View view) {
//        recyclerView = (RecyclerView) view.findViewById(R.id.video_recyclerview);
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.video_xrecyclerview);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
    }
}
