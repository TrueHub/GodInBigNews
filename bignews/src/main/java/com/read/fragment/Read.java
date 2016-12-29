package com.read.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.read.fragment.readfragment.ReadRecommendFragment;
import com.read.fragment.readfragment.ReadSubscriberFragment;
import com.news.activity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 阅读页面，vidio。主体为两页ViewPager，可左右滑动，
 * viewpager上方对应显示当前页面的分类标题。
 * <p/>
 * 推荐阅读页面：主体为listview，item种类：1，
 * item：左上图片，图片右边title，中间分割线，左下新闻来源，右下可单独点击的×。
 * 我的订阅页面：两个栏目：我的订阅  精选订阅。 其中内容
 */
public class Read extends Fragment {

    private static final String ARG_COLUMN_COUNT = "read";
    private ViewPager fragmentReadViewpager;
    private String mColumnCount;
    private ReadRecommendFragment listfragment;
    private List<Fragment> frags = new ArrayList<>();
    private String[] strs = new String[]{"推荐阅读", "我的订阅"};
    ;
    private TabLayout fragmentReadTablayout; //tablayout标题.
    private VPAdapter adapter;

    /**
     * 无参构造方法，注意不用构造方法传值，传值使用newInstance
     */
    public Read() {
    }

    public static Read newInstance(String columnCount) {
        Read fragment = new Read();
        Bundle args = new Bundle();
        args.putString(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getString(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read, container, false);

        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFrags();

        initTablayout();

        initAdapter();


    }

    private void initAdapter() {

        adapter = new VPAdapter(getFragmentManager());
        fragmentReadViewpager.setAdapter(adapter);
    }

    private void initFrags() {//添加fragment
        frags.add(new ReadRecommendFragment());
        frags.add(new ReadSubscriberFragment());
    }

    private void initTablayout() {
        for (int i = 0; i < strs.length; i++) {
            fragmentReadTablayout.addTab(fragmentReadTablayout.newTab().setText(strs[i]).setTag(i));
        }

        fragmentReadTablayout.setBackgroundColor(getResources().getColor(R.color.colorWYRed));
        fragmentReadTablayout.setTabTextColors(getResources().getColor(R.color.colorTextUnSelector), getResources().getColor(R.color.colorTextSelector));
        fragmentReadTablayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorTextSelector));
        fragmentReadTablayout.setSelectedTabIndicatorHeight(2);


        fragmentReadTablayout.setupWithViewPager(fragmentReadViewpager);
    }

    private void initView(View view) {
        fragmentReadTablayout = (TabLayout) view.findViewById(R.id.fragment_read_tablayout);
        fragmentReadViewpager = (ViewPager) view.findViewById(R.id.fragment_read_viewpager);
    }

    private class VPAdapter extends FragmentStatePagerAdapter {

        public VPAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return frags.get(position);
        }

        @Override
        public int getCount() {
            return frags.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strs[position];
        }
    }
}
