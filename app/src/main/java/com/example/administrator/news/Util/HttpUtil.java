package com.example.administrator.news.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.news.Bean.NewsBean;
import com.example.administrator.news.Fragment.BaseFragment;
import com.example.administrator.news.Fragment.SubFragmentFactory;
import com.example.administrator.news.MainActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/4/15.
 */

public class HttpUtil {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();//
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT*2+1;
    private static final int CORE_POOL_SIZE = CPU_COUNT+1;//核心线程数
    private static final long KEEP_ALIVE=10L;//闲置线程超时时间
    private static final ThreadPoolExecutor threadPoolExecutor =  new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
    public static void getNewsBean(final int fragmentType)
    {
        Runnable runnable =new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectivityManager cm = (ConnectivityManager) MainActivity.activityContext
                            .getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(cm!=null) {
                        NetworkInfo n=cm.getActiveNetworkInfo();
                        if(n==null) {
                            throw new IOException();
                        }
                        if (!n.isAvailable()||!n.isConnected()) {
                            Log.v("ssss","为空");
                            throw new IOException();
                        }
                    }
                    else
                    {
                        Log.v("ssss","为空");
                    }
                    HttpURLConnection httpURLConnection = (HttpURLConnection) getUrl(fragmentType).openConnection();
                    httpURLConnection.setConnectTimeout(2000);
                    httpURLConnection.setReadTimeout(2000);
                    httpURLConnection.setRequestMethod("GET");
                    if(httpURLConnection.getResponseCode()==200)
                    {
                        String json = DataUtil.readJsonFromInputStream(httpURLConnection.getInputStream());
                        if(json==null)
                                  {
                                      throw new IOException();
                                  }
                        ArrayList<NewsBean> newsBeens =DataUtil.jsonToNewsBean(json);
                        if(newsBeens!=null)
                        {

                            Object  o = newsBeens;
                            MainActivity.sendMessageToHandler(o, fragmentType);
                        }
                        else
                        {

                            MainActivity.sendMessageToHandler(fragmentType, BaseFragment.INTERNET_ERROR);
                        }
                    }
                    else{
                        Log.v("ssss","sfdf"+httpURLConnection.getResponseCode());
                        MainActivity.sendMessageToHandler(fragmentType, BaseFragment.INTERNET_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("ssss","sfd");
                    MainActivity.sendMessageToHandler(fragmentType, BaseFragment.INTERNET_ERROR);
                }
            }
        };
        threadPoolExecutor.execute(runnable);
    }
     public static void loadImage(final ImageView imageView)
     {
         Runnable runnable = new Runnable() {
             @Override
             public void run() {
                 try {
                     HttpURLConnection httpURLConnection = (HttpURLConnection) new URL((String) imageView.getTag()).openConnection();
                     BitmapImagUnion bimapImagUnion = new BitmapImagUnion();
                     bimapImagUnion.url=(String) imageView.getTag();
                     if( httpURLConnection.getResponseCode()==200)
                    {
                        Bitmap bitmap=DataUtil.readBitmapFromInputStream(httpURLConnection.getInputStream());
                        bimapImagUnion.bitmap=bitmap;
                        bimapImagUnion.imageView=imageView;
                        if(bitmap!=null)
                        {
                            MainActivity.sendMessageToHandler(bimapImagUnion,BaseFragment.BITMAP_DEAL);
                        }
                        else
                        {

                        }
                    }

                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         };
         threadPoolExecutor.execute(runnable);
     }


    private static URL getUrl(int fragmentType)
    {
        String url = "http://v.juhe.cn/toutiao/index?type=%s&key=6c26f38d95a4d72d9a60ea2f8d241147";
        if(fragmentType== SubFragmentFactory.ToutTiao)
        {
            url = String.format(url, "top");
        }
        else if(fragmentType== SubFragmentFactory.SheHui)
        {
            url = String.format(url, "shehui");
        }
        else if(fragmentType== SubFragmentFactory.GuoNei)
        {
            url = String.format(url, "guonei");
        }
        else if(fragmentType== SubFragmentFactory.GuoJi)
        {
            url = String.format(url, "guoji");
        }else if(fragmentType== SubFragmentFactory.YuLe)
        {
            url = String.format(url, "yule");
        }else if(fragmentType== SubFragmentFactory.TiYu)
        {
            url = String.format(url, "tiyu");
        }else if(fragmentType== SubFragmentFactory.JunShi)
        {
            url = String.format(url, "junshi");
        }
        else if(fragmentType== SubFragmentFactory.KeJi)
        {
            url = String.format(url, "keji");
        }
        else if(fragmentType== SubFragmentFactory.CaiJin)
        {
            url = String.format(url, "caijing");
        }
        else if(fragmentType== SubFragmentFactory.ShiShan)
        {
            url = String.format(url, "shishang");
        }

        try {
            URL url1 = new URL(url);
            return url1;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static class BitmapImagUnion
    {
        public Bitmap bitmap;
        public ImageView imageView;
        public String url;
    }
}
