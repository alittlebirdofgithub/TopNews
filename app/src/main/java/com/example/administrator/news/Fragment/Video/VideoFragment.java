package com.example.administrator.news.Fragment.Video;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.news.Fragment.BaseFragment;
import com.example.administrator.news.Fragment.SubFragmentFactory;
import com.example.administrator.news.MainActivity;
import com.example.administrator.news.R;
import com.example.administrator.news.Util.DataUtil;
import com.example.administrator.news.Util.HttpUtil;

/**
 * Created by Administrator on 2017-04-25.
 */

public class VideoFragment extends BaseFragment implements View.OnClickListener {


    public VideoFragment(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    protected boolean dealMessage(Message msg) {
        mSubBaseFragment.handleMessage(msg);
        return true;
    }

    @Override
    protected boolean dealInternetErrorMessage(Message msg) {
        mSubBaseFragment.handleInternetErrorMessage(msg);
        return true;
    }



    @Override
    public void init() {
        String[]strings = SubFragmentFactory.videoSubFragmentTitleDes;
        TextView textView;
        LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int pix = DataUtil.dipToPix(textTitleMargin,mActivity);
        layoutParams.setMargins(pix,0,pix,0);
        horizontalTextId = new int[strings.length];
        String match = SubFragmentFactory.getTitleAccordType(MainActivity.VideoFragmentTag);
        TextView tuijian =null;
        for(int i=0;i<strings.length;i++) {
            textView  = new TextView(mActivity);
            horizontalTextId[i] = DataUtil.getUnDifferienceViewID();
            textView.setText(strings[i]);
            textView.setLayoutParams(layoutParams);
            textView.setTextSize(20);
            textView.setId(horizontalTextId[i]);
            textView.setOnClickListener(this);
            mHorizontalScrollView.addView(textView);
            if(strings[i].equals(match))
            {
                tuijian=textView;
                tuijian.setTextColor(Color.RED);
            }

        }
     onClick(tuijian);
        ((VideoSubFragment)mSubBaseFragment).testVideo();
    }


    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        if(mSubBaseFragment !=null)
            fragmentManager.beginTransaction().remove(mSubBaseFragment).commit();
        if(v instanceof TextView)
        {
            String text= ((TextView)v).getText().toString();
            int type =SubFragmentFactory.getTypeAccordTitle(text);
            MainActivity.VideoFragmentTag =type;
            mSubBaseFragment = SubFragmentFactory.createSubBaseFragment(type,mActivity);
            showTextColor(text);
        }
        fragmentManager.beginTransaction().add(R.id.subfragment_parent, mSubBaseFragment).commit();
    }
}
