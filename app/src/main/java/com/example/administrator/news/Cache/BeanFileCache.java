package com.example.administrator.news.Cache;

import android.os.Parcelable;

import com.example.administrator.news.Bean.NewsBean;
import com.example.administrator.news.Util.DataUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/21.
 */

public class BeanFileCache extends FileCache {

    /**
     * 参数url为分类的bean所存目录文件
     *参数o为ArrayList<NewsBean>类型，存到一个文件中
     * @param url
     * @param o
     */
    @Override
    public void add(String url, Object o) {
      super.add(url,o);
        try {
                 DataUtil.newsBeanToJsonByJsonWriter((ArrayList<NewsBean>) o,new FileOutputStream(storeFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回NewsBean对象列表
     * @param url
     * @return
     */
    @Override
    public Object get(String url) {
        super.get(url);
        try {
           ArrayList<NewsBean> newsBean =DataUtil.jsonToNewsBeanByJsonReader(new FileInputStream(storeFile));
            return newsBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
