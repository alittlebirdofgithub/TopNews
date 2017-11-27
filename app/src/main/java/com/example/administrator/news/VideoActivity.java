package com.example.administrator.news;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.news.View.VideoView;


/**
 * Created by Administrator on 2017-04-26.
 */

public class VideoActivity  extends AppCompatActivity{
    private VideoView mVideoView;
    public static MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoview_layout);
        mVideoView = (VideoView) findViewById(R.id.video_video);
        mVideoView.setPadding(0,0,0,0);
         mVideoView.againInitData(mediaPlayer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer=null;
    }
}
