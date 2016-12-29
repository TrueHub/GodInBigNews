package com.read.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.read.adapter.AuthorInfoAdapter;
import com.read.bean.AuthorInfoBean;
import com.read.utils.HttpConnectAuthorInfo;
import com.read.utils.UrlUtil;
import com.read.view.FullyLinearLayoutManager;
import com.news.activity.R;

import java.util.ArrayList;
import java.util.List;

public class AuthorInfoActivity extends AppCompatActivity {

    private String eName;
    private int index = 20;
    private String url;

    private List<AuthorInfoBean.AuthorInfo> authorInfos = new ArrayList<>();
    private AuthorInfoAdapter adapter;
    private RelativeLayout mAuthorShowHeadinfo;
    private RecyclerView mLvAuthorinfo;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    Log.i("MSL", "handleMessage: ");
                    initAdapter();
                    break;
            }

            return false;
        }
    }) ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_author_info);
        initView();

        eName = getIntent().getStringExtra("pid");
        url = UrlUtil.URL_HEAD + "nc/article/list/" + eName + "/0-20.html";
        Log.i("MSL", "onCreate: " + url);


        initData();

        initAdapter();
    }

    private void initAdapter() {
        mLvAuthorinfo.setLayoutManager(new FullyLinearLayoutManager(this));
        adapter = new AuthorInfoAdapter(authorInfos, this);

        mLvAuthorinfo.setAdapter(adapter);
    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //好气呀!手动解析
                authorInfos = new HttpConnectAuthorInfo(eName, url).httpFunction();

                handler.sendEmptyMessage(0);

            }
        }).start();

    }

    private void initView() {
        mAuthorShowHeadinfo = (RelativeLayout) findViewById(R.id.author_show_headinfo);
        mLvAuthorinfo = (RecyclerView) findViewById(R.id.lv_authorinfo);
    }
}
