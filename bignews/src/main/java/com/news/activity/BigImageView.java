package com.news.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.news.bean.ATT03ClickBigImageBean;
import com.news.fragment.NewsAllBigImage;
import com.news.utils.MyCustomRequest;

import java.util.ArrayList;

public class BigImageView extends AppCompatActivity {
    private String url;
    private ViewPager viepager;
    private ATT03ClickBigImageBean bean;
    private RequestQueue queue;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private MyAdapter adapter;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bigimage_view);
        queue = Volley.newRequestQueue(this);
        initView();
        setUrl();
        title = getIntent().getStringExtra("title");
        initData();
        initAdapter();
    }

    private void setUrl() {
        String[] t = getIntent().getStringExtra("url").split("\\|");
        String str = "http://c.m.163.com/photo/api/set/YYY/XXX.json".replace("XXX", t[1]);
        url = str.replace("YYY", t[0].substring(4, 8));
    }

    private void initData() {
        MyCustomRequest request = new MyCustomRequest<ATT03ClickBigImageBean>(
                url, ATT03ClickBigImageBean.class,
                new Response.Listener<ATT03ClickBigImageBean>() {
                    @Override
                    public void onResponse(ATT03ClickBigImageBean response) {
                        bean = response;
                        for (int i = 0; i < bean.getPhotos().size(); i++) {
                            fragments.add(NewsAllBigImage.getInstance(
                                    bean.getPhotos().get(i).getImgurl(),
                                    title, (i + 1) + "/" + bean.getPhotos().size(),
                                    bean.getPhotos().get(i).getNote()
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, null
        );
        queue.add(request);
    }


    private void initAdapter() {
        adapter = new MyAdapter(getSupportFragmentManager());
        viepager.setAdapter(adapter);
    }


    private void initView() {
        viepager = (ViewPager) findViewById(R.id.viepager_activity_bigimageview);
    }

    class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }


        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    class m extends PagerAdapter{
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

    }
}
