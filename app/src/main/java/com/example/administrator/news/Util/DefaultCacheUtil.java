package com.example.administrator.news.Util;


import com.example.administrator.news.Cache.BeanFileCache;
import com.example.administrator.news.Cache.BitmapFileCache;
import com.example.administrator.news.Cache.BitmapMemoryCache;
import com.example.administrator.news.Cache.CacheInterface;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/21.
 */

public class DefaultCacheUtil extends CacheUtil{
    public static  int BEAN_FILE;
    public static int BITMAP_MEMORY;
    public static int BITMAP_FILE;
    public DefaultCacheUtil()
{
    BEAN_FILE = registCacheInterface(new BeanFileCache());
    BITMAP_MEMORY = registCacheInterface(new BitmapMemoryCache());
    BITMAP_FILE = registCacheInterface(new BitmapFileCache());
}


}
