package com.example.administrator.news.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.news.Cache.FileCache;
import com.example.administrator.news.Fragment.FirstPage.FirstPageSubBaseFragment;
import com.example.administrator.news.MainActivity;
import com.example.administrator.news.R;
import com.example.administrator.news.Util.CacheUtil;
import com.example.administrator.news.Util.DataUtil;
import com.example.administrator.news.Util.DefaultCacheUtil;
import com.example.administrator.news.Util.HttpUtil;

import java.io.File;


/**
 * Created by Administrator on 2017/4/15.
 */

public abstract class BaseFragment extends Fragment{

    //HorizontalScrolview中的LinearLayout
    protected LinearLayout mHorizontalScrollView;
    //子Fragment
    protected SubBaseFragment mSubBaseFragment;
    protected AppCompatActivity mActivity;
    protected int textTitleMargin=8;
    //缓存类
    public static CacheUtil defaultCacheUtil = new DefaultCacheUtil();
    //TextView的id
    protected int[]horizontalTextId;
    //网络请求数据错误
    public static final  int INTERNET_ERROR=-1;
    //处理网络图片请求
    public static final int BITMAP_DEAL=100;
   //新闻点击状况的关键key
    public static final String NEWS_KEY="news_key.txt";

    protected  Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==INTERNET_ERROR)
            {

                //处理网络错误，读取缓存,
            dealInternetErrorMessage(msg);

                return true;
            }
            else if(msg.what==BITMAP_DEAL)
            {
               return  dealBitmapformHttp(msg);
            }
            return dealMessage(msg);
        }


    });



    private boolean dealBitmapformHttp(Message msg)
    {
        HttpUtil.BitmapImagUnion bimapImagUnion = (HttpUtil.BitmapImagUnion) msg.obj;
        //缓存到内存和本地
        String md5Url =  DataUtil.getMD5Name(bimapImagUnion.url);
        defaultCacheUtil.dealAddCache(DefaultCacheUtil.BITMAP_MEMORY,md5Url,bimapImagUnion.bitmap);
        //缓存到本地的时候需要额外判断本地图片是否过多，过多需要删除。
          checkBitmapFileFull();
        defaultCacheUtil.dealAddCache(DefaultCacheUtil.BITMAP_FILE, FileCache.Bitmap_Save_File+"/"+md5Url,bimapImagUnion.bitmap);
        if(bimapImagUnion.url.equals(bimapImagUnion.imageView.getTag())) {
            bimapImagUnion.imageView.setImageBitmap(bimapImagUnion.bitmap);
        }
        else//图片组件url已变,不做处理
        {

        }
        return  true;
    }

    /**
     * 判断Bitmap缓存文件是否过多进行删除
     */
    private void checkBitmapFileFull()
    {
        File file = new File(FileCache.Bitmap_Save_File);
        File subFile =null;
        if(!file.exists())
            return;
        String []subFiles =file.list();
        //这里设置大于50就清空
        if(subFiles.length>=50)
        {
            SharedPreferences sharedPreferences = mActivity.getSharedPreferences(NEWS_KEY, Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().commit();
            for(int i=0;i<subFiles.length;i++) {
                subFile = new File(FileCache.Bitmap_Save_File + "/" + subFiles[i]);
                if(subFile.exists())
                {
                   subFile.delete();

                }
                else
                {
                    MainActivity.log(FileCache.Bitmap_Save_File + "/" + subFiles[i]);
                }
            }
        }
    }
    public void sendMessageToHandler(Object object,int code)
    {

        handler.obtainMessage(code,object).sendToTarget();
    }

    /**
     * 由子Fragment处理请求成功的网络数据
     * @param msg
     * @return
     */
    protected abstract boolean dealMessage(Message msg);

    /**
     * 由子Fragment处理请求失败的情况。
     * @param msg
     * @return
     */
    protected abstract boolean dealInternetErrorMessage(Message msg);
    public BaseFragment(AppCompatActivity appCompatActivity)
    {
        super();
        mActivity = appCompatActivity;
        checkBitmapFileFull();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view,container,false);
        mHorizontalScrollView = (LinearLayout) view.findViewById(R.id.fragment_horizontal);
        //显示进度条
        MainActivity.showProgressBar();
        init();
        return  view;
    }
    public abstract void  init();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity=null;
    }
    public void showTextColor(String text)
    {
        int count = mHorizontalScrollView.getChildCount();
        for(int i=0;i<count;i++)
        {
            TextView textView = (TextView) mHorizontalScrollView.findViewById(horizontalTextId[i]);
            if(text.equals(textView.getText().toString()))
            {
                textView.setTextColor(Color.RED);
            }
            else
            {
                textView.setTextColor(Color.BLACK);
            }
        }
    }
}
