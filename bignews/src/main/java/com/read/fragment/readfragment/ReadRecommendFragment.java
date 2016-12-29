package com.read.fragment.readfragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.activity.R;
import com.read.activity.ReadContentActivity;
import com.read.bean.ReadCommandBean;
import com.read.itfc.LongRetrofitInterface;
import com.read.utils.UrlUtil;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 2016-11-11.
 */
public class ReadRecommendFragment extends Fragment {
    private final String[] urls = new String[]{UrlUtil.READ_RECOMMAND, UrlUtil.READ_SUBSCRIBE};//0.推荐 1.订阅
    private XRecyclerView xrvReadfragment;
    private ArrayList<ReadCommandBean.推荐Bean> list = new ArrayList<>();
    private int type;
    private RecommandRcvAdapter adapter;
    private String path = "getSubDocPic";   //上拉加载与下拉刷新时网址的变更部分
    private Intent intent;
    private String docId;
    private AlertDialog.Builder ab;
    private CharSequence[] unlikereson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_viewpager, container, false);

        initView(view);
        intent = new Intent(getActivity(), ReadContentActivity.class);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ab = new AlertDialog.Builder(getContext());

        initData(path);

        initAdapter();

        xrvReadfragment.setPullRefreshEnabled(true);
        xrvReadfragment.setLoadingMoreEnabled(true);
        xrvReadfragment.setRefreshProgressStyle(ProgressStyle.BallPulse);
        xrvReadfragment.setLoadingMoreProgressStyle(ProgressStyle.BallZigZag);
        xrvReadfragment.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                path = "getSubDocPic";
                initData(path);
                xrvReadfragment.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                Log.i("MSL", "onLoadMore: ");
                path = "getSubDocNews";
                initData(path);
                xrvReadfragment.loadMoreComplete();
            }
        });
        xrvReadfragment.setClickable(false);

    }

    private void initAdapter() {

        xrvReadfragment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));//设置不反转
        adapter = new RecommandRcvAdapter();
        xrvReadfragment.setAdapter(adapter);
        //item和item内子控件的点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                List<String> ublikeresons = new ArrayList<>();
                if (position == 0) {//第0条数据没有unlikereson这个数据，需要排除
                    ublikeresons.add("举报");
                    ublikeresons.add("不感兴趣");
                } else {
                    ublikeresons.addAll(list.get(position).getUnlikeReason());
                }
                unlikereson = ublikeresons.toArray(new CharSequence[ublikeresons.size()]);

                docId = list.get(position).getDocid();
                Log.i("MSL", "onItemClick: item被点击，跳转页面" + docId);
                intent.putExtra("docId", docId);
                intent.putExtra("unlikereson",unlikereson);
                startActivity(intent);

            }

            @Override
            public void onDeleteBtnClick(final int position) {

                Log.i("MSL", "onDeleteBtnClick: " + list.size());
                Log.i("MSL", "onDeleteBtnClick: " + position);
                Log.i("MSL", "onDeleteBtnClick: " + list.get(position).toString());

                List<String> ublikeresons = new ArrayList<>();
                if (position == 0) {//第0条数据没有unlikereson这个数据，需要排除
                    ublikeresons.add("举报");
                    ublikeresons.add("不感兴趣");
                } else {
                    ublikeresons.addAll(list.get(position).getUnlikeReason());
                }
                unlikereson = ublikeresons.toArray(new CharSequence[ublikeresons.size()]);
                boolean[] b = new boolean[ublikeresons.size()];

                //弹出popupupwindow ，复选 确定删除此条。
                ab.setTitle("选择不感兴趣的关键词(多选)")
                        .setMultiChoiceItems(unlikereson, b, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position);
                                adapter.notifyItemRemoved(position);
                            }
                        }).show();

            }
        });

    }

    private void initData(final String path) {

        Retrofit getRecommandRead = new Retrofit.Builder()
                .baseUrl(UrlUtil.URL_HEAD)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getRecommandRead.create(LongRetrofitInterface.class).refreshOrLoadMore(path).enqueue(new Callback<ReadCommandBean>() {
            @Override
            public void onResponse(Call<ReadCommandBean> call, Response<ReadCommandBean> response) {

                if (path.equals("getSubDocPic")) {
                    list.clear();
                    list.addAll(response.body().get推荐());
                } else {//将得到的新数据添加到list中，原list中已有的数据不再重复添加
                    Log.i("MSL", "onResponse: " + response.body());
                    Set set = new LinkedHashSet<>();
                    set.addAll(response.body().get推荐());
                    Iterator<ReadCommandBean.推荐Bean> iterator = set.iterator();

                    while (iterator.hasNext()) {

                        ReadCommandBean.推荐Bean obj = iterator.next();

                        if (!list.contains(obj)) {
                            list.add(obj);
                        }
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReadCommandBean> call, Throwable t) {

                Log.i("MSL", "onFailure: ");
            }
        });

    }


    private void initView(View view) {
        xrvReadfragment = (XRecyclerView) view.findViewById(R.id.xrv_readfragment);
    }

    /**
     * 推荐页面的Adapter适配器
     */
    private class RecommandRcvAdapter extends XRecyclerView.Adapter<RecommandRcvAdapter.RecommandRcvHolder> implements View.OnClickListener {

        private ReadCommandBean.推荐Bean 推荐;
        private OnItemClickListener mItemClickListener;

        private void setOnItemClickListener(OnItemClickListener listener) {
            this.mItemClickListener = listener;
        }

        @Override
        public RecommandRcvHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_read_recommand, parent, false);

            RecommandRcvHolder holder = new RecommandRcvHolder(view);
            view.setOnClickListener(this);
            holder.tvDelete.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecommandRcvHolder holder, final int position) {

            推荐 = list.get(position);
            holder.tvItemReadRecommandTitle.setText(推荐.getTitle());
            holder.tvItemReadRecommandSource.setText(推荐.getSource());

            Glide.with(getActivity()).load(推荐.getImgsrc())
                    .placeholder(R.drawable.no_loading)
                    .skipMemoryCache(false)
                    .into(holder.ivItemReadRecommand);
            holder.itemView.setTag(R.id.relative_container_item_read_recommand,position);
            holder.tvDelete.setTag(R.id.tv_item_read_recommand_delete, position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
                switch (v.getId()) {

                    case R.id.relative_container_item_read_recommand:
                        mItemClickListener.onItemClick((int) v.getTag(v.getId()));
                        break;
                    case R.id.tv_item_read_recommand_delete:
                        mItemClickListener.onDeleteBtnClick((Integer) v.getTag(v.getId()));
                        break;
                }
            }
        }

        class RecommandRcvHolder extends XRecyclerView.ViewHolder {
            public TextView tvItemReadRecommandTitle;
            public ImageView ivItemReadRecommand;
            public TextView tvItemReadRecommandSource;
            public TextView tvDelete;

            public RecommandRcvHolder(View itemView) {
                super(itemView);
                this.tvItemReadRecommandTitle = (TextView) itemView.findViewById(R.id.tv_item_read_recommand_title);
                this.ivItemReadRecommand = (ImageView) itemView.findViewById(R.id.iv_item_read_recommand);
                this.tvItemReadRecommandSource = (TextView) itemView.findViewById(R.id.tv_item_read_recommand_soucre);
                this.tvDelete = (TextView) itemView.findViewById(R.id.tv_item_read_recommand_delete);
            }
        }

    }

    //item点击事件的自定义接口
    private interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteBtnClick(int position);
    }

}
