package com.others.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.others.activity.FoundActivity;
import com.others.bean.Banner;
import com.others.bean.FoundBean;
import com.others.bean.Recommend;
import com.others.utils.MyCustomRequest;
import com.others.utils.MyListView;
import com.news.activity.R;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * Created by Administrator on 2016/11/10.
 */
public class FoundFragment extends Fragment {
    private final static String TAG = "tmd";
    //    private OkHttpClient client;
    private RequestQueue queue;
    private ArrayList<Banner.BannerBean> bannerList = new ArrayList<>();
    private ArrayList<String> bannerImageList = new ArrayList<>();
    private ArrayList<ImageView> BannerImageList = new ArrayList<>();
    private ArrayList<String> bannerurl = new ArrayList<>();
    private ArrayList<Recommend.RecommendBean> recommendList = new ArrayList<>();
    private ArrayList<String> recommendIcon = new ArrayList<>();
    private ArrayList<String> recommendTitle = new ArrayList<>();
    private ArrayList<String> recommendUrl = new ArrayList<>();
    private ArrayList<FoundBean.ResultBean.DataBean> foundList = new ArrayList<>();
    private ArrayList<String> foundurl = new ArrayList<>();
    private XRecyclerView mFoundFragmentXRecyclerView;
    private MyAdapter adapter;
    private FoundViewPagerAdapter foundviewpageradapter;
    private FoundGridViewAdapter foundgridviewadapter;
    private FoundListViewAdapter foundlistviewadapter;
    private Runnable r;
    private Handler handler = new Handler();
    private boolean flag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_found, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());
