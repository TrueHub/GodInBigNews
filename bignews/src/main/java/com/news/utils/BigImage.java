package com.news.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.news.activity.BigImageView;
import com.news.activity.ReadContentActivity;
import com.news.activity.Special;
import com.bumptech.glide.Glide;

/**
 * Created by 371964363 on 2016/11/17.
 * 处理新闻顶端大图片的点击事件
 */
public class BigImage {
    private ImageView imageView;
    private Context context;

    public BigImage(Context context){
        this.context = context;
    }
    public ImageView getBigImageSpcial(String imgUrl,final String url) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Special.class);
                intent.putExtra("special", url);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(imgUrl).into(imageView);
        return imageView;
    }
    /**
     * @param imgUrl 图片地址
     * @param url 图集地址
     * @param title 图片主题
     * */
    public ImageView getBigImagePhotoSet(String imgUrl, final String url, final String title) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BigImageView.class);
                intent.putExtra("url",url);
                intent.putExtra("title",title);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(imgUrl).into(imageView);
        return imageView;
    }
    public ImageView getBigImageNormal(String imgUrl, final String url) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadContentActivity.class);
                intent.putExtra("docId", url);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(imgUrl).into(imageView);
        return imageView;
    }
}
