package com.bignews9527.vidio.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignews9527.vidio.beans.MeiMvBean;
import com.news.activity.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Created by 1945374040 on 2016/11/11.
 */
public class MeiNvXRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<MeiMvBean.VAP4BG6DLBean> list;
    private JCVideoPlayerStandard currentVideo;

    public MeiNvXRecyclerViewAdapter(ArrayList<MeiMvBean.VAP4BG6DLBean> list, LayoutInflater inflater, Context context) {
        this.inflater = inflater;
        this.context = context;
        this.list = list;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new ListHolder(inflater.inflate(R.layout.item_xrecyclerview_video, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ListHolder listHolder = (ListHolder) holder;
        currentVideo = listHolder.vv;
//                Glide.with(context).load(list.get(position - 1).getCover()).into(listHolder.iv);
        listHolder.tv.setText(list.get(position).getTitle());
////                listHolder.iv.setImageURI(Uri.parse(list.get(position-1).getCover()));
//                listHolder.iv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        listHolder.iv.setVisibility(View.GONE);
//                        listHolder.vv.setVisibility(View.VISIBLE);
//                        listHolder.vv.setVideoPath(list.get(position-1).getMp4_url());
////                listHolder.vv.setVideoURI(Uri.parse(list.get(position-1).getMp4_url()));
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
                list.get(position).getMp4_url(),
                JCVideoPlayerStandard.SCREEN_LAYOUT_LIST,
                list.get(position).getTitle());


//                listHolder.vv.startWindowTiny();
        Glide.with(context)
                .load(list.get(position).getCover())
                .asBitmap()
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .into(listHolder.vv.thumbImageView);

    }


    @Override
    public int getItemCount() {
        return list.size();
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
