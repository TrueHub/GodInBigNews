package com.read.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.news.activity.R;
import com.read.adapter.ReadContentRelativeSysAdapter;
import com.read.bean.ReadArticleBean;
import com.read.utils.HttpConnectAndPullReadConent;
import com.read.utils.UrlUtil;
import com.read.utils.Utils;
import com.read.view.HeightSumListView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReadContentActivity extends AppCompatActivity implements View.OnClickListener {

    private String docId;
    private String url;
    private String body;
    private ReadArticleBean readArticleBean;
    private List<ReadArticleBean.ArticleBean.ImgBean> imgs = new ArrayList<>();
    private List<ReadArticleBean.ArticleBean.RelativeSysBean> relativeSysBeanList = new ArrayList<>();

    private TextView mTextView, mTvReadcontentTitle, mTvReadcontentAuthor, mTvReadcontentUpLoadTime, mTvReadcontentSupportSum, tvReplyCount;
    private WebView mWebviewReadcontent;
    private ImageView mIvShareToYixin, mIvShareToWechatCircle, mIvShareToSina, mIvShareToMore;
    private EditText mEditText;
    private HeightSumListView mLvReadcontentRelativeSys;
    private LinearLayout mLlReadcontentRelativeSys;
    private ActionBar actionBar;

    private ReadContentRelativeSysAdapter adapter;
    private Context context;
    private int replyCount = 0;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    showWebcontent();
                    break;
            }
            return false;
        }
    });
    private RadioButton mRbtnReadcontentSupport;
    private RadioButton mRbtnReadcontentTread;
    private List<ReadArticleBean.ArticleBean.TopiclistNewsBean> topList = new ArrayList<>();
    private AlertDialog.Builder abb;
    private CharSequence[] unlikeresons;
    private RelativeLayout mRlRupportOrtread;
    private int textsize;
    private SharedPreferences pref;
    private int textDefaultsizeType = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.show();

        setContentView(R.layout.activity_read_content);
        context = getApplicationContext();
        pref = getSharedPreferences("readcontent_textsize", MODE_PRIVATE);

        abb = new AlertDialog.Builder(this);

        initView();

        overridePendingTransition(R.anim.activity_open_right, 0);

        docId = getIntent().getStringExtra("docId");

        if (getIntent().getCharSequenceArrayExtra("unlikereson") != null) {
            unlikeresons = getIntent().getCharSequenceArrayExtra("unlikereson");
        } else {
            mRlRupportOrtread.setVisibility(View.GONE);
        }
        url = UrlUtil.URL_HEAD + "nc/article/" + docId + "/full.html";
        Log.i("MSL", "onCreate: " + url);


        initData();

        initAdapter();

    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e("MSL", "onPrepareOptionsPanel: onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override               //
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_readcontent, menu);
        View view = menu.findItem(R.id.menu_item_replaycount).getActionView();
        tvReplyCount = (TextView) view.findViewById(R.id.item_tvmenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_replaycount:
                Log.i("MSL", "onOptionsItemSelected: 进入跟帖评论详情页面");

                break;
            case R.id.menu_share:
                //第三方分享
                break;
            case R.id.menu_collection:
                //添加收藏
                break;
            case R.id.menu_screencapture:
                //调用系统截屏功能
                break;
            case R.id.menu_textstyle:
                //更改页面样式,弹出popupupwindow 单选样式后确定刷新页面显示

                final String[] styles = new String[]{"特大号字", "大号字", "中号字", "小号字"};
                new AlertDialog.Builder(this).setTitle("正文样式")
                        .setSingleChoiceItems(styles, textDefaultsizeType, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case 0:
                                        textsize = 17;
                                        break;
                                    case 1:
                                        textsize = 14;
                                        break;
                                    case 2:
                                        textsize = 11;
                                        break;
                                    case 3:
                                        textsize = 8;
                                        break;
                                }
                                textDefaultsizeType = which;
                            }
                        }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.i("MSL", "要存储了: "+textDefaultsizeType);
                        pref.edit().putInt("defaultType", textDefaultsizeType).commit();
                        showWebcontent();
                    }
                }).show();

                break;
            case R.id.menu_daynight:
                //更改白夜主题
                break;

        }

        return super.

                onOptionsItemSelected(item);

    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                readArticleBean = new HttpConnectAndPullReadConent(docId, url).httpFunction();
                Message msg = Message.obtain();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void initAdapter() {
        Utils.setListViewHeightBasedOnChildren(mLvReadcontentRelativeSys);
        adapter = new ReadContentRelativeSysAdapter(relativeSysBeanList, context);
        mLvReadcontentRelativeSys.setAdapter(adapter);

        //相似文章的list里面的item点击事件，点击跳入具体文章页面，也就是本activity再启动一次
        mLvReadcontentRelativeSys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String docId = relativeSysBeanList.get(position).getDocID();
                String docId = (String) view.getTag(R.id.tv_readcontent_author);
                Log.i("MSL", "onItemClick: " + docId);
                Intent intent = new Intent(ReadContentActivity.this, ReadContentActivity.class);
                intent.putExtra("docId", docId);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        mWebviewReadcontent = (WebView) findViewById(R.id.webview_readcontent);
        mTvReadcontentTitle = (TextView) findViewById(R.id.tv_readcontent_title);
        mTvReadcontentAuthor = (TextView) findViewById(R.id.tv_readcontent_author);
        mTvReadcontentUpLoadTime = (TextView) findViewById(R.id.tv_readcontent_upLoadTime);
        mTvReadcontentSupportSum = (TextView) findViewById(R.id.tv_readcontent_supportSum);
        mIvShareToYixin = (ImageView) findViewById(R.id.iv_shareToYixin);
        mIvShareToWechatCircle = (ImageView) findViewById(R.id.iv_shareToWechatCircle);
        mIvShareToSina = (ImageView) findViewById(R.id.iv_shareToSina);
        mIvShareToMore = (ImageView) findViewById(R.id.iv_shareToMore);
        mEditText = (EditText) findViewById(R.id.editText);
        mTextView = (TextView) findViewById(R.id.textView);
        mLvReadcontentRelativeSys = (HeightSumListView) findViewById(R.id.lv_readcontent_relativeSys);
        mLlReadcontentRelativeSys = (LinearLayout) findViewById(R.id.ll_readcontent_relativeSys);
        mTvReadcontentTitle.setOnClickListener(this);
        mTvReadcontentAuthor.setOnClickListener(this);
        mTvReadcontentUpLoadTime.setOnClickListener(this);
        mTvReadcontentSupportSum.setOnClickListener(this);
        mIvShareToYixin.setOnClickListener(this);
        mIvShareToWechatCircle.setOnClickListener(this);
        mIvShareToSina.setOnClickListener(this);
        mIvShareToMore.setOnClickListener(this);
        mEditText.setOnClickListener(this);
        mTextView.setOnClickListener(this);
        mRbtnReadcontentSupport = (RadioButton) findViewById(R.id.rbtn_readcontent_support);
        mRbtnReadcontentTread = (RadioButton) findViewById(R.id.rbtn_readcontent_tread);
        mRbtnReadcontentSupport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mRbtnReadcontentTread.setFocusable(false);
                mRbtnReadcontentTread.setClickable(false);
                mRbtnReadcontentSupport.setFocusable(false);
                mRbtnReadcontentSupport.setClickable(false);
            }
        });
        mRbtnReadcontentTread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRbtnReadcontentTread.setChecked(false);
                boolean[] b = new boolean[unlikeresons.length];

                //弹出popupupwindow ，复选 确定删除此条。

                abb.setTitle("选择不感兴趣的关键词(多选)")
                        .setMultiChoiceItems(unlikeresons, b, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mRbtnReadcontentTread.setChecked(true);
                                mRbtnReadcontentTread.setFocusable(false);
                                mRbtnReadcontentTread.setClickable(false);
                                mRbtnReadcontentSupport.setFocusable(false);
                                mRbtnReadcontentSupport.setClickable(false);

                                //暂时不知如何操作推荐方面的屏蔽

                            }
                        }).show();
            }
        });
        mRlRupportOrtread = (RelativeLayout) findViewById(R.id.rl_rupportOrtread);
        mRlRupportOrtread.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_shareToYixin:
                Log.i("MSL", "onClick: 易信");

                break;
            case R.id.iv_shareToWechatCircle:
                Log.i("MSL", "onClick: 微信");

                break;
            case R.id.iv_shareToSina:
                Log.i("MSL", "onClick: 新浪");

                break;
            case R.id.iv_shareToMore:
                Log.i("MSL", "onClick: 更多");

                break;
            case R.id.textView:
                Log.i("MSL", "onClick: 提交评论");

                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mEditText.isFocused()) {
            mWebviewReadcontent.requestFocus();
        } else {
            ReadContentActivity.this.finish();
        }

    }

    private void encodeHtmlbody() {

        if (pref.getInt("defaultType", -1) != -1) {
            textDefaultsizeType = pref.getInt("defaultType", -1);
        }
        switch (textDefaultsizeType) {
            case 0:
                textsize = 17;
                break;
            case 1:
                textsize = 14;
                break;
            case 2:
                textsize = 11;
                break;
            case 3:
                textsize = 8;
                break;
        }

        try {
            replyCount = readArticleBean.getArticleBean().getReplyCount();
        } catch (NullPointerException e) {
            Log.e("MSL", "run: NullPoint", e);
        }
        try {
            body = readArticleBean.getArticleBean().getBody();
            imgs = readArticleBean.getArticleBean().getImg();

            for (ReadArticleBean.ArticleBean.ImgBean imgBean : imgs
                    ) {
                String str = imgBean.getRef();
                String imgurl = imgBean.getSrc();

                String alt = imgBean.getAlt();
                if (!alt.equals("")) {
                    body = body.replaceAll(str, "&lt;img src='" + imgurl + "' alt='" + alt + "'&gt;");//将图片网址转为HTML

                } else {
                    body = body.replaceAll(str, "&lt;img src='" + imgurl + "'&gt;");
                }
            }

            body = body.replaceAll("&", "")
                    .replaceAll("quot;", "\"")
                    .replaceAll("lt;", "<")
                    .replaceAll("gt;", ">")
                    .replaceAll("hellip", "…");
            StringBuffer sbBody = new StringBuffer(body);
            sbBody.insert(0, "<style>img{max-width:100%;height:auto;}p{font: " + textsize + "pt/" + (textsize + 3) + "pt 'arial'; color: black}</style>");//设置图片缩放模式,文字大小的css样式
            body = sbBody.toString();
            topList = readArticleBean.getArticleBean().getTopiclist_news();
            Log.i("MSL", "onResponse: " + body);
        } catch (NullPointerException e) {
            Log.e("MSL", "run: ", e);
        }
    }

    private void showWebcontent() {
        encodeHtmlbody();
        ReadArticleBean.ArticleBean articleBean = readArticleBean.getArticleBean();
        tvReplyCount.setText(replyCount + "跟帖");
        mTvReadcontentTitle.setText(articleBean.getTitle());
        mTvReadcontentAuthor.setText(articleBean.getSource());
        mTvReadcontentUpLoadTime.setText(articleBean.getPtime());
        mTvReadcontentSupportSum.setText(articleBean.getThreadVote()+"");

        if (articleBean.getRelative_sys() != null) {
            mLlReadcontentRelativeSys.setVisibility(View.VISIBLE);
            relativeSysBeanList.addAll(articleBean.getRelative_sys());
            Log.i("MSL", "handleMessage: " + relativeSysBeanList.get(0).toString());

            adapter.notifyDataSetChanged();
            Utils.setListViewHeightBasedOnChildren(mLvReadcontentRelativeSys);
        } else {
            mLlReadcontentRelativeSys.setVisibility(View.GONE);
        }

        Log.i("MSL", "showWebcontent: " + body);
        mWebviewReadcontent.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
        WebSettings wvSettings = mWebviewReadcontent.getSettings();
        wvSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        Log.i("MSL", "handleMessage: 0 do");
    }
}
