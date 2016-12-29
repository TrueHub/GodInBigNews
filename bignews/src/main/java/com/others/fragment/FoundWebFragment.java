package com.others.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.news.activity.R;


/**
 * Created by Administrator on 2016/11/15.
 */
public class FoundWebFragment extends Fragment{
    private static final String TAG="tmd";
private WebView mFoundFragmentWebView;
    private String CurrentUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_found_web,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        CurrentUrl=getArguments().getString("key");
        Log.i(TAG,"FoundWeb中的值"+getArguments().getString("key"));
        mFoundFragmentWebView.getSettings().setJavaScriptEnabled(true);
        mFoundFragmentWebView.getSettings().setSupportZoom(true);
        mFoundFragmentWebView.getSettings().setUseWideViewPort(true);
        mFoundFragmentWebView.getSettings().setLoadWithOverviewMode(true);
        mFoundFragmentWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(CurrentUrl);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mFoundFragmentWebView.loadUrl(CurrentUrl);
    }

    private void initView(View view) {
        mFoundFragmentWebView= (WebView) view.findViewById(R.id.fragment_found_web);
    }


}
