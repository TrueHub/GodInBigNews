package com.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.activity.R;
import com.bumptech.glide.Glide;

/**
 * Created by 371964363 on 2016/11/15.
 */
public class NewsAllBigImage extends Fragment {
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mContent;
    private TextView mNum;

    public static NewsAllBigImage getInstance(String url, String title, String num, String content) {
        NewsAllBigImage image = new NewsAllBigImage();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        bundle.putString("title",title);
        bundle.putString("num",num);
        bundle.putString("content",content);
        image.setArguments(bundle);
        return image;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_activity_bigimageview,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        mImageView = (ImageView) view.findViewById(R.id.item_activity_imageView);
        mTitle = (TextView) view.findViewById(R.id.item_activity_title);
        mNum = (TextView) view.findViewById(R.id.item_activity_num);
        mContent = (TextView) view.findViewById(R.id.item_activity_content);

    }

    private void initData() {
        Bundle bundle = getArguments();
        Glide.with(getContext())
                .load(bundle.get("url"))
                .into(mImageView);
        mTitle.setText(bundle.getString("title"));
        mNum.setText(bundle.getString("num"));
        mContent.setText(bundle.getString("content"));
    }

}
