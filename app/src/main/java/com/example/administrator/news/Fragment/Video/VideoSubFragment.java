package com.example.administrator.news.Fragment.Video;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.news.Bean.VideoHolder;
import com.example.administrator.news.Fragment.SubBaseFragment;
import com.example.administrator.news.Fragment.SubFragmentFactory;
import com.example.administrator.news.MainActivity;
import com.example.administrator.news.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-04-25.
 */

public abstract class VideoSubFragment extends SubBaseFragment {
    protected static ArrayList<String> mVideoPaths = new ArrayList<>();
    public VideoSubFragment(Context context) {
        super(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.sub_fragment_view,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerAdapter = new VideoAdapter();
        mRecyclerView.setAdapter(mRecyclerAdapter);
        return view;
    }



    class VideoAdapter extends RecyclerView.Adapter<VideoHolder>
    {

        @Override
        public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view =View.inflate(mContext,R.layout.videoview_layout,null);
            VideoHolder videoHolder= new VideoHolder(view);
            return videoHolder;
        }

        @Override
        public void onBindViewHolder(VideoHolder holder, int position) {
             holder.bindUrl(mVideoPaths.get(position));
        }

        @Override
        public int getItemCount() {
            return mVideoPaths.size();
        }
    }
    //没有服务器数据，所以用此方法模拟出20个数据,并查询sd卡中的视频作为模拟
    public void testVideo()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                scanVideoFile();

            }
        }).start();
    }
    private void scanVideoFile()
    {
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return;
        File rootFile = Environment.getExternalStorageDirectory();
        if(rootFile.exists()) {
            ArrayList<File> files = getVideoFiles(rootFile);

            //这里随便结果码,
            MainActivity.sendMessageToHandler(files, SubFragmentFactory.TuiJian);
        }
    }
    public ArrayList<File> getVideoFiles(File rootFile)
    {
        ArrayList<File>videoFiles = new ArrayList<>();
        File[]files=rootFile.listFiles();
        if(files==null)
            return null;
        for(int i=0;i<files.length;i++)
        {
            if(files[i].isDirectory())
            {
                    getVideoFiles(files[i]);
            }
            else
            {
                 String[]names=files[i].getName().split("\\.");
                if(names[names.length-1].equals("mp4")||names[names.length-1].equals("flv"))
                {
                    videoFiles.add(files[i]);
                }
            }
        }
        return videoFiles;
    }
}
