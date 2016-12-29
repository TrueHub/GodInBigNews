package com.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.news.activity.BigImageView;
import com.news.activity.R;
import com.news.activity.ReadContentActivity;
import com.news.activity.Special;
import com.news.bean.AllNewsBean;
import com.news.utils.BigImage;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 371964363 on 2016/11/14.
 */
public class NewsAllPager extends Fragment {
    private String currentType;
    private int current;
    private AllNewsBean allNewsBean;
    private XRecyclerView xRecyclerView;
    private String url;
    private RequestQueue queue;
    private MyRecyclerAdapter recyclerAdapter;
    private ImageView imageView;
    private ScrollPagerAdapter02 scrollAdapter;
    private Animation animation;
    private HashMap<Integer,AllNewsBean.ABean> maps = new HashMap();
    private int refrshCount;//纪录刷新次数

    private int bigImagePos;
    public static NewsAllPager getInstance(String url,String type) {
        NewsAllPager pager = new NewsAllPager();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        bundle.putString("type",type);
        pager.setArguments(bundle);
        return pager;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //初始化全局变量
        url = getArguments().getString("url");
        current = Integer.parseInt(getArguments().getString("url").split("/")[7].split("\\.")[0].split("-")[1]);
        View view = inflater.inflate(R.layout.fragment_news_viewpager, container, false);
        queue = Volley.newRequestQueue(getContext());
        imageView = (ImageView) view.findViewById(R.id.fragment_loadmore_img);
        animation = AnimationUtils.loadAnimation(getContext(),R.anim.fragment_loadmore);
        imageView.startAnimation(animation);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentType = getArguments().getString("type");
        initView(view);
        initData("加载",url.split("/")[6]);

        initAdapter();

        initListener();
    }

    private void initListener() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setPullRefreshEnabled(true);

        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        //上拉加载下拉刷新
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if(allNewsBean == null){
                    xRecyclerView.refreshComplete();
                    return;
                }
                refrshCount = 0;