//        client=new OkHttpClient();
        initView(view);
        initData();
        initAdapter();
        mFoundFragmentXRecyclerView.setPullRefreshEnabled(true);
        mFoundFragmentXRecyclerView.setLoadingMoreEnabled(true);
        mFoundFragmentXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);

        //自定义下拉刷新过程中的动画效果
        mFoundFragmentXRecyclerView.setRefreshProgressStyle(ProgressStyle.TriangleSkewSpin);
        mFoundFragmentXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        foundlistviewadapter.notifyDataSetChanged();
                        mFoundFragmentXRecyclerView.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        foundlistviewadapter.notifyDataSetChanged();
                        mFoundFragmentXRecyclerView.loadMoreComplete();
                    }
                }, 2000);
            }
        });

    }


    private void initData() {
        initViewPager();
        initRadioGroup();
        initListView();
    }

    private void initRadioGroup() {
        MyCustomRequest<Recommend> mcr = new MyCustomRequest<Recommend>(
                "http://c.m.163.com/nc/topicset/uc/api/discovery/indexV52",
                Recommend.class,
                new Response.Listener<Recommend>() {
                    @Override
                    public void onResponse(Recommend response) {
                        recommendList.addAll(response.getRecommend());
                        foundgridviewadapter.notifyDataSetChanged();
                    }
                }, null

        );
        queue.add(mcr);
    }


    private void initListView() {
        MyCustomRequest<FoundBean> mcr = new MyCustomRequest<FoundBean>(
                "http://v.juhe.cn/toutiao/index?type=top&key=c367783a0881edefa8ab872095f2414a",
                FoundBean.class,
                new Response.Listener<FoundBean>() {
                    @Override
                    public void onResponse(FoundBean response) {
                        foundList.addAll(response.getResult().getData());
                        foundlistviewadapter.notifyDataSetChanged();
                    }
                }, null

        );
        queue.add(mcr);
    }


    private void initViewPager() {
        MyCustomRequest<Banner> mcr = new MyCustomRequest<Banner>(
                "ne",
                Banner.class,
                new Response.Listener<Banner>() {
                    @Override
                    public void onResponse(Banner response) {
                        Log.i(TAG, "response.getBanner的值" + response.getBanner().toString());
//                        Log.i(TAG,"response的值"+response);
                        bannerList.addAll(response.getBanner());
                        for (int i = 0; i < bannerList.size(); i++) {
                            bannerImageList.add(bannerList.get(i).getImage());
                            bannerurl.add(bannerList.get(i).getUrl());
                        }

                        foundviewpageradapter.notifyDataSetChanged();
                    }
                }, null
        );
        queue.add(mcr);
    }


    private void initAdapter() {
        mFoundFragmentXRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyAdapter();
        mFoundFragmentXRecyclerView.setAdapter(adapter);
        mFoundFragmentXRecyclerView.setFocusable(true);
    }


    private void initView(View view) {
        mFoundFragmentXRecyclerView = (XRecyclerView) view.findViewById(R.id.FoundFragment_XRecyclerView);
    }


    class MyAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else if (position == 1) {
                return 1;
            } else {
                return 2;
            }

        }

        @Override
        public XRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder = null;
            switch (viewType) {
                case 0:
                    holder = new FoundViewPagerHolder(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_found_banner, null));

                    break;

                case 1:
                    holder = new FoundGridViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_found_recommend, null));
                    break;

                case 2:
                    holder = new FoundListViewHodler(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_found_stream, null));
                    break;

            }
            return holder;
        }

        @Override
        public void onBindViewHolder(XRecyclerView.ViewHolder holder, int position) {
            switch (getItemViewType(position)) {
                case 0:
                    final FoundViewPagerHolder mFoundViewPagerHolder = (FoundViewPagerHolder) holder;
                    r = new Runnable() {
                        @Override
                        public void run() {
                            mFoundViewPagerHolder.FoundFragment_ViewPager.setCurrentItem(mFoundViewPagerHolder.FoundFragment_ViewPager.getCurrentItem() + 1);
                            handler.postDelayed(r, 5000);
                        }
                    };
                    handler.postDelayed(r, 5000);
                    mFoundViewPagerHolder.FoundFragment_ViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            
                        }

                        @Override
                        public void onPageSelected(int position) {
                            
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
                            switch (state){
                                case ViewPager.SCROLL_STATE_DRAGGING:
                                    handler.removeCallbacks(r);
                                    flag=true;
                                    break;
                                case ViewPager.SCROLL_STATE_IDLE:
                                    if(flag){
                                        handler.postDelayed(r,5000);
                                        flag=false;
                                    }
                                
                            }
                        }
                    });
                    foundviewpageradapter = new FoundViewPagerAdapter(bannerImageList);
                    mFoundViewPagerHolder.FoundFragment_ViewPager.setAdapter(foundviewpageradapter);

                    break;

                case 1:
                    FoundGridViewHolder mFoundGridViewHolder = (FoundGridViewHolder) holder;
                    foundgridviewadapter = new FoundGridViewAdapter(recommendList);
                    mFoundGridViewHolder.FoundFragment_GridView.setAdapter(foundgridviewadapter);
                    mFoundGridViewHolder.FoundFragment_GridView.setFocusable(false);
                    break;

                case 2:
                    FoundListViewHodler mFoundListViewHodler = (FoundListViewHodler) holder;
                    foundlistviewadapter = new FoundListViewAdapter(foundList);
                    mFoundListViewHodler.FoundFragment_ListView.setAdapter(foundlistviewadapter);
                    mFoundListViewHodler.FoundFragment_ListView.setFocusable(false);
                    break;

            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        class FoundViewPagerHolder extends XRecyclerView.ViewHolder {
            ViewPager FoundFragment_ViewPager;

            public FoundViewPagerHolder(View itemView) {
                super(itemView);
                FoundFragment_ViewPager = (ViewPager) itemView.findViewById(R.id.FoundFragment_ViewPager);
            }
        }

        class FoundGridViewHolder extends XRecyclerView.ViewHolder {
            GridView FoundFragment_GridView;

            public FoundGridViewHolder(View itemView) {
                super(itemView);
                FoundFragment_GridView = (GridView) itemView.findViewById(R.id.FoundFragment_GridView);
            }
        }

        class FoundListViewHodler extends XRecyclerView.ViewHolder {
            MyListView FoundFragment_ListView;

            public FoundListViewHodler(View itemView) {
                super(itemView);
                FoundFragment_ListView = (MyListView) itemView.findViewById(R.id.FoundFragment_ListView);
            }
        }
    }

    class FoundViewPagerAdapter extends PagerAdapter {
        private ArrayList<String> bannerImageList = new ArrayList<>();
        private ImageView imageView;

        public FoundViewPagerAdapter(ArrayList<String> bannerImageList) {
            this.bannerImageList = bannerImageList;
        }

        @Override
        public int getCount() {
            return bannerImageList.size() == 0 ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            for (int i = 0; i < bannerImageList.size(); i++) {
                imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getActivity())
                        .load(bannerImageList.get(i))
                        .skipMemoryCache(false)
                        .crossFade(2000)
                        .placeholder(R.mipmap.no_loading)
                        .into(imageView);
                BannerImageList.add(imageView);
            }
            ViewGroup parent = (ViewGroup) BannerImageList.get(position % bannerImageList.size()).getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            container.addView(BannerImageList.get(position % bannerImageList.size()));
            BannerImageList.get(position % bannerImageList.size()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), FoundActivity.class);

                    intent.putExtra("path", bannerurl.get(position % bannerImageList.size()));

                    startActivity(intent);
                }
            });
            return BannerImageList.get(position % bannerImageList.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(BannerImageList.get(position % bannerImageList.size()));
        }
    }

    class FoundGridViewAdapter extends BaseAdapter {
        private ArrayList<Recommend.RecommendBean> recommendList = new ArrayList<>();

        public FoundGridViewAdapter(ArrayList<Recommend.RecommendBean> recommendList) {
            this.recommendList = recommendList;
        }

        @Override
        public int getCount() {
            return recommendList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.foundgridview_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            for (int j = 0; j < recommendList.size(); j++) {
                recommendIcon.add(recommendList.get(i).getIcon());
                recommendTitle.add(recommendList.get(i).getTitle());
                recommendUrl.add(recommendList.get(i).getUrl());
            }
            for (int k = 0; k < recommendIcon.size(); k++) {
                Glide.with(getActivity())
                        .load(recommendIcon.get(k))
                        .crossFade(2000)
                        .placeholder(R.mipmap.small_no_loading)
                        .skipMemoryCache(false)
                        .into(holder.mFoundgridviewItemIv);
                holder.mFoundgridviewItemTv.setText(recommendTitle.get(k));
            }
            holder.mFoundgridviewItemIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), FoundActivity.class);
                    intent.putExtra("path", recommendUrl.get(i));
                    startActivity(intent);

                }
            });

            return view;
        }


        class ViewHolder {
            public View rootView;
            public ImageView mFoundgridviewItemIv;
            public TextView mFoundgridviewItemTv;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.mFoundgridviewItemIv = (ImageView) rootView.findViewById(R.id.foundgridview_item_iv);
                this.mFoundgridviewItemTv = (TextView) rootView.findViewById(R.id.foundgridview_item_tv);
            }
        }
    }

    class FoundListViewAdapter extends BaseAdapter {
        private ArrayList<FoundBean.ResultBean.DataBean> foundList = new ArrayList<>();

        public FoundListViewAdapter(ArrayList<FoundBean.ResultBean.DataBean> foundList) {
            this.foundList = foundList;
        }

        @Override
        public int getCount() {
            return foundList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.foundlistview_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.mFoundlistviewTitle.setText(foundList.get(i).getTitle());
            Glide.with(getActivity())
                    .load(foundList.get(i).getThumbnail_pic_s())
                    .placeholder(R.mipmap.no_loading)
                    .skipMemoryCache(false)
                    .into(holder.mFoundlistviewFirstImg);
            holder.mFoundlistviewAuthorName.setText(foundList.get(i).getAuthor_name());
            holder.mFoundlistviewRealtype.setText(foundList.get(i).getRealtype());
            holder.mFoundlistviewDate.setText(foundList.get(i).getDate());
            holder.mFoundlistviewFirstImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), FoundActivity.class);
                    intent.putExtra("path", foundList.get(i).getUrl());
                    startActivity(intent);
                }
            });
            holder.mFoundlistviewShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShareSDK.initSDK(getActivity());
                    OnekeyShare oks=new OnekeyShare();
                    oks.disableSSOWhenAuthorize();
                    //新浪微博
                    oks.setText(foundList.get(i).getTitle());
                    oks.setImageUrl(foundList.get(i).getUrl());
                    //qq空间ow
                    oks.setText(foundList.get(i).getTitle());
                    //qq
                    oks.setImageUrl(foundList.get(i).getThumbnail_pic_s());
                    oks.setTitle(foundList.get(i).getTitle());
                    oks.show(getActivity());
                }
            });

            return view;
        }


        public class ViewHolder {
            public View rootView;
            public TextView mFoundlistviewTitle;
            public ImageView mFoundlistviewFirstImg;
            public TextView mFoundlistviewAuthorName;
            public TextView mFoundlistviewRealtype;
            public TextView mFoundlistviewDate;
            public ImageView mFoundlistviewShare;
            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.mFoundlistviewTitle = (TextView) rootView.findViewById(R.id.Foundlistview_title);
                this.mFoundlistviewFirstImg = (ImageView) rootView.findViewById(R.id.Foundlistview_firstImg);
                this.mFoundlistviewAuthorName = (TextView) rootView.findViewById(R.id.Foundlistview_author_name);
                this.mFoundlistviewRealtype = (TextView) rootView.findViewById(R.id.Foundlistview_realtype);
                this.mFoundlistviewDate = (TextView) rootView.findViewById(R.id.Foundlistview_date);
                this.mFoundlistviewShare= (ImageView) rootView.findViewById(R.id.Foundlistview_share);
            }

        }
    }
}
