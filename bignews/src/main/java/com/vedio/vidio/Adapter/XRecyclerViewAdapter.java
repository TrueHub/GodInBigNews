package com.bignews9527.vidio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bignews9527.vidio.beans.VideoBean;
import com.bignews9527.vidio.video.JingPinActivity;
import com.bignews9527.vidio.video.MeiMvActivity;
import com.bignews9527.vidio.video.MengWuActivity;
import com.bignews9527.vidio.video.QiPaActivity;
import com.news.activity.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Created by 1945374040 on 2016/11/11.
 */
public class XRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private int index;
    private ArrayList<VideoBean.VideoSidListBean> videoSidList;
    private ArrayList<VideoBean.VideoListBean> videoList;
    private JCVideoPlayerStandard currentVideo;

    public XRecyclerViewAdapter(ArrayList<VideoBean.VideoSidListBean> videoSidList, ArrayList<VideoBean.VideoListBean> videoList, LayoutInflater inflater, Context context) {
        this.inflater = inflater;
        this.context = context;
        this.videoSidList = videoSidList;
        this.videoList = videoList;

    }

    @Override
    public int getItemViewType(int position) {

        if (videoSidList.size() != 0 && position == 0) {
            return 1;
        } else {
            return 2;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 1:
                holder = new GroupHolder(inflater.inflate(R.layout.item_xrecyclerview_radiogroup, parent, false));
                break;
            case 2:
                holder = new ListHolder(inflater.inflate(R.layout.item_xrecyclerview_video, parent, false));
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (getItemViewType(position)) {
            case 1:
                Log.i("tmd", "onResourceReady: 111");

                GroupHolder groupHolder = (GroupHolder) holder;
//                Log.i("tmd", "onBindViewHolder: " + videoSidList.size() + holder.itemView.getId());
                RadioGroup group = (RadioGroup) groupHolder.itemView;
                for (int i = 0; i < videoSidList.size(); i++) {
                    VideoBean.VideoSidListBean bean = videoSidList.get(i);
                    final RadioButton button = (RadioButton) group.getChildAt(i);
                    button.setId(i);
//                    final RadioButton button = new RadioButton(context);
//                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(group.getWidth() / videoSidList.size(), ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.setMargins(10,10,10,10);

                    //设置好params，id， 以及显示内容
//                    button.setLayoutParams(params);
//                    button.setId(i);
//                    button.setText(bean.getTitle());

                    // 设置不同状态下button的背景色
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                        button.setBackground(context.getResources().getDrawable(R.drawable.video_radiobutton_color));
//                    }

                    // 设置button的显示图片
                    Glide.with(context)
                            .load(bean.getImgsrc())
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    button.setButtonDrawable(new BitmapDrawable());
                                    Drawable drawable = new BitmapDrawable(resource);
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    button.setCompoundDrawables(null, drawable, null, null);
                                    Log.i("tmd", "onResourceReady: 111");
//                                    button.setGravity(Gravity.CENTER_HORIZONTAL);
                                }
                            });

                    //给radiobutton设置点击事件，跳转页面
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Log.i("tmd", "onClick: " + view.getId());

                            switch (view.getId()) {
                                case 0:
                                    context.startActivity(new Intent(context, QiPaActivity.class).putExtra("url","http://c.3g.163.com/nc/video/list/VAP4BFE3U/n/0-10.html"));
                                    break;
                                case 1:
                                    context.startActivity(new Intent(context, MengWuActivity.class).putExtra("url","http://c.3g.163.com/nc/video/list/VAP4BFR16/n/0-10.html"));
                                    break;
                                case 2:
                                    context.startActivity(new Intent(context, MeiMvActivity.class).putExtra("url","http://c.3g.163.com/nc/video/list/VAP4BG6DL/n/0-10.html"));
                                    break;
                                case 3:
                                    context.startActivity(new Intent(context, JingPinActivity.class).putExtra("url","http://c.3g.163.com/nc/video/list/VAP4BGTVD/n/0-10.html"));
                                    break;
                            }
                        }
                    });
//                    group.addView(button);
                    Log.i("tmd", "onBindViewHolder: " + group.getChildCount());
                }
                break;

            case 2:

                if (videoSidList.size() == 0) {

                    index = position;
                } else {
                    index = position - 1;
                }
                final ListHolder listHolder = (ListHolder) holder;
                currentVideo = listHolder.vv;
//                Glide.with(context).load(videoList.get(position - 1).getCover()).into(listHolder.iv);
                listHolder.tv.setText(videoList.get(index).getTitle());
////                listHolder.iv.setImageURI(Uri.parse(videoList.get(position-1).getCover()));
//                listHolder.iv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        listHolder.iv.setVisibility(View.GONE);
//                        listHolder.vv.setVisibility(View.VISIBLE);
//                        listHolder.vv.setVideoPath(videoList.get(position-1).getMp4_url());
////                listHolder.vv.setVideoURI(Uri.parse(videoList.get(position-1).getMp4_url()));
////                listHolder.vv.start();
//                        MediaController mediaCo = new MediaController(context);
//                        listHolder.vv.setMediaController(mediaCo);
//                        mediaCo.setMediaPlayer(listHolder.vv);
//                        listHolder.vv.requestFocus();
//                        listHolder.vv.start();
//                        if (lastvv != null) {
//                            lastvv.stopPlayback();
//                        }
//                        lastvv = listHolder.vv;
//                    }
//                });

                listHolder.vv.setUp(
                        videoList.get(index).getMp4_url(),
                        JCVideoPlayerStandard.SCREEN_LAYOUT_LIST,
                        videoList.get(index).getTitle());


//                listHolder.vv.startWindowTiny();
                Glide.with(context)
                        .load(videoList.get(index).getCover())
                        .asBitmap()
                        .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                        .into(listHolder.vv.thumbImageView);
                break;
        }

    }


    @Override
    public int getItemCount() {
        return videoSidList.size() == 0 ? videoList.size() : videoList.size() + 1;
    }

    class GroupHolder extends RecyclerView.ViewHolder {

        public GroupHolder(View itemView) {
            super(itemView);
        }
    }

    class ListHolder extends RecyclerView.ViewHolder {

        JCVideoPlayerStandard vv;
        TextView tv;
        ImageView iv;

        public ListHolder(View itemView) {
            super(itemView);
//            this.setIsRecyclable(false);
            vv = (JCVideoPlayerStandard) itemView.findViewById(R.id.item_recyclerview_video);
            tv = (TextView) itemView.findViewById(R.id.item_xrecyclerview_tv);
            iv = (ImageView) itemView.findViewById(R.id.item_recyclerview_iv);
        }
    }
}
