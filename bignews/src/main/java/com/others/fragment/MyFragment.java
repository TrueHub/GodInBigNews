package com.others.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.others.activity.MySetActivity;
import com.news.activity.R;

/**
 * Created by Administrator on 2016/11/16.
 */
public class MyFragment extends Fragment {
    private TextView mMySet;
    private ImageView mMyIcon;
    private TextView mMyLogin;
    private RadioButton mMyRead;
    private RadioButton mMyCollect;
    private RadioButton mMyComment;
    private RadioButton mMyGod;
    private TextView mMyMessage;
    private TextView mMyGoldshop;
    private TextView mMyMission;
    private TextView mMyPocket;
    private TextView mMyEmail;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        initListener();
    }

    private void initListener() {
        mMySet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MySetActivity.class);
                intent.putExtra("tag","1");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
        });
        mMyRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MySetActivity.class);
                intent.putExtra("tag","2");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
        });
        mMyMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MySetActivity.class);
                intent.putExtra("tag","3");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
        });
        mMyGoldshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MySetActivity.class);
                intent.putExtra("tag","4");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
        });
        mMyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MySetActivity.class);
                intent.putExtra("tag","5");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
        });
        mMyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MySetActivity.class);
                intent.putExtra("tag","6");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
        });
    }

    private void initView(View view) {

        mMySet = (TextView) view.findViewById(R.id.my_set);
        mMyIcon = (ImageView) view.findViewById(R.id.my_icon);
        mMyLogin = (TextView) view.findViewById(R.id.my_login);
        mMyRead = (RadioButton) view.findViewById(R.id.my_read);
        mMyCollect = (RadioButton) view.findViewById(R.id.my_collect);
        mMyComment = (RadioButton) view.findViewById(R.id.my_comment);
        mMyGod = (RadioButton) view.findViewById(R.id.my_god);
        mMyMessage = (TextView) view.findViewById(R.id.my_message);
        mMyGoldshop = (TextView) view.findViewById(R.id.my_goldshop);
        mMyMission = (TextView) view.findViewById(R.id.my_mission);
        mMyPocket = (TextView) view.findViewById(R.id.my_pocket);
        mMyEmail = (TextView) view.findViewById(R.id.my_email);
    }
}
