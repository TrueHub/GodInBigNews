package com.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.news.activity.R;
import com.bumptech.glide.Glide;

/**
 * Created by 371964363 on 2016/11/16.
 */
public class ImageFragment extends Fragment {
    private ImageView imageView;
    private String url;
    public static ImageFragment getInstance(String url) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        url = getArguments().getString("url");
        return inflater.inflate(R.layout.item_imagefragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = (ImageView) view.findViewById(R.id.item_imagefragment_image333);
        Glide.with(getContext()).load(url).placeholder(R.mipmap.ic_launcher).into(imageView);
    }
}
