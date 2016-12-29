package com.read.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.news.activity.R;
import com.read.bean.ReadSubscriberBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2016-11-18.
 */
public class ReadSubscriberAdapter extends BaseAdapter implements View.OnClickListener {

    private List<ReadSubscriberBean.RecommendlistBean> list = new ArrayList<>();
    private Context context;

    public OnItemChildrenClickListener listener;

    public void setOnItemChildrenClickListener(OnItemChildrenClickListener listener) {
        this.listener = listener;
    }

    public ReadSubscriberAdapter(List<ReadSubscriberBean.RecommendlistBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_read_subscriber, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ReadSubscriberBean.RecommendlistBean subscriber = list.get(position);

        holder.mTvAuthorname.setText(subscriber.getTname());
        holder.mTvSubnum.setText(subscriber.getSubnum());
        holder.mTvSubscriberTitle.setText(subscriber.getTitle());
//        holder.mIvAuthorheadimg               //设置作者头像

        holder.mRlAuthorinfo.setOnClickListener(this);
        holder.mRlSubscribeTitle.setOnClickListener(this);
        holder.mBtnFavorite.setOnClickListener(this);

        holder.mRlAuthorinfo.setTag(R.id.rl_authorinfo, subscriber.getEname());
        holder.mRlSubscribeTitle.setTag(R.id.rl_subscribe_title, subscriber.getDocid());
        holder.mBtnFavorite.setTag(R.id.btn_favorite, subscriber.getEname());

        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (null == listener) {
            return;
        }
        switch (v.getId()) {
            case R.id.rl_authorinfo:
                listener.onAuthorInfoClick((String) v.getTag(v.getId()));
                break;
            case R.id.rl_subscribe_title:
                listener.onHotArticleClick((String) v.getTag(v.getId()));
                break;
            case R.id.btn_favorite:
                listener.onSubscriberBtnClick(v,(String) v.getTag(v.getId()));
                break;
        }
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView mIvAuthorheadimg;
        public TextView mTvAuthorname;
        public TextView mTvSubnum;
        public Button mBtnFavorite;
        public View mView;
        public RelativeLayout mRlAuthorinfo;
        public TextView mTvSubscriberTitle;
        public RelativeLayout mRlSubscribeTitle;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mIvAuthorheadimg = (ImageView) rootView.findViewById(R.id.iv_authorheadimg);
            this.mTvAuthorname = (TextView) rootView.findViewById(R.id.tv_authorname);
            this.mTvSubnum = (TextView) rootView.findViewById(R.id.tv_subnum);
            this.mBtnFavorite = (Button) rootView.findViewById(R.id.btn_favorite);
            this.mView = (View) rootView.findViewById(R.id.view);
            this.mRlAuthorinfo = (RelativeLayout) rootView.findViewById(R.id.rl_authorinfo);
            this.mTvSubscriberTitle = (TextView) rootView.findViewById(R.id.tv_subscriber_title);
            this.mRlSubscribeTitle = (RelativeLayout) rootView.findViewById(R.id.rl_subscribe_title);
        }

    }

    public interface OnItemChildrenClickListener {
        /**
         * 点击作者信息的relativeLayout的点击事件
         *

         * @param eName 作者的ID
         */
        void onAuthorInfoClick(String eName);

        /**
         * 点击关注按钮的点击事件
         * @param view 当前控件
         * @param eName 作者的ID
         */
        void onSubscriberBtnClick(View view, String eName);

        /**
         * 点击作者的文章标题RelativeLayout的点击事件
         *
         * @param docId 文章ID
         */
        void onHotArticleClick(String docId);
    }
}
