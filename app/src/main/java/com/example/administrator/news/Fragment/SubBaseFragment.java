package com.example.administrator.news.Fragment;

import android.content.Context;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;


/**
 * Created by Administrator on 2017-04-25.
 */

public abstract class SubBaseFragment extends Fragment {
    protected RecyclerView mRecyclerView;
    protected static Context mContext;
    protected RecyclerView.Adapter mRecyclerAdapter;
    public SubBaseFragment(Context context)
    {
        mContext =context;
    }
    /**
     * 处理相应Fragment请求Bean数据的成功的网络请求，必须由具体Fragment实现。
     * @param msg
     */
    public abstract void handleMessage(Message msg);

    /**
     * 处理相应Fragment请求Bean数据的失败的情况,由具体Fragment实现
     */
    public abstract void handleInternetErrorMessage(Message msg);
}
