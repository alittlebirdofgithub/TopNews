package com.example.administrator.news;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.administrator.news.Fragment.BaseFragment;
import com.example.administrator.news.Fragment.FirstPage.FirstPageFragment;
import com.example.administrator.news.Fragment.SubFragmentFactory;
import com.example.administrator.news.Fragment.Top.TopFragment;
import com.example.administrator.news.Fragment.User.UserFragment;
import com.example.administrator.news.Fragment.Video.VideoFragment;
import com.example.administrator.news.Util.DataUtil;
import com.example.administrator.news.View.OtherView.MKLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private RadioButton videoBtn;
    private RadioButton topBtn;
    private RadioButton firstPageBtn;
    private RadioButton userBtn;
    private static BaseFragment mFillFragment;
    public static AppCompatActivity activityContext;
    private static FrameLayout rootView;
    private static MKLoader mkLoader;
    public static int displayWidth;
    public static int displayHeight;
    public static int FirstPageFragmentTag=0;
    public static int VideoFragmentTag= SubFragmentFactory.TuiJian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoBtn = (RadioButton) findViewById(R.id.video);
        topBtn = (RadioButton) findViewById(R.id.top);
        firstPageBtn = (RadioButton) findViewById(R.id.firstpage);
        userBtn = (RadioButton) findViewById(R.id.user);
        videoBtn.setOnClickListener(this);
        topBtn.setOnClickListener(this);
        firstPageBtn.setOnClickListener(this);
        userBtn.setOnClickListener(this);
        activityContext=this;
        rootView = (FrameLayout) userBtn.getRootView();
       init();
    }
public static void log(String url)
{
    Toast.makeText(activityContext,url,Toast.LENGTH_LONG).show();
}
    private void init() {
        onClick(firstPageBtn);
        DisplayMetrics displayMetrics= new DisplayMetrics();
        ((WindowManager)(getSystemService(WINDOW_SERVICE))).getDefaultDisplay().getMetrics(displayMetrics);
        displayHeight = displayMetrics.heightPixels;
        displayWidth = displayMetrics.widthPixels;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        if(mFillFragment!=null)
        fm.beginTransaction().remove(mFillFragment).commit();
        switch (v.getId())
        {
            case R.id.video:
                mFillFragment = new VideoFragment(this);
                break;
            case R.id.firstpage:
                mFillFragment = new FirstPageFragment(this);
                break;
            case R.id.top:
                mFillFragment = new TopFragment(this);
                break;
            case R.id.user:
                mFillFragment = new UserFragment(this);
                break;
        }
        fm.beginTransaction().add(R.id.fragment_parent,mFillFragment).commit();
    }
    public static void sendMessageToHandler(Object object,int code)
    {
         mFillFragment.sendMessageToHandler(object,code);
    }
    public static void showProgressBar()
    {
        if(mkLoader!=null)
        {
            dismissProgressBar();
        }
        mkLoader = new MKLoader(activityContext,2, Color.WHITE);
        rootView.addView(mkLoader);
        int dip = DataUtil.dipToPix(50,activityContext);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dip,dip);
        layoutParams.gravity= Gravity.CENTER;
        mkLoader.setLayoutParams(layoutParams);
    }
    public static void dismissProgressBar()
    {
        rootView.removeView(mkLoader);
        mkLoader = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityContext=null;
        mFillFragment=null;
    }
}
