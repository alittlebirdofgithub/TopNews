package com.example.administrator.news.Fragment.FirstPage;

import android.content.Context;
import android.os.Message;

import com.example.administrator.news.Bean.NewsBean;
import com.example.administrator.news.Cache.FileCache;
import com.example.administrator.news.Fragment.BaseFragment;
import com.example.administrator.news.Fragment.FirstPage.FirstPageSubBaseFragment;
import com.example.administrator.news.Fragment.SubFragmentFactory;
import com.example.administrator.news.Util.DefaultCacheUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-04-24.
 */

public class TiYuFragmentFirstPage extends FirstPageSubBaseFragment {
    public static final String TIYU_SAVE_FILE= FileCache.Save_Directory+"/Bean/tiyu.txt";
    public TiYuFragmentFirstPage(Context context) {
        super(context);
    }
    /**
     * 处理请求成功的头条信息，
     */
    @Override
    public void handleMessage(Message msg) {
        //缓存newsbeen到本地文件夹。
        ArrayList<NewsBean> newsBeen = (ArrayList<NewsBean>) msg.obj;
        BaseFragment.defaultCacheUtil.dealAddCache(DefaultCacheUtil.BEAN_FILE,TIYU_SAVE_FILE,newsBeen);
        //如果不相等，说明子Fragment切换过快（当数据发送到handler时,子Fragment改变了），不再是原来请求数据的那个子Fragment。所以不响应更新UI。
        if(msg.what== SubFragmentFactory.TiYu)
            addNewsBeen(newsBeen);
    }
    /**
     * 处理请求失败的头条信息，
     */
    @Override
    public void handleInternetErrorMessage(Message msg) {
        int type = (int) msg.obj;
        if(type==SubFragmentFactory.TiYu)
        {
            ArrayList<NewsBean> arrayList = (ArrayList<NewsBean>) BaseFragment.defaultCacheUtil.dealGetCache(DefaultCacheUtil.BEAN_FILE,TIYU_SAVE_FILE);
            if(arrayList!=null) {
                addNewsBeen(arrayList);
            }
        }
    }
}
