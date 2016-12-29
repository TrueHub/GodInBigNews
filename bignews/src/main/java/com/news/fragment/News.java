package com.news.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.news.activity.R;
import com.news.drag.DragAdapter;
import com.news.drag.DragGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by 371964363 on 2016/11/10.
 */
public class News extends Fragment {
    private SharedPreferences pre;
    private ImageView imageView;//点击回到头条
    private HashMap<String,String> urlType = new HashMap<>();
    private TabLayout tablayout;
    private ViewPager viewpager;
    private HorizontalScrollView scrollView;

    //fragment内部支持下拉刷新
    private ArrayList<XRecyclerView> recyclerViews = new ArrayList<>();
    private ArrayList<Fragment> viewPagers = new ArrayList<>();
    private CheckBox box;




    //选择菜单
    private ArrayList<String> title = new ArrayList<>();
    ArrayList<String> hideList = new ArrayList<>();

    private LinearLayout linearLayout2;

    private DragAdapter dragGridAdapter;
    private RecyclerView recyclerView2;
    private DragGridView dragGridView;
    private MyHideRecycler recyclerHideAdapter;
    private ViewPagerAdapter viewPagerAdapter;
    private Handler dragHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
                    viewpager.setAdapter(viewPagerAdapter);
                    break;
                case 2:

                    int pos = msg.arg1;
                    if(pos != 0){
                        hideList.add(title.remove(pos));
                        recyclerHideAdapter.notifyDataSetChanged();
                        dragHandler.sendEmptyMessage(1);
                    }
                    break;
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pre = getActivity().getSharedPreferences("", getContext().MODE_PRIVATE);//初始化轻量级数据库
        initFirstOpen();
        initUrlType();
        initHideList();
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    private void initFirstOpen() {
        SharedPreferences.Editor editor = pre.edit();
        if(isFirstOpen()){
            String[] titles = pre.getString("newsTitle","").split("Y");
            for (int i = 0; i < titles.length; i++) {
                title.add(titles[i]);
            }
        }else {
            editor.putBoolean("isFirst",true);
            editor.commit();
            title.add("1头条");title.add("1娱乐");title.add("1体育");
            title.add("1财经");title.add("1科技");title.add("1时尚");
            title.add("2轻松一刻");title.add("2军事");title.add("2历史");
            title.add("2游戏");title.add("2政务");title.add("2NBA");
        }
    }

