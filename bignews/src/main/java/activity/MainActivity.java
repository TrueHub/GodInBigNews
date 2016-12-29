package activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.others.fragment.FoundFragment;
import com.others.fragment.MyFragment;
import com.bignews9527.vidio.fragment.VideoFragment;
import com.read.fragment.Read;
import com.news.activity.R;
import com.news.fragment.News;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mMainactivityActivityGradiogrounp;
    private FragmentManager manager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment LastFragment;
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        toast = Toast.makeText(this,"再次返回退出",Toast.LENGTH_SHORT);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        //实现沉浸式状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //初始化类控件
        initView();
        initData();
        //radiogrounp设置checked监听
        initListener();
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.mainactivity_activity_framlayout,fragments.get(0)).commit();
        LastFragment = fragments.get(0);
    }

    private void initData() {
        fragments.add(new News());
        fragments.add(new Read());
        fragments.add(new VideoFragment());
        fragments.add(new FoundFragment());
        fragments.add(new MyFragment());
    }

    @Override
    public void onBackPressed() {

        if(toast.getView().getParent() == null){
            toast.show();
        }else {
            super.onBackPressed();
        }
    }

    private void initListener() {
        mMainactivityActivityGradiogrounp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radButTag = Integer.parseInt(((RadioButton) findViewById(checkedId)).getTag().toString());
                if(!fragments.get(radButTag).isAdded()){
                    manager.beginTransaction().add(R.id.mainactivity_activity_framlayout,fragments.get(radButTag)).commit();
                }else {
                    manager.beginTransaction().show(fragments.get(radButTag)).commit();
                }

                manager.beginTransaction().hide(LastFragment).commit();
                LastFragment = fragments.get(radButTag);
            }
        });
    }

    private void initView() {
        mMainactivityActivityGradiogrounp = (RadioGroup) findViewById(R.id.mainactivity_activity_gradiogrounp);
    }
}
