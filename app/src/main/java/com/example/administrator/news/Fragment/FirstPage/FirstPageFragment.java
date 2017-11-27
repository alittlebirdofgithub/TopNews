package com.example.administrator.news.Fragment.FirstPage;

import android.graphics.Color;
import android.os.Message;
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
 * Created by Administrator on 2017/4/15.
 */

public class FirstPageFragment extends BaseFragment implements View.OnClickListener {


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


    public FirstPageFragment(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void init() {
        String[]strings = SubFragmentFactory.firstpageSubFragmentTitleDes;
        TextView textView;
        LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int pix = DataUtil.dipToPix(textTitleMargin,mActivity);
        layoutParams.setMargins(pix,0,pix,0);
        horizontalTextId = new int[strings.length];
        TextView toutiao =null;
        String match = SubFragmentFactory.getTitleAccordType(MainActivity.FirstPageFragmentTag);
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
                toutiao=textView;
                toutiao.setTextColor(Color.RED);
            }
        }
      onClick(toutiao);
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
            MainActivity.FirstPageFragmentTag = type;
            mSubBaseFragment = SubFragmentFactory.createSubBaseFragment(type,mActivity);
            HttpUtil.getNewsBean(type);
            showTextColor(text);
        }
        fragmentManager.beginTransaction().add(R.id.subfragment_parent, mSubBaseFragment).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler=null;
    }
}
