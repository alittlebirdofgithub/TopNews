package com.example.administrator.news.View;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.news.MainActivity;
import com.example.administrator.news.R;
import com.example.administrator.news.VideoActivity;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017-04-25.
 */

public class VideoView extends FrameLayout implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{
    private SurfaceView surfaceView;
    private  MediaPlayer mediaPlayer=null;
    private RelativeLayout pauseLayout;
    private TextView titleView;
    private ImageButton pauseButton;
    private SeekBar seekBar;
    private TextView progressDes;
    private TextView progressTotal;
    private ImageButton fullScreen;
    private Timer timer;
    private TimerTask timeTask;
    private boolean pauseLayoutIsHide=true;
    private Uri uri;
    private boolean isFullScreen=false;
    private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==1)
            progressDes.setText((String) msg.obj);
            else if(msg.what==2)
            {
                pauseLayout.setVisibility(INVISIBLE);
                pauseLayoutIsHide = true;
            }
            return true;
        }
    });


    public VideoView(Context context) {
        super(context);
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void initUI()
    {


        surfaceView = (SurfaceView) findViewById(R.id.video_surface);
        pauseLayout = (RelativeLayout) findViewById(R.id.pause_layout);
        pauseButton = (ImageButton) findViewById(R.id.pause_btn);
        titleView = (TextView) findViewById(R.id.video_tv_title);
        seekBar = (SeekBar) findViewById(R.id.video_seekbar);
        progressDes = (TextView) findViewById(R.id.video_tv_progress);
        progressTotal = (TextView) findViewById(R.id.video_tv_progress_total);
        fullScreen = (ImageButton) findViewById(R.id.video_full_screen);
        pauseButton.setOnClickListener(this);
        surfaceView.setOnClickListener(this);
        fullScreen.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }
    private int autoHideCount=0;
    public void initData(String url,String title) {
        titleView.setText(title);

        uri = Uri.parse(url);
        SurfaceHolder surfaceHolder =surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(final SurfaceHolder holder) {
                MainActivity.log("创建");
                try {
                    if(mediaPlayer==null) {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(getContext(), uri);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.prepare();
                    }//设置mediaPlayer与当前surfaceView关联一起，这里是非全屏
                    mediaPlayer.setDisplay(holder);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                int max=mediaPlayer.getDuration();
                progressTotal.setText(getTime(max));
                seekBar.setMax(max);
                //定时器更新进度条
                timer=new Timer();

                timeTask=new TimerTask() {

                    @Override
                    public void run() {
                        if(mediaPlayer.isPlaying()) {
                            int current = mediaPlayer.getCurrentPosition();
                            seekBar.setProgress(current);
                            handler.obtainMessage(1, getTime(current)).sendToTarget();
                        }//自动隐藏布局隔4*500=两秒
                        if(!pauseLayoutIsHide)
                        {
                            if(autoHideCount>4)
                            {
                                handler.obtainMessage(2,null).sendToTarget();
                            }
                            autoHideCount++;
                        }
                        else
                        {
                            autoHideCount=0;
                        }
                    }
                };

                timer.schedule(timeTask, 0, 500);

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //非全屏状态下销毁MediaPlayer，比如上下移动
                if (mediaPlayer!=null&&mediaPlayer.isPlaying()&&!isFullScreen) {
                    currentPosition=mediaPlayer.getCurrentPosition();
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer=null;
                    timer.cancel();
                    timeTask.cancel();
                    timer=null;
                    timeTask=null;
                }
            }
        });


    }
    public  void againInitData(MediaPlayer media)
    {
  mediaPlayer=media;
    initUI();
        SurfaceHolder surfaceHolder =surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(final SurfaceHolder holder) {
                //mediaPlayer.reset();
                //设置全屏sufrace
                mediaPlayer.setDisplay(holder);
                int max = mediaPlayer.getDuration();
                progressTotal.setText(getTime(max));
                seekBar.setMax(max);
                //定时器更新进度条
                timer = new Timer();

                timeTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (mediaPlayer.isPlaying()) {
                            int current = mediaPlayer.getCurrentPosition();
                            seekBar.setProgress(current);
                            handler.obtainMessage(1, getTime(current)).sendToTarget();
                        }
                        if (!pauseLayoutIsHide) {
                            if (autoHideCount > 4) {
                                handler.obtainMessage(2, null).sendToTarget();
                            }
                            autoHideCount++;
                        } else {
                            autoHideCount = 0;
                        }
                    }
                };

                timer.schedule(timeTask, 0, 500);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mediaPlayer!=null&&mediaPlayer.isPlaying()) {
                    currentPosition=mediaPlayer.getCurrentPosition();
                    //全屏和缩小屏共用一个MediaPlayer所以不能全屏surfaceview销毁时释放mediaplayer
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
                    mediaPlayer=null;
                    timer.cancel();
                    timeTask.cancel();
                    timer=null;
                    timeTask=null;
                }
            }
        });
    }
            public String getTime(int millSeconds)
    {
        int seconds =millSeconds/1000;
        int minutes =seconds/60;
        int second = seconds%60;
        int hour = minutes/60;
        int minute =minutes%60;
        StringBuilder stringBuilder =new StringBuilder();
        if(hour!=0)
        {
            if(hour<10)
            {
                stringBuilder.append("0");
            }
            stringBuilder.append(hour);
            stringBuilder.append(":");
        }
        else
        {
            stringBuilder.append("00:");
        }
        if(minute!=0)
        {
            if(minute<10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(minute);
            stringBuilder.append(":");
        }
        else
        {
            stringBuilder.append("00:");
        }
        if(second!=0)
        {
            if(second<10)
            {
                stringBuilder.append("0");
            }
            stringBuilder.append(second);
        }
        else
        {
            stringBuilder.append("00");
        }
        return stringBuilder.toString();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.video_surface:
                if(pauseLayoutIsHide) {
                    pauseLayout.setVisibility(VISIBLE);
                    pauseLayoutIsHide=false;
                }
                else {
                    pauseLayout.setVisibility(INVISIBLE);
                    pauseLayoutIsHide=true;
                }
                break;
            case R.id.pause_btn:
                if(mediaPlayer.isPlaying())
                mediaPlayer.pause();
                else {
                    mediaPlayer.start();
                    pauseLayout.setVisibility(INVISIBLE);
                    pauseLayoutIsHide=false;
                }
                break;
            case R.id.video_full_screen:
                Intent intent = new Intent(MainActivity.activityContext, VideoActivity.class);
                VideoActivity.mediaPlayer = mediaPlayer;
                isFullScreen=true;
                MainActivity.activityContext.startActivity(intent);
                break;

        }
    }
private int currentPosition;
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              currentPosition = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mediaPlayer.seekTo(currentPosition);
    }

}
