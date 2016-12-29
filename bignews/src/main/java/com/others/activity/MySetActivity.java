package com.others.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.others.fragment.CalendarFragment;
import com.others.fragment.MyGoldShopFragment;
import com.others.fragment.MyLoginFragment;
import com.others.fragment.MyMessageFragment;
import com.others.fragment.MySetFragment;
import com.news.activity.R;

public class MySetActivity extends AppCompatActivity {
    private int tag;

    private FragmentManager manager;
    private FragmentTransaction ft;
    private MySetFragment mySetFragment;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fragment_set);
        manager=getSupportFragmentManager();
        ft=manager.beginTransaction();
        Intent intent=getIntent();
        tag=Integer.parseInt(intent.getStringExtra("tag"));
        actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        switch (tag){
            case 1:
                mySetFragment=new MySetFragment();
               ft.replace(R.id.myset_frame,mySetFragment).commit();
                actionBar.setTitle("设置");
                break;
            case 2:
                ft.replace(R.id.myset_frame,new CalendarFragment()).commit();
                actionBar.setTitle("阅读");
                break;
            case 3:
                ft.replace(R.id.myset_frame,new MyMessageFragment()).commit();
                actionBar.setTitle("消息");

                break;
            case 4:
                ft.replace(R.id.myset_frame,new MyGoldShopFragment()).commit();
                actionBar.setTitle("金币商城");
                break;
            case 5:
                ft.replace(R.id.myset_frame,new MyLoginFragment()).commit();
                actionBar.setTitle("登录");
                break;

            case 6:
                ft.replace(R.id.myset_frame,new MyLoginFragment()).commit();
                actionBar.setTitle("登录");
                break;




        }



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
               this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
