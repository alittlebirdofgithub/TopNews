package com.example.administrator.news.Util;

import com.example.administrator.news.Cache.CacheInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2017/4/21.
 */

public abstract class CacheUtil {
    public static int autoAdd=0;
    protected HashMap<Integer,CacheInterface> mCaches = new HashMap<>();
    public int registCacheInterface(CacheInterface cacheInterface)
    {
        autoAdd++;
        mCaches.put(autoAdd,cacheInterface);
        return autoAdd;
    }
    public void unRegistCacheInterface(int type)
    {
        mCaches.remove(type);
    }

    /**
     * 如果是存Bean文件夹 url是存储路径,o是newsbean列表,存bitmap,url是图片网络地址,o是bitmap，存
     * @param type 为缓存处理类型
     * @param url
     * @param o
     */
    public  void dealAddCache(int type,String url,Object o)
    {
        if(mCaches.get(type)!=null)
        mCaches.get(type).add(url,o);
    }
    public Object dealGetCache(int type,String url)
    {
       return mCaches.get(type).get(url);
    }
}
