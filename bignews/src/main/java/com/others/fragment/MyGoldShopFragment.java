package com.others.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.news.activity.R;

/**
 * Created by Administrator on 2016/11/18.
 */
public class MyGoldShopFragment extends Fragment {


    private WebView mMyGoldshopWebview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goldshop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mMyGoldshopWebview.getSettings().setJavaScriptEnabled(true);
        mMyGoldshopWebview.getSettings().setSupportZoom(true);
        mMyGoldshopWebview.getSettings().setUseWideViewPort(true);
        mMyGoldshopWebview.getSettings().setLoadWithOverviewMode(true);
        mMyGoldshopWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mMyGoldshopWebview.loadUrl("http://3g.163.com/links/6687");
    }

    private void initView(View view) {
        mMyGoldshopWebview = (WebView) view.findViewById(R.id.my_goldshop_webview);

    }
}
