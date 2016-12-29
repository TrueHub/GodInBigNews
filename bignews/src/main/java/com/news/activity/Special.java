package com.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.news.bean.SpecialBean;
import com.news.utils.MyCustomRequest;
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

public class Special extends AppCompatActivity {
    String url;
    String fled;
    private XRecyclerView xRecyclerView;
    private RequestQueue queue;
    private ImageView imageView;
    private int count = 0;
    private Animation animation;
    private SpecialBean specialBean;
    private MyRecyclerAdapter recyclerAdapter;
    private int[] countSize;
    private int[] lineSize;
    private HashMap<Integer,SpecialBean.ABean.TopicsBean.DocsBean> maps = new HashMap<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    recyclerAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        url = getUrl(getIntent().getStringExtra("special"));
        initView();

        initData();

        initAdapter();

        initListener();


    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                specialBean = httpFunction(fled);


                int count = 1;
                int pos = 0;
                for (int i = 0; i < specialBean.getlistBean().getTopics().size(); i++) {
                    count+=(specialBean.getlistBean().getTopics().get(i).getDocs().size()+1);
                }
                countSize = new int[count];
                lineSize = new int[specialBean.getlistBean().getTopics().size()];
                for (int i = 0; i < specialBean.getlistBean().getTopics().size(); i++) {
                    if(i ==0 ){
                        countSize[pos] = 2;
                        pos++;
                    }
                    for(int k=-1; k<specialBean.getlistBean().getTopics().get(i).getDocs().size();k++){
                        if(k == -1){
                            countSize[pos] = 6;
                            lineSize[i] = pos;
                        }else if(specialBean.getlistBean().getTopics().get(i).getDocs().get(k).getSkipType().equals("")){
                            countSize[pos] = 0;
                            maps.put(pos,specialBean.getlistBean().getTopics().get(i).getDocs().get(k));

                        }else if(specialBean.getlistBean().getTopics().get(i).getDocs().get(k).getSkipType().equals("photoset")) {
                            countSize[pos] = 1;
                            maps.put(pos,specialBean.getlistBean().getTopics().get(i).getDocs().get(k));
                        }
                        pos++;
                    }
                }
                handler.sendEmptyMessage(1);
            }
        }).start();

    }
    private void initAdapter() {
        recyclerAdapter = new MyRecyclerAdapter();
        xRecyclerView.setAdapter(recyclerAdapter);
    }
    private void initListener() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setPullRefreshEnabled(true);

        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        //上拉加载下拉刷新
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                MyCustomRequest<SpecialBean> news = new MyCustomRequest<SpecialBean>(url, SpecialBean.class,
                        new Response.Listener<SpecialBean>() {
                            @Override
                            public void onResponse(SpecialBean response) {
                                initData();
                                xRecyclerView.refreshComplete();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(news);
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    public String getUrl(String docId) {
        fled = docId;
        return "http://c.3g.163.com/nc/special/XXX.html".replace("XXX", docId);
    }

    //手动解析json
    public SpecialBean httpFunction(String fled) {
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
                SpecialBean specialBean = new SpecialBean();
                JSONObject jsonObject = new JSONObject(json);
                //第一层S1479197787084
                SpecialBean.ABean aBean = specialBean.new ABean();
                JSONObject s = jsonObject.getJSONObject(fled);//动态类名
                try {
                    aBean.setSid(s.optString("sid"));
                    aBean.setSkipcontent(s.optString("skipcontent"));
                    aBean.setType(s.optString("type"));
                    aBean.setSname(s.optString("sname"));
                    aBean.setLmodify(s.optString("lmodify"));
                    aBean.setPtime(s.optString("ptime"));
                    aBean.setBanner(s.optString("banner"));
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                //第二层

                JSONArray topArray = s.optJSONArray("topics");
                ArrayList<SpecialBean.ABean.TopicsBean> topicsBeans = new ArrayList<>();
                for (int i = 0; i < topArray.length(); i++) {
                    SpecialBean.ABean.TopicsBean topBean = aBean.new TopicsBean();
                    JSONObject topObject = topArray.optJSONObject(i);
                    try {
                        topBean.setIndex(topObject.optInt("index"));
                        topBean.setTname(topObject.optString("tname"));
                        topBean.setShortname(topObject.optString("shortname"));
                        topBean.setType(topObject.optString("type"));
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    //第三层
                    ArrayList<SpecialBean.ABean.TopicsBean.DocsBean> docList = new ArrayList<>();
                    JSONArray docArray = topObject.optJSONArray("docs");


                    for (int j = 0; j < docArray.length(); j++) {
                        SpecialBean.ABean.TopicsBean.DocsBean docs = topBean.new DocsBean();
                        JSONObject docObject = docArray.optJSONObject(j);
                        try {
                            docs.setImgsrc(docObject.optString("imgsrc"));
                            docs.setPostid(docObject.optString("postid"));
                            docs.setReplyCount(docObject.optInt("replyCount"));
                            docs.setTitle(docObject.optString("title"));
                            docs.setVotecount(docObject.optInt("votecount"));
                            docs.setLtitle(docObject.optString("ltitle"));
                            docs.setDocid(docObject.optString("docid"));
                            docs.setPtime(docObject.optString("ptime"));
                            docs.setSkipType(docObject.optString("skipType"));
                            docs.setPhotosetID(docObject.optString("photosetID"));
                            //第四层
                            JSONArray imgArray = docObject.optJSONArray("imgextra");
                            ArrayList<SpecialBean.ABean.TopicsBean.DocsBean.ImgextraBean> imgextraList = new ArrayList<>();
                            for (int k = 0; k < imgArray.length(); k++) {
                                JSONObject imgObject = imgArray.optJSONObject(k);
                                SpecialBean.ABean.TopicsBean.DocsBean.ImgextraBean imgextra = docs.new ImgextraBean();
                                imgextra.setImgsrc(imgObject.optString("imgsrc"));
                                imgextraList.add(imgextra);
                            }
                            docs.setImgextra(imgextraList);

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                        docList.add(docs);
                    }
                    //TODO 添加一个小标题
//                    SpecialBean.ABean.TopicsBean.DocsBean docs = topBean.new DocsBean();
//                    docs.setTitle("小标题"+i);
//                    docList.add(docs);

                    topBean.setDocs(docList);
                    topicsBeans.add(topBean);
                }
                aBean.setTopics(topicsBeans);
                specialBean.setlistBean(aBean);
                return specialBean;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initView() {
        xRecyclerView = (XRecyclerView) findViewById(R.id.activity_special);
        queue = Volley.newRequestQueue(this);
        imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.fragment_news_viewpager,
                null).findViewById(R.id.fragment_loadmore_img);
        animation = AnimationUtils.loadAnimation(this,R.anim.fragment_loadmore);
        imageView.startAnimation(animation);
    }

    public class MyRecyclerAdapter extends XRecyclerView.Adapter<RecyclerView.ViewHolder>{


        @Override
        public int getItemViewType(int position) {
            return countSize[position];
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder = null;
            switch (viewType){
                case 0:
                    //单张图片普通新闻
                    holder = new MyOneHolder(LayoutInflater.from(Special.this).inflate(R.layout.item_fragment_news_itme1,parent,false));
                    break;
                case 1:
                    //三张图片普通新闻
                    holder = new MyTwoHolder(LayoutInflater.from(Special.this).inflate(R.layout.item_fragment_news_itme2,parent,false));
                    break;
                case 2:
                    //顶端大图片点击viewpager浏览新闻
                    holder = new MyThreHolder(LayoutInflater.from(Special.this).inflate(R.layout.item_spceial_topimage,parent,false));
                    break;
                case 3:
                    //专题新闻
                    holder = new MyFourHolder(LayoutInflater.from(Special.this).inflate(R.layout.item_fragment_news_item4,parent,false));
                    break;
                case 6:
                    holder = new MySixHolder(LayoutInflater.from(Special.this).inflate(R.layout.item_special_blackline,parent,false));
                    break;
            }

            return holder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            switch (getItemViewType(position)){
                case 0:
                    MyOneHolder oneHolder = (MyOneHolder) holder;
                    Glide.with(Special.this)
                                    .load(maps.get(position).getImgsrc())
                                    .into(oneHolder.imageView);
                            oneHolder.title.setText(maps.get(position).getTitle());
                            if(maps.get(position).getReplyCount()==0){
                                oneHolder.time.setText("");
                            }else {
                                oneHolder.time.setText(maps.get(position).getReplyCount()+"");
                            }
                    oneHolder.author.setText("");

                    break;
                case 1:
                    MyTwoHolder twoHolder = (MyTwoHolder) holder;
                    twoHolder.title.setText(maps.get(position).getTitle());
                    if(maps.get(position).getReplyCount()!=0){
                        twoHolder.time.setText(maps.get(position).getReplyCount()+"人跟帖");
                    }else {
                        twoHolder.time.setText("");
                    }

                    Glide.with(Special.this).load(maps.get(position).getImgsrc()).into(twoHolder.imageView1);
                    Glide.with(Special.this).load(maps.get(position).getImgextra().get(0).getImgsrc()).into(twoHolder.imageView2);
                    Glide.with(Special.this).load(maps.get(position).getImgextra().get(1).getImgsrc()).into(twoHolder.imageView3);
                    break;
                case 2:
                    final MyThreHolder threHolder = (MyThreHolder) holder;
                    Glide.with(Special.this).load(specialBean.getlistBean().getBanner()).into(threHolder.imageView);
                    threHolder.gridView.setLayoutManager(new GridLayoutManager(Special.this,4,GridLayoutManager.VERTICAL,false));
                    threHolder.gridView.setAdapter(new Adapterii());
                    break;
                case 3:

                    break;
                case 6:
                    MySixHolder sixHolder = (MySixHolder) holder;
                    boolean b = true;
                    int sixCount = 0;
                    for (int i = 0; i < specialBean.getlistBean().getTopics().size(); i++) {
                        for(int j=0; j<specialBean.getlistBean().getTopics().get(i).getDocs().size();j++){
                            sixCount++;
                            if(position==sixCount){
                                sixHolder.textView1.setText((i+1)+"/"+specialBean.getlistBean().getTopics().size());
                                sixHolder.textView2.setText(specialBean.getlistBean().getTopics().get(i).getShortname());
                            }
                        }
                    }
                    break;
            }


        }

        @Override
        public int getItemCount() {
            if(specialBean !=null){
                return countSize.length;
            }else {
                return  0;
            }
        }

        class MyOneHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView title, author, time;
            public MyOneHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tempUrl = maps.get(getPosition()-1).getPostid();
                        Intent intent = new Intent(Special.this, ReadContentActivity.class);
                        intent.putExtra("docId", tempUrl);
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
                        Intent intent = new Intent(Special.this, BigImageView.class);
                        intent.putExtra("url",maps.get(getPosition()-1).getPhotosetID());
                        intent.putExtra("title",maps.get(getPosition()-1).getTitle());
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
        //顶端大图片下边button
        public class MyThreHolder extends RecyclerView.ViewHolder{
            public ImageView imageView;
            public RecyclerView gridView;
            public MyThreHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.item_special_topimage_image);
                gridView = (RecyclerView) itemView.findViewById(R.id.item_special_topimage_gridlayout);
            }
        }

        class MyFourHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView title, time, skipType;
            public MyFourHolder(View itemView) {
                super(itemView);
//
//
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(this, Special.class);
//                        intent.putExtra("special", specialBean.getT1348647909107().get(getPosition()-1).getSpecialID());
//                        startActivity(intent);
//                    }
//                });
//                imageView = (ImageView) itemView.findViewById(R.id.itme_fragment_new_item4_img);
//                title = (TextView) itemView.findViewById(R.id.itme_fragment_new_item4_title);
//                time = (TextView) itemView.findViewById(R.id.itme_fragment_new_item4_time);
//                skipType = (TextView) itemView.findViewById(R.id.itme_fragment_new_item4_zhuanti);
            }
        }

        class MySixHolder extends RecyclerView.ViewHolder{
            TextView textView1, textView2;
            public MySixHolder(View itemView) {
                super(itemView);
                textView1 = (TextView) itemView.findViewById(R.id.item_special_top_text1);
                textView2 = (TextView) itemView.findViewById(R.id.item_special_top_text2);
            }
        }
    }
    class Adapterii extends RecyclerView.Adapter<Adapterii.MyHodler>{

        @Override
        public Adapterii.MyHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHodler(LayoutInflater.from(Special.this).inflate(R.layout.item_news_changemenu,parent,false));
        }

        @Override
        public void onBindViewHolder(Adapterii.MyHodler holder, final int position) {

                ViewGroup.LayoutParams params = holder.button.getLayoutParams();
                params.width = getResources().getDisplayMetrics().widthPixels/5;
                params.height = 60;
                holder.button.setLayoutParams(params);
                holder.button.setText(specialBean.getlistBean().getTopics().get(position).getShortname());
                holder.button.setBackgroundResource(R.drawable.special_topimage_but_shape);
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        xRecyclerView.smoothScrollToPosition(lineSize[position]+7);
                    }
                });
        }

        @Override
        public int getItemCount() {
            return specialBean.getlistBean().getTopics().size();
        }
        class MyHodler extends RecyclerView.ViewHolder{
            public Button button;
            public MyHodler(View itemView) {
                super(itemView);
                button = (Button) itemView.findViewById(R.id.item_news_changemenu_but);
            }
        }
    }

}