    private void initHideList() {
            Iterator<String> iterators  = urlType.keySet().iterator();
            while (iterators.hasNext()){
                String key = iterators.next();
                if(!title.contains(key)){
                    hideList.add(key);
                }
            }
    }
    private void initUrlType() {
        urlType.put("1头条","http://c.m.163.com/nc/article/headline/T1348647909107/0-140.html");
        urlType.put("1娱乐","http://c.3g.163.com/nc/article/list/T1348648517839/0-20.html");
        urlType.put("1体育","http://c.3g.163.com/nc/article/list/T1348649079062/0-20.html");
        urlType.put("1财经","http://c.3g.163.com/nc/article/list/T1348648756099/0-20.html");
        urlType.put("1科技","http://c.3g.163.com/nc/article/list/T1348649580692/0-20.html");
        urlType.put("1时尚","http://c.m.163.com/nc/article/list/T1348650593803/0-20.html");
        urlType.put("2轻松一刻","http://c.3g.163.com/nc/article/list/T1350383429665/0-20.html");
        urlType.put("2军事","http://c.3g.163.com/nc/article/list/T1348648141035/0-20.html");
        urlType.put("2历史","http://c.m.163.com/nc/article/list/T1368497029546/0-20.html");
        urlType.put("2游戏","http://c.m.163.com/nc/article/list/T1348654151579/0-20.html");
        urlType.put("2政务","http://c.m.163.com/nc/article/list/T1414142214384/0-20.html");
        urlType.put("2NBA","http://c.m.163.com/nc/article/list/T1348649145984/0-20.html");
        urlType.put("2亲子","http://c.3g.163.com/nc/article/list/T1397116135282/0-20.html");
        urlType.put("2博客","http://c.3g.163.com/nc/article/list/T1349837698345/0-20.html");
        urlType.put("2暴雪游戏","http://c.3g.163.com/nc/article/list/T1397016069906/0-20.html");
        urlType.put("2社会","http://c.3g.163.com/nc/article/list/T1348648037603/0-20.html");
        urlType.put("2态度营销","http://c.3g.163.com/nc/article/list/T1464592736048/0-20.html");
        urlType.put("2哒哒趣闻","http://c.3g.163.com/nc/article/list/T1444289532601/0-20.html");
        urlType.put("2彩票","http://c.3g.163.com/nc/article/list/T1356600029035/0-20.html");
        urlType.put("2手机","http://c.3g.163.com/nc/article/list/T1348649654285/0-20.html");
        urlType.put("2数码","http://c.3g.163.com/nc/article/list/T1348649776727/0-20.html");
        urlType.put("2智能","http://c.3g.163.com/nc/article/list/T1351233117091/0-20.html");
        urlType.put("2态度公开课","http://c.3g.163.com/nc/article/list/T1456394562871/0-20.html");
        urlType.put("2旅行","http://c.3g.163.com/nc/article/list/T1348654204705/0-20.html");
        urlType.put("2读书","http://c.3g.163.com/nc/article/list/T1401272877187/0-20.html");
        urlType.put("2酒香","http://c.3g.163.com/nc/article/list/T1385429690972/0-20.html");
        urlType.put("2教育","http://c.3g.163.com/nc/article/list/T1348654225495/0-20.html");
        urlType.put("2情感","http://c.3g.163.com/nc/article/list/T1348650839000/0-20.html");
        urlType.put("2艺术","http://c.3g.163.com/nc/article/list/T1441074311424/0-20.html");
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = (ImageView) view.findViewById(R.id.fragment_news_image_main);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(0);
            }
        });
        scrollView = (HorizontalScrollView) view.findViewById(R.id.fragment_news_scrollview);
        tablayout = (TabLayout) view.findViewById(R.id.fragment_news_tablayout);
        viewpager = (ViewPager) view.findViewById(R.id.fragment_news_viewpager);

        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setKeepScreenOn(true);

        tablayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tablayout.setSelectedTabIndicatorColor(Color.rgb(240, 0, 0));
        tablayout.setTabTextColors(Color.rgb(200, 200, 200), Color.rgb(240, 0, 0));
        tablayout.setupWithViewPager(viewpager);
        //切换栏目按钮
        box = (CheckBox) view.findViewById(R.id.fragment_news_menus);
        initBox();
        initData();
        linearLayout2 = (LinearLayout) view.findViewById(R.id.fragment_news_change_Linearlayout2);
        dragGridView = (DragGridView) view.findViewById(R.id.fragment_news_Recycler1);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.fragment_news_Recycler2);
        initRecycler();
    }

    private void initRecycler() {
        dragGridAdapter = new DragAdapter(getContext(),title,dragHandler,viewPagers);
        dragGridView.setAdapter(dragGridAdapter);

        recyclerHideAdapter = new MyHideRecycler();

        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(),4,LinearLayout.VERTICAL,false));
        recyclerView2.setAdapter(recyclerHideAdapter);
    }

    private void initBox() {
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    viewpager.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                }else {
                    viewpager.setVisibility(View.VISIBLE);
                    linearLayout2.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        SharedPreferences.Editor editor = pre.edit();
        editor.remove("newsTitle");
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < title.size()-1; i++) {
            buffer.append(title.get(i)+"Y");
        }
        buffer.append(title.get(title.size()-1));
        editor.putString("newsTitle",buffer.toString());
        editor.commit();
        super.onDestroy();
    }

    private void initData() {
        for (int i = 0; i < title.size(); i++) {
            viewPagers.add(NewsAllPager.getInstance(urlType.get(title.get(i)),title.get(i)));
        }
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewpager.setAdapter(viewPagerAdapter);
    }

    public boolean isFirstOpen() {
        return pre.getBoolean("isFirst",false);
    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            return viewPagers.get(position);
        }
        @Override
        public int getCount() {
            return title.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if(title.size() == 0){
                title.add("1头条");title.add("1娱乐");title.add("1体育");
                title.add("1财经");title.add("1科技");title.add("1时尚");
                title.add("2轻松一刻");title.add("2军事");title.add("2历史");
                title.add("2游戏");title.add("2政务");title.add("2NBA");
                dragHandler.sendEmptyMessage(1);
            }
            return title.get(position).substring(1);
        }
    }

    class MyHideRecycler extends RecyclerView.Adapter<MyHideRecycler.MyHideHodler>{
        @Override
        public MyHideHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHideHodler(LayoutInflater.from(getContext()).inflate(R.layout.item_news_changemenu,parent,false));
        }
        @Override
        public void onBindViewHolder(MyHideHodler holder, final int position) {
            holder.button.setText(hideList.get(position).substring(1));
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title.add(hideList.get(position));
                    notifyDataSetChanged();
                    dragGridAdapter.notifyDataSetChanged();
                    viewPagers.add(NewsAllPager.getInstance(urlType.get(hideList.get(position)),hideList.remove(position)));
                    viewPagerAdapter.notifyDataSetChanged();
                    viewpager.setCurrentItem(viewPagers.size());

                    SharedPreferences.Editor editor = pre.edit();
                    editor.remove("newsTitle");
                    StringBuffer buffer = new StringBuffer();

                    for (int i = 0; i < title.size()-1; i++) {
                        buffer.append(title.get(i)+"Y");
                    }
                    buffer.append(title.get(title.size()-1));
                    editor.putString("newsTitle",buffer.toString());
                    editor.commit();
                }
            });
            ViewGroup.LayoutParams params = holder.button.getLayoutParams();
            params.width = 150;
            params.height = 60;
            holder.button.setLayoutParams(params);
        }
        @Override
        public int getItemCount() {
            return hideList.size();
        }
        class MyHideHodler extends RecyclerView.ViewHolder{
            private Button button;
            public MyHideHodler(View itemView) {
                super(itemView);
                button = (Button) itemView.findViewById(R.id.item_news_changemenu_but);
            }
        }
    }

}
