package com.bignews9527.vidio.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.activity.R;
import com.bignews9527.vidio.beans.VideoRadioBean;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;

/**
 * Created by 1945374040 on 2016/11/10.
 */
public class Radio extends Fragment {

    private XRecyclerView radio_xrecyclerview;
    private RecyclerView.Adapter adapter;
    private OkHttpClient client;
    private VideoRadioBean bean;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<VideoRadioBean.CListBean> list = new ArrayList<>();
    private int lastID, lastPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        return inflater.inflate(R.layout.fragment_pager_radio, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        client = new OkHttpClient();

        context = getActivity();

        initView(view);

        initData();

        initAdapter();

        initListener();
    }

    private void initListener() {

        radio_xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    private void initAdapter() {

        radio_xrecyclerview.setAdapter(adapter = new MyAdapter());
    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection)
                            new URL("http://c.3g.163.com/nc/topicset/android/radio/index.html").openConnection();
                    conn.connect();
                    if (conn.getResponseCode() == 200) {
//                        Log.i("tmd", "initData: successful");
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        for (String line = null; (line = br.readLine()) != null; ) {
                            sb.append(line);
                        }
//                        Log.i("tmd", "initData: " + sb);
                        bean = new Gson().fromJson(sb.toString(), VideoRadioBean.class);
                        list.clear();
                        list.addAll(bean.getCList());
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                radio_xrecyclerview.refreshComplete();
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView(View view) {

        radio_xrecyclerview = (XRecyclerView) view.findViewById(R.id.radio_xrecyclerview);
        radio_xrecyclerview.setLayoutManager(new LinearLayoutManager(context));
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {


        private void glide(final int position, final ImageView iv, TextView tv, final int i){
            Glide.with(context).load(list.get(position).getTList().get(i).getRadio().getImgsrc())
                    .asBitmap()
//                    .transform(new CircleTransform(context))
                    .into(iv);
            tv.setText(list.get(position).getTList().get(i).getRadio().getTitle());
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Log.i("tmd", "onClick: "+position+"    "+iv.getId());
                    Log.i("tmd", "onClick: "+list.get(position).getTList().get(i).getRadio().getPostid());

                    startActivity(new Intent(context, RadioOpenActivity.class).putExtra("postid",list.get(position).getTList().get(i).getRadio().getPostid()));
                    lastID = iv.getId();
                    lastPosition = position;
                }
            });
        }
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(inflater.inflate(R.layout.fragment_pager_radio_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, int position) {

            holder.radio_item_tv.setText(list.get(position).getCname());

//            holder.radio_item_tv1.setText(list.get(position).getTList().get(0).getRadio().getTitle());
//            holder.radio_item_tv2.setText(list.get(position).getTList().get(1).getRadio().getTitle());
//            holder.radio_item_tv3.setText(list.get(position).getTList().get(2).getRadio().getTitle());

            glide(position, holder.radio_item_iv1,holder.radio_item_tv1,0);
            glide(position, holder.radio_item_iv2,holder.radio_item_tv2,1);
            glide(position, holder.radio_item_iv3,holder.radio_item_tv3,2);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            public TextView radio_item_tv;
            public TextView radio_item_tv1;
            public TextView radio_item_tv2;
            public TextView radio_item_tv3;
            ImageView radio_item_iv1;
            ImageView radio_item_iv2;
            ImageView radio_item_iv3;
            public MyHolder(View itemView) {
                super(itemView);
                this.radio_item_tv = (TextView) itemView.findViewById(R.id.radio_item_tv);
                this.radio_item_tv1 = (TextView) itemView.findViewById(R.id.radio_item_tv1);
                this.radio_item_tv2 = (TextView) itemView.findViewById(R.id.radio_item_tv2);
                this.radio_item_tv3 = (TextView) itemView.findViewById(R.id.radio_item_tv3);
                radio_item_iv1 = (ImageView) itemView.findViewById(R.id.radio_item_iv1);
                radio_item_iv2 = (ImageView) itemView.findViewById(R.id.radio_item_iv2);
                radio_item_iv3 = (ImageView) itemView.findViewById(R.id.radio_item_iv3);
            }
        }
    }
}
