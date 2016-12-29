package com.news.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class News_item_Click extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_item__click);

        initView();
        webview.getSettings().setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webview.loadUrl(getIntent().getStringExtra("url"));

    }

    private void initView() {
        webview = (WebView) findViewById(R.id.webview_activity_news_click);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(webview.canGoBack()){
            webview.goBack();
        }else{
            finish();
        }
    }


}
