package com.example.administrator.news.Fragment.FirstPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.news.Bean.NewsBean;
import com.example.administrator.news.Bean.NewsNewsBaseHolder;
import com.example.administrator.news.Bean.NewsNewsBaseHolder2;
import com.example.administrator.news.Bean.NewsBaseHolder;
import com.example.administrator.news.Fragment.BaseFragment;
import com.example.administrator.news.Fragment.SubBaseFragment;
import com.example.administrator.news.MainActivity;
import com.example.administrator.news.R;
import com.example.administrator.news.WebActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/15.
 */

public abstract class FirstPageSubBaseFragment extends SubBaseFragment {

    protected static ArrayList<NewsBean> mNewsBeen= new ArrayList<>();


    public FirstPageSubBaseFragment(Context context)
    {
        super(context);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.sub_fragment_view,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerAdapter = new RecycleAdapter();
        mRecyclerView.setAdapter(mRecyclerAdapter);
        return view;
    }
    public  void addNewsBeen(ArrayList<NewsBean> newsBeen)
    {
        mNewsBeen.clear();
        mNewsBeen.addAll(newsBeen);
        mRecyclerAdapter.notifyDataSetChanged();
        MainActivity.dismissProgressBar();
    }


    class RecycleAdapter extends RecyclerView.Adapter<NewsBaseHolder>
    {
        @Override
        public NewsBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            NewsBaseHolder newsBaseHolder =null;
            View view =null;
            if(viewType==1) {
                view = View.inflate(mContext,R.layout.recycleview_replcae_holder,null);
                newsBaseHolder = new NewsNewsBaseHolder2(view);
            }
            else
            {
                view = View.inflate(mContext,R.layout.recycleview_holder,null);
                newsBaseHolder = new NewsNewsBaseHolder(view);
            }

            return newsBaseHolder;
        }

        @Override
        public void onBindViewHolder(NewsBaseHolder holder, int position) {
           int type = getItemViewType(position);
            NewsBean newsBean = mNewsBeen.get(position);
            switch (type)
            {
                case 1:
                    ((NewsNewsBaseHolder2)holder).bindViewOneImage(newsBean.pics[0]);
                    break;
                case 2:
                    ((NewsNewsBaseHolder) holder).bindViewTwoImage(newsBean.pics[0],newsBean.pics[1]);
                    break;
                case 3:
                    ((NewsNewsBaseHolder) holder).bindViewThreeImage(newsBean.pics[0],newsBean.pics[1],newsBean.pics[2]);
                    break;
            }
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(BaseFragment.NEWS_KEY,Context.MODE_PRIVATE);
            holder.bindViewText(newsBean.title,newsBean.author_name+"   "+newsBean.date);
            holder.setTextClickState(sharedPreferences.getBoolean(newsBean.uniqueKey,false));
        }

        @Override
        public int getItemViewType(int position) {
            return mNewsBeen.get(position).pics.length;
        }

        @Override
        public int getItemCount() {
            return mNewsBeen.size();
        }


    }

    /**
     * 提供给ViewHolder的ItemView跳到WebView
     * @param position
     */
    public static void startWebView(int position)
    {
        Intent intent = new Intent(MainActivity.
        activityContext, WebActivity.class);
        NewsBean newsBean = mNewsBeen.get(position);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(BaseFragment.NEWS_KEY,Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(newsBean.uniqueKey,true).commit();

        intent.putExtra("url",newsBean.url);
        MainActivity.activityContext.startActivity(intent);
    }
}
