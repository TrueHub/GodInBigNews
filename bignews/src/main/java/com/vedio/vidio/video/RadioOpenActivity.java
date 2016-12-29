package com.bignews9527.vidio.video;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.news.activity.R;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class RadioOpenActivity extends AppCompatActivity implements View.OnClickListener {

    private String postId;
    private XRecyclerView radio_xrecyclerview_open;
    private Context context;
    private ArrayList<String> list = new ArrayList<>();
    private AppBarLayout appbar;
    private ImageView radio_iv;
    private Button radio_but_previous;
    private Button radio_but_next;
    private Animation animation;
    private Animator animator;
    private LinearLayout radio_linearlayout;
    private Button radio_but_statebar;
    private CheckBox radio_but_start;
    private boolean flag;
    private ImageView radio_iv_back;
    private ServiceConnection conn;
    private MusicPlayerService.MyBinder binder;
    private Runnable r;
    private Handler handler;
    private MyReAdapter adapter;
    private SeekBar radio_seekbar;
    private String URL = "http://abv.cn/music/光辉岁月.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_radio_open);

        context = this;

        animation = AnimationUtils.loadAnimation(context, R.anim.tip);
        animator = AnimatorInflater.loadAnimator(context, R.animator.iv_rotation);

//        animation.setInterpolator(new LinearInterpolator());


        postId = getIntent().getStringExtra("postid");

        Log.i("tmd", "onCreate: "+ postId);

        initView();

        initService();

        initData();

        initAdapter();

        initLIstener();
    }

    private void initService() {

        startService(new Intent(this, MusicPlayerService.class).putExtra("postid", postId));
        bindService(new Intent(this, MusicPlayerService.class),
                conn = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

                        binder = (MusicPlayerService.MyBinder) iBinder;
                        if (binder.getPlayer().isPlaying()) {
                            animator.setTarget(radio_iv);
                            radio_but_start.setChecked(true);
                            animator.start();
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName componentName) {

                    }
                }, BIND_AUTO_CREATE);
    }


    private void initAdapter() {

        radio_xrecyclerview_open.setLayoutManager(new LinearLayoutManager(context));

        radio_xrecyclerview_open.setLoadingMoreEnabled(false);

        radio_xrecyclerview_open.setAdapter(adapter = new MyReAdapter());
    }

    private void initData() {

        list.clear();

        for (int i = 0; i < 20; i++) {
            list.add("光辉岁月");
        }
    }

    private void initLIstener() {


        //监听appbar，计算偏移量得到xrecyclerview是否可刷新并设置button的visibility

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    radio_xrecyclerview_open.setPullRefreshEnabled(true);
                } else {
                    radio_xrecyclerview_open.setPullRefreshEnabled(false);
                }
                if (verticalOffset <= -300) {
                    radio_but_statebar.setVisibility(View.VISIBLE);

//                    radio_but_statebar.getBackground().setAlpha((-verticalOffset - 300) * 255 / 150);
                    radio_but_statebar.setAlpha(((float) (-verticalOffset - 300)) / 150);
                } else {
                    radio_but_statebar.setVisibility(View.GONE);
                }
            }
        });

        radio_but_start.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {

                if (!binder.getPlayer().isPlaying()&&!flag) {
                    flag = true;
                    animator.setTarget(radio_iv);
                    animator.end();
                    binder.playUrl(URL);
                    binder.getPlayer().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
//                            animator.setTarget(radio_iv);
                            animator.start();
                        }
                    });
                }

                if (b) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        animator.resume();
                        binder.play();
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        animator.pause();
                        binder.pause();
                        flag = true;
                    }
                }

            }

        });

        radio_xrecyclerview_open.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
                radio_xrecyclerview_open.refreshComplete();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    public void onBackPressed() {
//        moveTaskToBack(true);
        super.onBackPressed();
        finish();
    }

    private void initView() {
        radio_xrecyclerview_open = (XRecyclerView) findViewById(R.id.radio_xrecyclerview_open);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        radio_iv = (ImageView) findViewById(R.id.radio_iv);
        Glide.with(context)
                .load(R.mipmap.background)
                .transform(new CircleTransform(context))
                .into(radio_iv);
        radio_but_previous = (Button) findViewById(R.id.radio_but_previous);
        radio_but_previous.setOnClickListener(this);
//        radio_but_start = (Button) findViewById(R.id.radio_but_start);
//        radio_but_start.setOnClickListener(this);
        radio_but_next = (Button) findViewById(R.id.radio_but_next);
        radio_but_next.setOnClickListener(this);
        radio_linearlayout = (LinearLayout) findViewById(R.id.radio_linearlayout);
        radio_linearlayout.setOnClickListener(this);
        radio_but_statebar = (Button) findViewById(R.id.radio_but_statebar);
        radio_but_statebar.setOnClickListener(this);
        radio_but_start = (CheckBox) findViewById(R.id.radio_but_start);
        radio_but_start.setOnClickListener(this);
        radio_iv_back = (ImageView) findViewById(R.id.radio_iv_back);
        radio_iv_back.setOnClickListener(this);
        radio_seekbar = (SeekBar) findViewById(R.id.radio_seekbar);
        radio_seekbar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_but_previous:


                break;
            case R.id.radio_but_next:


                break;
            case R.id.radio_but_statebar:
                break;

            case R.id.radio_iv_back:

                finish();
                break;
        }
    }

    public class MyReAdapter extends RecyclerView.Adapter<MyReAdapter.MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(new TextView(context));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.tv.setText(list.get(position));
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    radio_but_start.setChecked(true);
                    animator.setTarget(radio_iv);
                    animator.end();
                    binder.playUrl(URL);
                    binder.getPlayer().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                            animator.start();

                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyHolder(View itemView) {
                super(itemView);

                tv = (TextView) itemView;
                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setTextColor(Color.rgb(0, 0, 0));
                tv.setTextSize(20);
                tv.setPadding(0, 20, 0, 20);
            }
        }
    }
}
