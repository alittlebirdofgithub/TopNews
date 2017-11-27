package com.example.administrator.news.Fragment.Top;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.news.Bean.TopHolder;
import com.example.administrator.news.Fragment.BaseFragment;
import com.example.administrator.news.Fragment.SubFragmentFactory;
import com.example.administrator.news.MainActivity;
import com.example.administrator.news.R;
import com.example.administrator.news.Util.DefaultCacheUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-04-29.
 */

public class TopFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private RecycleAdpater mRecycleAdapter;
    private ArrayList<String>mImagePath = new ArrayList<>();
    @Override
    protected boolean dealMessage(Message msg) {
        if(msg.what==SubFragmentFactory.Top)
        {
            ArrayList<File> files = (ArrayList<File>) msg.obj;
            String path;
            FileInputStream fileInputStream=null;
            File file;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            for(int i=0;i<files.size();i++) {
                file = files.get(i);
                path = file.getAbsolutePath();
                try {
                    fileInputStream = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if(fileInputStream==null)
                    MainActivity.log("fileinputstream为空");

                if(bitmap==null)
                {
                    MainActivity.log("bitmap为空");
                    return true;
                }
                defaultCacheUtil.dealAddCache(DefaultCacheUtil.BITMAP_MEMORY, path, bitmap);
                mImagePath.add(path);
            }
            mRecycleAdapter.notifyDataSetChanged();
            MainActivity.dismissProgressBar();
        }

        return true;
    }

    @Override
    protected boolean dealInternetErrorMessage(Message msg) {
        return true;
    }

    public TopFragment(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_top,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.top_recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecycleAdapter = new RecycleAdpater();
        mRecyclerView.setAdapter(mRecycleAdapter);
        init();
        MainActivity.showProgressBar();
        return view;
    }
  //初始化模仿数据
    @Override
    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                scanImageFile();
            }
        }).start();
    }
    private void scanImageFile()
    {
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return;
        File rootFile = Environment.getExternalStorageDirectory();
        if(rootFile.exists()) {
            ArrayList<File> files = getImageFiles(rootFile);

            MainActivity.sendMessageToHandler(files, SubFragmentFactory.Top);
        }

    }
    public ArrayList<File> getImageFiles(File rootFile)
    {
        ArrayList<File>videoFiles = new ArrayList<>();
//        File[]files=rootFile.listFiles();
//
//        for(int i=0;i<files.length;i++)
//        {
//            if(files[i].isDirectory())
//            {
//                getImageFiles(files[i]);
//            }
//            else
//            {
//                String[]names=files[i].getName().split("\\.");
//                MainActivity.sendMessageToHandler(names[names.length-1],40);
//                if(names[names.length-1].equals("jpg")||names[names.length-1].equals("png"))
//                {
//                    videoFiles.add(files[i]);
//                }
//            }
//        }
        rootFile = new File(Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera");
        File[]files=rootFile.listFiles();
        if(files!=null) {
            for (int i = 0; i < files.length; i++)
                videoFiles.add(files[i]);
        }
        return videoFiles;
    }
    class RecycleAdpater extends RecyclerView.Adapter<TopHolder>
    {
      private String[] initImagesUrl(int count)
      {
          String []url = new String[count];
          for(int i=0;i<count;i++)
          {
              url[i]=mImagePath.get(i);
          }
          return url;
      }
        @Override
        public TopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TopHolder topHolder=null;

            View view =View.inflate(MainActivity.activityContext,R.layout.top_recycle_holder,null);
            switch(viewType)
            {
                case 1:
                    topHolder = new TopHolder(view,initImagesUrl(1));
                    break;
                case 2:
                    topHolder = new TopHolder(view,initImagesUrl(2));
                    break;
                case 3:
                    topHolder = new TopHolder(view,initImagesUrl(3));
                    break;
                case 4:
                    topHolder = new TopHolder(view,initImagesUrl(4));
                    break;
                case 5:
                    topHolder = new TopHolder(view,initImagesUrl(5));
                    break;
                case 6:
                    topHolder = new TopHolder(view,initImagesUrl(6));
                    break;
                case 7:
                    topHolder = new TopHolder(view,initImagesUrl(7));
                    break;
                case 8:
                    topHolder = new TopHolder(view,initImagesUrl(8));
                    break;
                case 9:
                    topHolder = new TopHolder(view,initImagesUrl(9));
                    break;
            }
            return topHolder;
        }

        @Override
        public int getItemViewType(int position) {
            switch (position%9)
            {
                case 0:
                    return 1;
                case 1:
                    return 2;
                case 2:
                    return 3;
                case 3:
                    return 4;
                case 4:
                    return 5;
                case 5:
                    return 6;
                case 6:
                    return 7;
                case 7:
                    return 8;
                case 8:
                    return 9;
            }
            return super.getItemViewType(position);
        }

        @Override
        public void onBindViewHolder(TopHolder holder, int position) {
        // holder.flushGridView(initImagesUrl(getItemViewType(position)));
        }

        @Override
        public int getItemCount() {
            return mImagePath.size();
        }
    }
}
