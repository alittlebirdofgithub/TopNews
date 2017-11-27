package com.example.administrator.news.Cache;

/**
 * Created by Administrator on 2017/4/21.
 */

public interface CacheInterface {
     void add(String url,Object o);
    void remove(Object o);
    Object get(String url);
}
