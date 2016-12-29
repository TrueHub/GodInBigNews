package com.read.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.read.bean.AuthorInfoBean;
import com.news.activity.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2016-11-22.
 */
public class AuthorInfoAdapter extends RecyclerView.Adapter<AuthorInfoAdapter.AuthorViewHolder> {

    private List<AuthorInfoBean.AuthorInfo> list = new ArrayList<>();
    private Context context;
    private AuthorInfoBean.AuthorInfo authorInfo;

    public AuthorInfoAdapter(List<AuthorInfoBean.AuthorInfo> list, Context context) {
        this.list.addAll(list);
        this.context = context;
        Log.i("MSL", "AuthorInfoAdapter: ppppp");
    }

    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_authorinfo, parent, false);

        AuthorViewHolder holder = new AuthorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AuthorViewHolder holder, int position) {

        authorInfo = list.get(position);
        holder.mTvAuthorinfoArticletitle.setText(authorInfo.getTitle());
        holder.mTvAuthorarticlePtime.setText(authorInfo.getPtime());
        Glide.with(context).load(authorInfo.getImgsrc())
                .placeholder(R.drawable.no_loading)
                .skipMemoryCache(false)
                .into(holder.mIvArticleimg);
        Log.i("MSL", "onBindViewHolder: " + authorInfo.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class AuthorViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public ImageView mIvArticleimg;
        public TextView mTvAuthorinfoArticletitle;
        public TextView mTvAuthorarticlePtime;
        public RelativeLayout mItemAuthorarticle;

        public AuthorViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.mIvArticleimg = (ImageView) rootView.findViewById(R.id.iv_articleimg);
            this.mTvAuthorinfoArticletitle = (TextView) rootView.findViewById(R.id.tv_authorinfo_articletitle);
            this.mTvAuthorarticlePtime = (TextView) rootView.findViewById(R.id.tv_authorarticle_ptime);
            this.mItemAuthorarticle = (RelativeLayout) rootView.findViewById(R.id.item_authorarticle);
        }

    }
}
