package com.bignews9527.vidio.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.news.activity.R;
import com.bignews9527.vidio.video.Radio;
import com.bignews9527.vidio.video.Video;

import java.util.ArrayList;

/**
 * Created by 1945374040 on 2016/11/10.
 */
public class VideoFragment extends Fragment {

    private ViewPager pager;
    private TabLayout tabLayout;
    private ArrayList<Fragment> pagerList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        initData();

        initAdapter();

        initListener();
    }

    private void initListener() {

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initAdapter() {

        pager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return pagerList.get(position);
            }

            @Override
            public int getCount() {
                return pagerList.size();
            }

        });
        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(0).setText("视频");
        tabLayout.getTabAt(1).setText("电台");
    }

    private void initData() {

        pagerList.add(new Video());
        pagerList.add(new Radio());

    }

    private void initView(View view) {

        pager = (ViewPager) view.findViewById(R.id.fragment_video_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.fragment_video_tablayout);
    }

}