                initData("下拉",url.split("/")[6]);
                //如果新闻大于200条尾部全部删掉
                if(maps.size()>=200){
                    for (int i = 200; i < allNewsBean.getABean().size(); i++) {
                        maps.remove(i);
                    }
                }
                recyclerAdapter.notifyDataSetChanged();
                scrollAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();

            }

            @Override
            public void onLoadMore() {
                String url = getRefrshUrl();
                initData("上拉",url.split("/")[6]);
                refrshCount++;
            }
        });
    }

    private String getRefrshUrl(){
        return "http://c.3g.163.com/nc/article/list/"+url.split("/")[6]+"/"+(current+(refrshCount*20))+"-20.html";
    }

    private void initAdapter() {
        recyclerAdapter = new MyRecyclerAdapter();
        xRecyclerView.setAdapter(recyclerAdapter);
    }
    //手动解析json数据
    private AllNewsBean initData(final String reType, final String fled) {
        if(reType.equals("加载")){
            url = getArguments().getString("url");
        }else if(reType.equals("下拉")){
        }else if(reType.equals("上拉")){
            url = getRefrshUrl();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    if (connection.getResponseCode() == 200) {

                        InputStream is = connection.getInputStream();
                        StringBuffer sb = new StringBuffer();
                        BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        String line;
                        while ((line = buffer.readLine()) != null) {
                            sb.append(line);
                        }
                        String json = sb.toString();
                        AllNewsBean allNewsBean = new AllNewsBean();
                        JSONObject allNewsObject = new JSONObject(json);
                        JSONArray aBeanArray = allNewsObject.getJSONArray(fled);
                        ArrayList<AllNewsBean.ABean> aBeanList = new ArrayList<>();
                        for (int i = 0; i < aBeanArray.length(); i++) {
                            JSONObject aBeanObject = aBeanArray.getJSONObject(i);
                            AllNewsBean.ABean aBean = allNewsBean.new ABean();
                            try {
                                if(i ==  0){
                                    aBean.setDocid(aBeanObject.optString("docid"));
                                    aBean.setTitle(aBeanObject.optString("title"));
                                    aBean.setPhotosetID(aBeanObject.optString("photosetID"));
                                    aBean.setSkipID(aBeanObject.optString("skipID"));
                                    aBean.setSkipType(aBeanObject.optString("skipType"));
                                    aBean.setPtime(aBeanObject.optString("ptime"));
                                    aBean.setImgsrc(aBeanObject.optString("imgsrc"));
                                    AllNewsBean.ABean.AdsBean adsBean = aBean.new AdsBean();
                                    ArrayList<AllNewsBean.ABean.AdsBean> adsBeanList = new ArrayList<>();
                                    JSONArray adsArray = aBeanObject.optJSONArray("ads");
                                    //第二层
                                    maps.put(0,aBean);
                                    if(adsArray == null){
                                        continue;
                                    }
                                    for (int j = 0; j < adsArray.length(); j++) {
                                        JSONObject adsObject = adsArray.getJSONObject(j);
                                        AllNewsBean.ABean.AdsBean ads = aBean.new AdsBean();
                                        ads.setTitle(adsObject.getString("title"));
                                        ads.setImgsrc(adsObject.getString("imgsrc"));
                                        ads.setSubtitle(adsObject.getString("subtitle"));
                                        ads.setTag(adsObject.getString("tag"));
                                        ads.setUrl(adsObject.getString("url"));
                                        adsBeanList.add(ads);
                                    }
                                    //图集
                                    ArrayList<AllNewsBean.ABean.ImgextraBean> imgextraList = new ArrayList<>();
                                    JSONArray imgExtraArray = aBeanObject.optJSONArray("imgextra");
                                    if(imgExtraArray != null){
                                        for (int j = 0; j < imgExtraArray.length(); j++) {
                                            AllNewsBean.ABean.ImgextraBean imgextra = aBean.new ImgextraBean();
                                            JSONObject imgObject =imgExtraArray.getJSONObject(j);
                                            imgextra.setImgsrc(imgObject.getString("imgsrc"));
                                            imgextraList.add(imgextra);
                                        }
                                        aBean.setImgextra(imgextraList);
                                    }
                                    maps.put(0,aBean);
                                    maps.put(-999,aBean);
                                    aBean.setAds(adsBeanList);
                                    aBeanList.add(aBean);
                                }else {//普通新闻
                                    aBean.setPostid(aBeanObject.optString("postid"));
                                    aBean.setReplyCount(aBeanObject.optInt("replyCount"));
                                    aBean.setSkipType(aBeanObject.optString("skipType"));
                                    aBean.setSkipID(aBeanObject.optString("skipID"));
                                    aBean.setSpecialID(aBeanObject.optString("specialID"));
                                    aBean.setDocid(aBeanObject.optString("docid"));
                                    aBean.setTitle(aBeanObject.optString("title"));
                                    aBean.setImgsrc(aBeanObject.optString("imgsrc"));
                                    aBean.setPtime(aBeanObject.optString("ptime"));
                                    aBean.setPhotosetID(aBeanObject.optString("photosetID"));
                                    //图集
                                    ArrayList<AllNewsBean.ABean.ImgextraBean> imgextraList = new ArrayList<>();
                                    JSONArray imgExtraArray = aBeanObject.optJSONArray("imgextra");
                                    if(imgExtraArray != null){
                                        for (int j = 0; j < imgExtraArray.length(); j++) {
                                            AllNewsBean.ABean.ImgextraBean imgextra = aBean.new ImgextraBean();
                                            JSONObject imgObject =imgExtraArray.getJSONObject(j);
                                            imgextra.setImgsrc(imgObject.getString("imgsrc"));
                                            imgextraList.add(imgextra);
                                        }
                                    }
                                    aBean.setImgextra(imgextraList);
                                    aBeanList.add(aBean);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        allNewsBean.setABean(aBeanList);
                        if(reType.equals("加载")){
                            NewsAllPager.this.allNewsBean = allNewsBean;
                            handler.sendEmptyMessage(3);//清除加载动画//gong掉imageView
                        }else if(reType.equals("下拉")){
                            NewsAllPager.this.allNewsBean = allNewsBean;
                        }else if(reType.equals("上拉")){

                        }
                        initMaps(reType,allNewsBean);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return null;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    recyclerAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    recyclerAdapter.notifyDataSetChanged();
                    xRecyclerView.loadMoreComplete();
                    break;
                case 3:
                    imageView.clearAnimation();
                    imageView.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private void initMaps(String reType,AllNewsBean response) {
        if(reType.equals("加载")){
            for (int i = 1; i < response.getABean().size(); i++) {
                maps.put(i,response.getABean().get(i));
            }
            handler.sendEmptyMessage(1);
        }else if(reType.equals("下拉")){
            for (int i = 1; i < response.getABean().size(); i++) {
                maps.put(i,response.getABean().get(i));
            }
            handler.sendEmptyMessage(1);
        }else if(reType.equals("上拉")){
            int pos = maps.size()-1;
            for (int i = 1; i < response.getABean().size(); i++) {
                maps.put(pos,response.getABean().get(i));
                pos++;
            }

            handler.sendEmptyMessage(2);
        }
    }

    private void initView(View view) {
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.fragment_news_viewpager);
    }

    public class MyRecyclerAdapter extends XRecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public int getItemViewType(int position) {
            if(position == 0){
                return 2;
            }else if(maps.get(position).getSkipType() == null){
                return 0;
            }else if(maps.get(position).getSkipType().equals("photoset")){
                if(maps.get(position).getImgextra().size()!= 0){
                    return 1;
                }else {
                    return 10;
                }
            }else if(maps.get(position).getSkipType().equals("special")){
                return 3;
            }else {
                return 0;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder = null;
            switch (viewType){
                case 0:
                    //单张图片普通新闻
                    holder = new MyOneHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_fragment_news_itme1,parent,false));
                    break;
                case 1:
                    //三张图片普通新闻
                    holder = new MyTwoHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_fragment_news_itme2,parent,false));
                    break;
                case 10:
                    holder = new MyTwo2Holder(LayoutInflater.from(getActivity()).inflate(R.layout.item_fragement_news_item10,parent,false));
                    break;
                case 2:
                    //顶端大图片点击viewpager浏览新闻
                    holder = new MyThreHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_fragment_news_itme3top,parent,false));
                    break;
                case 3:
                    //专题新闻
                    holder = new MyFourHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_news_item4,parent,false));
                    break;
            }

            return holder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

            switch (getItemViewType(position)){
                case 0://单张图片普通新闻
                    MyOneHolder oneHolder = (MyOneHolder) holder;
                    Glide.with(getActivity())
                            .load(maps.get(position).getImgsrc())
                            .into(oneHolder.imageView);
                    oneHolder.title.setText(maps.get(position).getTitle());
                    oneHolder.author.setText(maps.get(position).getSource());
                    oneHolder.time.setText(maps.get(position).getReplyCount()+"人跟帖");
                    break;
                case 1://三张图片普通新闻
                    MyTwoHolder twoHolder = (MyTwoHolder) holder;
                    twoHolder.title.setText(maps.get(position).getTitle());

                    if(maps.get(position).getReplyCount()!=0){
                       twoHolder.time.setText(maps.get(position).getReplyCount()+"人跟帖");
                    }else {
                        twoHolder.time.setText("");
                    }
                    Glide.with(getContext()).load(maps.get(position).getImgsrc()).into(twoHolder.imageView1);
                    Glide.with(getContext()).load(maps.get(position).getImgextra().get(0).getImgsrc()).into(twoHolder.imageView2);
                    Glide.with(getContext()).load(maps.get(position).getImgextra().get(1).getImgsrc()).into(twoHolder.imageView3);
                    break;
                case 10://三张图片合成一张图片普通新闻
                    MyTwo2Holder two2Holder = (MyTwo2Holder) holder;
                    two2Holder.title.setText(maps.get(position).getTitle());
                    Glide.with(getContext()).load(maps.get(position).getImgsrc()).into(two2Holder.imageView1);
                    if(maps.get(position).getReplyCount()!=0){
                        two2Holder.time.setText(maps.get(position).getReplyCount()+"人跟帖");
                    }else {
                        two2Holder.time.setText("");
                    }
                    break;
                case 2://顶端大图片点击viewpager浏览新闻
                    final MyThreHolder threHolder = (MyThreHolder) holder;
                    scrollAdapter = new ScrollPagerAdapter02();
                    threHolder.viewPager.setAdapter(scrollAdapter);
                    threHolder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            bigImagePos = position;
                            threHolder.radioGroup.check(position);
                            threHolder.textView.setText(scrollAdapter.title[position]);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    if(currentType.startsWith("1")){
                        threHolder.radioGroup.removeAllViews();
                        for (int i = 0; i < scrollAdapter.views.size(); i++) {

                            RadioButton button = new RadioButton(getContext());
                            button.setButtonDrawable(R.drawable.radio_checked_select);
                            button.setId(i);
                            button.setPadding(3,0,3,0);
                            button.setClickable(false);
                            button.setChecked(false);
                            threHolder.radioGroup.addView(button);
                            threHolder.radioGroup.check(0);
                        }
                    }
                    threHolder.viewPager.setCurrentItem(bigImagePos);
                    threHolder.radioGroup.check(bigImagePos);
                    threHolder.textView.setText(scrollAdapter.title[bigImagePos]);
                    break;
                case 3://专题新闻
                    MyFourHolder fourHolder = (MyFourHolder) holder;

                    Glide.with(getActivity())
                            .load(maps.get(position).getImgsrc())
                            .into(fourHolder.imageView);
                    fourHolder.title.setText(maps.get(position).getTitle());
                    fourHolder.time.setText(maps.get(position).getPtime());
                    break;
            }
        }

        @Override
        public int getItemCount() {
//            return allNewsBean == null ? 0 : allNewsBean.getABean()().size();
            return  maps.size()-1;
        }
        //单张图片普通新闻
        class MyOneHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView title, author, time;
            public MyOneHolder(View itemView) {
                super(itemView);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ReadContentActivity.class);
                        intent.putExtra("docId", maps.get(getPosition()-1).getDocid());
                        startActivity(intent);
                    }
                });
                imageView = (ImageView) itemView.findViewById(R.id.itme_fragment_new_item4_img);
                title = (TextView) itemView.findViewById(R.id.itme_fragment_new_item4_title);
                author = (TextView) itemView.findViewById(R.id.itme_fragment_new_item4_time);
                time = (TextView) itemView.findViewById(R.id.itme_fragment_new_item4_zhuanti);
            }
        }
        //三张图片普通新闻
        public class MyTwoHolder extends RecyclerView.ViewHolder{
            public ImageView imageView1, imageView2, imageView3;
            TextView title, time;
            public MyTwoHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(maps.get(getPosition()-1).getSkipType().equals("special")){
                            special();
                        }else if(maps.get(getPosition()-1).getSkipType().equals("photoset")){
                            photoSet();
                        }else  if(maps.get(getPosition()-1).getSkipType() == null){
                            doc();
                        }
                    }

                    private void special(){
                        Intent intent = new Intent(getContext(), Special.class);
                        intent.putExtra("special", maps.get(getPosition()-1).getSpecialID());
                        startActivity(intent);
                    }
                    private void doc(){
                        Intent intent = new Intent(getContext(), ReadContentActivity.class);
                        intent.putExtra("docId", maps.get(getPosition()-1).getDocid());
                        startActivity(intent);
                    }
                    private void photoSet(){
                        Intent intent = new Intent(getContext(), BigImageView.class);
                        intent.putExtra("url",maps.get(getPosition()-1).getSkipID());
                        intent.putExtra("title",maps.get(getPosition()-1).getTitle());
                        intent.putExtra("type","头条");
                        startActivity(intent);
                    }
                });
                imageView1 = (ImageView) itemView.findViewById(R.id.itme_fragment_news_item2_img1);
                imageView2 = (ImageView) itemView.findViewById(R.id.itme_fragment_news_item2_img2);
                imageView3 = (ImageView) itemView.findViewById(R.id.itme_fragment_news_item2_img3);
                title = (TextView) itemView.findViewById(R.id.itme_fragment_news_item2_title);
                time = (TextView) itemView.findViewById(R.id.itme_fragment_news_item2_time);
            }


        }
        //三张图片合成一张图片普通新闻
        public class MyTwo2Holder extends RecyclerView.ViewHolder{
            public ImageView imageView1;
            TextView title, time;
            public MyTwo2Holder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(maps.get(getPosition()-1).getSkipType().equals("special")){
                            special();
                        }else if(maps.get(getPosition()-1).getSkipType().equals("photoset")){
                            photoSet();
                        }else  if(maps.get(getPosition()-1).getSkipType() == null){
                            doc();
                        }
                    }

                    private void special(){
                        Intent intent = new Intent(getContext(), Special.class);
                        intent.putExtra("special", maps.get(getPosition()-1).getSpecialID());
                        startActivity(intent);
                    }
                    private void doc(){
                        Intent intent = new Intent(getContext(), ReadContentActivity.class);
                        intent.putExtra("docId", maps.get(getPosition()-1).getDocid());
                        startActivity(intent);
                    }
                    private void photoSet(){
                        Intent intent = new Intent(getContext(), BigImageView.class);
                        intent.putExtra("url",maps.get(getPosition()-1).getSkipID());
                        intent.putExtra("title",maps.get(getPosition()-1).getTitle());
                        intent.putExtra("type","头条");
                        startActivity(intent);
                    }
                });
                imageView1 = (ImageView) itemView.findViewById(R.id.item_fragment_news_item10_image);
                title = (TextView) itemView.findViewById(R.id.itme_fragment_news_item10_title);
                time = (TextView) itemView.findViewById(R.id.itme_fragment_news_item10_time);
            }


        }
        //顶端大图片点击viewpager浏览新闻
        public class MyThreHolder extends RecyclerView.ViewHolder{
            public ViewPager viewPager;
            public TextView textView;
            public RadioGroup radioGroup;
            public MyThreHolder(View itemView) {
                super(itemView);
                viewPager = (ViewPager) itemView.findViewById(R.id.item_fragment_itme3_viewpagerffffff);
                textView = (TextView) itemView.findViewById(R.id.item_fragment_itme3_title);
                radioGroup = (RadioGroup) itemView.findViewById(R.id.item_fragment_itme3_radiogrounp);
            }
        }
        //专题新闻
        class MyFourHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView title, time, skipType;
            public MyFourHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), Special.class);
                        intent.putExtra("special", maps.get(getPosition()-1).getSpecialID());
                        startActivity(intent);
                    }
                });
                imageView = (ImageView) itemView.findViewById(R.id.itme_fragment_new_item4_img);
                title = (TextView) itemView.findViewById(R.id.itme_fragment_new_item4_title);
                time = (TextView) itemView.findViewById(R.id.itme_fragment_new_item4_time);
                skipType = (TextView) itemView.findViewById(R.id.itme_fragment_new_item4_zhuanti);
            }
        }
    }

    private class ScrollPagerAdapter02 extends PagerAdapter {
        public String[] title;
        public ArrayList<View> views = new ArrayList<>();
        public ScrollPagerAdapter02(){
            initScrollData();
        }
        private void initScrollData() {
            BigImage bigImage = new BigImage(getContext());

            if(currentType.startsWith("2")){
                title = new String[1];
                title[0] = maps.get(0).getTitle();
                if(maps.get(0).getSkipType().equals("photoset")){//图集
                    views.add(bigImage.getBigImagePhotoSet(
                            maps.get(0).getImgsrc()
                            ,maps.get(0).getPhotosetID()
                            ,maps.get(0).getTitle()
                    ));
                }else if(maps.get(0).getSkipType().equals("special")){//专题
                    views.add(bigImage.getBigImageSpcial(
                            maps.get(0).getImgsrc()
                            ,maps.get(0).getSpecialID()
                    ));
                }else if(maps.get(0).getSkipType().equals("doc")
                        || maps.get(0).getSkipType() == null){//普通
                    views.add(bigImage.getBigImageNormal(
                            maps.get(0).getImgsrc()
                            ,maps.get(0).getDocid()
                    ));
                }
            }else {
                title = new String[maps.get(-999).getAds().size() + 1];
                for (int i = 0; i < maps.get(-999).getAds().size() + 1; i++) {
                    if(i==0){
                        if(maps.get(-999).getSkipType().equals("photoset")){//图集
                            views.add(bigImage.getBigImagePhotoSet(
                                    maps.get(-999).getImgsrc()
                                    ,maps.get(-999).getSkipID()
                                    ,maps.get(-999).getTitle()
                            ));
                        }else if(allNewsBean.getABean().get(0).getSkipType().equals("special")){//专题
                            views.add(bigImage.getBigImageSpcial(
                                    maps.get(-999).getImgsrc()
                                    ,maps.get(-999).getSpecialID()
                            ));
                        }else if(maps.get(-999).getSkipType().equals("doc")){//普通
                            views.add(bigImage.getBigImageNormal(
                                    maps.get(-999).getImgsrc()
                                    ,maps.get(-999).getDocid()
                            ));
                        }
                        title[i] = maps.get(-999).getTitle();
                    }else{
                        if(maps.get(-999).getAds().get(i-1).getTag().equals("photoset")){//图集
                            views.add(bigImage.getBigImagePhotoSet(
                                    maps.get(-999).getAds().get(i-1).getImgsrc()
                                    ,maps.get(-999).getAds().get(i-1).getUrl()
                                    ,maps.get(-999).getAds().get(i-1).getTitle()
                            ));
                        }else if(allNewsBean.getABean().get(0).getAds().get(i-1).getTag().equals("special")){//专题
                            views.add(bigImage.getBigImageSpcial(
                                    maps.get(-999).getAds().get(i-1).getImgsrc()
                                    ,maps.get(-999).getAds().get(i-1).getUrl()
                            ));
                        }else if(maps.get(-999).getAds().get(i-1).getTag().equals("doc")){//普通
                            views.add(bigImage.getBigImageNormal(
                                    maps.get(-999).getAds().get(i-1).getImgsrc()
                                    ,maps.get(-999).getAds().get(i-1).getUrl()
                            ));
                        }
                        title[i] = maps.get(-999).getAds().get(i-1).getTitle();
                    }
                }
            }



        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }
}
