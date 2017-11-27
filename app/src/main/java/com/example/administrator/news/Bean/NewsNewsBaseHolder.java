package com.example.administrator.news.Bean;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.news.Cache.FileCache;
import com.example.administrator.news.Fragment.BaseFragment;
import com.example.administrator.news.Fragment.FirstPage.FirstPageSubBaseFragment;
import com.example.administrator.news.R;
import com.example.administrator.news.Util.DataUtil;
import com.example.administrator.news.Util.DefaultCacheUtil;
import com.example.administrator.news.Util.HttpUtil;

/**
 * Created by Administrator on 2017/4/17.
 */

public class NewsNewsBaseHolder extends NewsBaseHolder{

    private ImageView imageView2;
    private ImageView imageView3;
    public NewsNewsBaseHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.recycle_tv_title);
        describle = (TextView) itemView.findViewById(R.id.recycle_tv_des);
        imageView1 = (ImageView) itemView.findViewById(R.id.recyle_image1);
        imageView2 = (ImageView) itemView.findViewById(R.id.recyle_image2);
        imageView3 = (ImageView) itemView.findViewById(R.id.recyle_image3);


    }



    public void bindViewTwoImage(String url1,String url2)
    {
        bindImageThreeCache(imageView1,url1);
        bindImageThreeCache(imageView2,url2);
        imageView1.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.VISIBLE);
        imageView3.setVisibility(View.GONE);
    }
    public void bindViewThreeImage(String url1,String url2,String url3)
    {
        bindImageThreeCache(imageView1,url1);
        bindImageThreeCache(imageView2,url2);
        bindImageThreeCache(imageView3,url3);
        imageView1.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.VISIBLE);
        imageView3.setVisibility(View.VISIBLE);
    }




}
