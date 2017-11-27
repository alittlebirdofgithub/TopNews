package com.example.administrator.news.Cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.example.administrator.news.MainActivity;

/**
 * Created by Administrator on 2017/4/21.
 */

public class BitmapMemoryCache implements CacheInterface{
       private LruCache<String,Bitmap> bitmapLruCache = new LruCache<>(30);
    /**
     * 向lruCache存储东西。
     * @param url
     * @param o
     */
    @Override
    public void add(String url,Object o) {

        bitmapLruCache.put(url, (Bitmap) o);
    }

    /**
     * 参数为url
     * @param o
     */
    @Override
    public void remove(Object o) {
             bitmapLruCache.remove((String) o);
    }

    @Override
    public Object get(String url) {
        return bitmapLruCache.get(url);
    }
}
