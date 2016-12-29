package com.others.fragment;

import android.graphics.Color;
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

import com.news.activity.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/18.
 */
public class MyMessageFragment extends Fragment {
    private TabLayout mFragmentMymessageTablayout;
    private ViewPager mFragmentMymessageViewpager;
    private MyMessageAdapter adapter;
    private String title[]={"评论我的","通知"};
    private ArrayList<Fragment> list=new ArrayList<>();
    private ArrayList<String> DataList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mymessage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        initData();

        initAdapter();
        mFragmentMymessageTablayout.setTabMode(TabLayout.MODE_FIXED);
        mFragmentMymessageTablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mFragmentMymessageTablayout.setSelectedTabIndicatorColor(Color.RED);
       mFragmentMymessageTablayout.setTabTextColors(Color.BLACK, Color.RED);
        mFragmentMymessageTablayout.setupWithViewPager(mFragmentMymessageViewpager);
    }

    private void initData() {

    }

    private void initAdapter() {
        adapter=new MyMessageAdapter(getActivity().getSupportFragmentManager());
        mFragmentMymessageViewpager.setAdapter(adapter);
    }

    private void initView(View view) {

        mFragmentMymessageTablayout = (TabLayout) view.findViewById(R.id.fragment_mymessage_tablayout);

        mFragmentMymessageViewpager = (ViewPager) view.findViewById(R.id.fragment_mymessage_viewpager);

    }
    class MyMessageAdapter extends FragmentStatePagerAdapter{
        public MyMessageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}
