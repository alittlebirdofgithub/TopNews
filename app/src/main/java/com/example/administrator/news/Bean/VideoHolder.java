package com.example.administrator.news.Bean;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.administrator.news.MainActivity;
import com.example.administrator.news.R;
import com.example.administrator.news.Util.DataUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Created by Administrator on 2017-04-25.
 */

public class VideoHolder extends RecyclerView.ViewHolder{
    //private VideoView mSurfaceView;
    //private MediaPlayer mediaPlayer;
   // private MediaController mediaController;
      private com.example.administrator.news.View.VideoView videoView;
    public VideoHolder(View itemView) {
        super(itemView);
//        mSurfaceView = (VideoView) itemView.findViewById(R.id.holder_surface);
//        mediaController = new MediaController(MainActivity.activityContext);
//        mSurfaceView.setMediaController(mediaController);
          videoView = (com.example.administrator.news.View.VideoView) itemView.findViewById(R.id.video_video);
          FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,DataUtil.dipToPix(240,MainActivity.activityContext));
          videoView.setLayoutParams(layoutParams);
          videoView.initUI();
    }
    public void bindUrl(String url)
    {

            videoView.initData(url,"测试");

    }




}
