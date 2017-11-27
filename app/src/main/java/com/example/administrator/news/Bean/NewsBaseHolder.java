package com.example.administrator.news.Bean;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.administrator.news.Cache.FileCache;
import com.example.administrator.news.Fragment.BaseFragment;
import com.example.administrator.news.Fragment.FirstPage.FirstPageSubBaseFragment;
import com.example.administrator.news.R;
import com.example.administrator.news.Util.DataUtil;
import com.example.administrator.news.Util.DefaultCacheUtil;
import com.example.administrator.news.Util.HttpUtil;

/**
 * Created by Administrator on 2017-04-25.
 */

public abstract class NewsBaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    protected TextView title;
    protected   TextView describle;
    protected ImageView imageView1;
    public NewsBaseHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }
    public void setTextClickState(boolean isClick)
    {
        if(isClick)
        {
            title.setTextColor(Color.GRAY);
            describle.setTextColor(Color.GRAY);
        }
        else
        {
            int color = Color.parseColor("#000000");
            title.setTextColor(color);
            describle.setTextColor(color);
        }
    }


    public void bindViewText(String title,String des)
    {
        this.title.setText(title);
        describle.setText(des);
    }
   protected void bindImageThreeCache(ImageView imageView, String url)
    {
        imageView.setTag(url);
        String uri = DataUtil.getMD5Name(url);
        Bitmap bitmap=null;
        bitmap = (Bitmap) BaseFragment.defaultCacheUtil.dealGetCache(DefaultCacheUtil.BITMAP_MEMORY,uri);
        if(bitmap!=null)
        {
            imageView.setImageBitmap(bitmap);
            BaseFragment.defaultCacheUtil.dealAddCache(DefaultCacheUtil.BITMAP_FILE,uri,bitmap);
        }
        else {
            bitmap = (Bitmap) BaseFragment.defaultCacheUtil.dealGetCache(DefaultCacheUtil.BITMAP_FILE, FileCache.Bitmap_Save_File + "/" +uri);

            if(bitmap!=null)
            {
                imageView.setImageBitmap(bitmap);
                BaseFragment.defaultCacheUtil.dealAddCache(DefaultCacheUtil.BITMAP_MEMORY,uri,bitmap);
            }
            else
            {
                HttpUtil.loadImage(imageView);
            }
        }

    }
    @Override
    public void onClick(View v) {
        title.setTextColor(Color.GRAY);
        describle.setTextColor(Color.GRAY);
        FirstPageSubBaseFragment.startWebView(getLayoutPosition());
    }
}
