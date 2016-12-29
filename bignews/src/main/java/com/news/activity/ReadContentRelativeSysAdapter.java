package com.news.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.news.bean.ReadArticleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2016-11-17.
 */
public class ReadContentRelativeSysAdapter extends BaseAdapter {

    private List<ReadArticleBean.ArticleBean.RelativeSysBean> list = new ArrayList<>();
    private Context context;

    public ReadContentRelativeSysAdapter(List<ReadArticleBean.ArticleBean.RelativeSysBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder ;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_readcontent_relative_sys, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvReadcontentCommendTitle.setText(list.get(position).getTitle());
        holder.mTvreadcontentCommendAuthor.setText(list.get(position).getSource());
        holder.mTvreadcontentCommendPtime.setText(list.get(position).getPtime());
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView mTvReadcontentCommendTitle;
        public TextView mTvreadcontentCommendAuthor;
        public TextView mTvreadcontentCommendPtime;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mTvReadcontentCommendTitle = (TextView) rootView.findViewById(R.id.tv_readcontent_commend_title);
            this.mTvreadcontentCommendAuthor = (TextView) rootView.findViewById(R.id.tvreadcontent_commend_author);
            this.mTvreadcontentCommendPtime = (TextView) rootView.findViewById(R.id.tvreadcontent_commend_ptime);
        }

    }
}
