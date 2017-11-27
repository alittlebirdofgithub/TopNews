package com.example.administrator.news.Fragment.Video;

import android.content.Context;
import android.os.Message;

import com.example.administrator.news.Fragment.SubFragmentFactory;
import com.example.administrator.news.MainActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-04-25.
 */

public class TuiJianFragment extends VideoSubFragment {
    public TuiJianFragment(Context context) {
        super(context);
    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.what== SubFragmentFactory.TuiJian)
        {
            ArrayList<File>file = (ArrayList<File>) msg.obj;
            if(file==null)
            {
                MainActivity.log("SD卡中未找到视频");
            }
            else {
                mVideoPaths.clear();
                String s = file.get(0).getAbsolutePath();
                for (int i = 0; i < 20; i++) {
                    mVideoPaths.add(s);
                }
                mRecyclerAdapter.notifyDataSetChanged();
            }
                    MainActivity.dismissProgressBar();
        }
    }

    @Override
    public void handleInternetErrorMessage(Message msg) {

    }
}
