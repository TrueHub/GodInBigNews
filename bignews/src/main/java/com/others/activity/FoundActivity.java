package com.others.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.others.fragment.FoundWebFragment;
import com.news.activity.R;

public class FoundActivity extends AppCompatActivity {
    private static final String TAG="tmd";
    private FragmentManager manager;
    private FoundWebFragment fwf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_found);
        Intent intent=getIntent();
        String CurrentUrl=intent.getStringExtra("path");
        Log.i(TAG,"CurrentUrl的值"+intent.getStringExtra("path"));
        fwf=new FoundWebFragment();
        Bundle bundle=new Bundle();
        bundle.putString("key",CurrentUrl);
        fwf.setArguments(bundle);
        manager=getSupportFragmentManager();
        manager.beginTransaction().add(R.id.FoundFrameLayout,fwf).commit();
    }
}
