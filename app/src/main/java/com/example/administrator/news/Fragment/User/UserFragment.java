package com.example.administrator.news.Fragment.User;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.administrator.news.Fragment.BaseFragment;
import com.example.administrator.news.R;

/**
 * Created by Administrator on 2017-05-02.
 */

public class UserFragment extends BaseFragment {
    private int zoomViewWidth;
    private float mScaleTimes=2;

    private float zoomViewHeight;
    private float mReplyRatio=0.5f;
    private ScrollView scrollView;
    private int startY;
    private ImageView zoomView;
   private  boolean isScaleing=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user,container,false);
        zoomView = (ImageView) view.findViewById(R.id.user_background);
        scrollView = (ScrollView) view.findViewById(R.id.user_scrollView);
      scrollView.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {
              if(zoomViewWidth<=0||zoomViewHeight<=0)
              {
                  zoomViewWidth = zoomView.getMeasuredWidth();
                  zoomViewHeight = zoomView.getMeasuredHeight();
              }
              switch (event.getAction())
              {

                  case MotionEvent.ACTION_DOWN:
                      startY= (int) event.getRawY();
                      break;
                  case MotionEvent.ACTION_MOVE:
               if(!isScaleing) {
                   float distance =event.getRawY() - startY;
                   if(distance>0)
                   setZoom(distance);
               }

                      break;
                  case MotionEvent.ACTION_UP:

                      isScaleing=true;
                      replyView();
                      break;
              }
              return true;
          }
      });
        return view;
    }

    public UserFragment(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
    }
    /**放大view*/
    private void setZoom(float s) {
        float scaleTimes = (float) ((zoomViewWidth+s)/(zoomViewWidth*1.0));
//        如超过最大放大倍数，直接返回
        if (scaleTimes > mScaleTimes) return;

        ViewGroup.LayoutParams layoutParams = zoomView.getLayoutParams();
        layoutParams.width = (int) (zoomViewWidth + s);
        layoutParams.height = (int)(zoomViewHeight*((zoomViewWidth+s)/zoomViewWidth));
//        设置控件水平居中
        ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - zoomViewWidth) / 2, 0, 0, 0);
        zoomView.setLayoutParams(layoutParams);
    }

    /**回弹*/
    private void replyView() {
        final float distance = zoomView.getMeasuredWidth() - zoomViewWidth;
        // 设置动画
        final ValueAnimator anim = ObjectAnimator.ofFloat(distance, 0.0F).setDuration((long) (distance * mReplyRatio));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setZoom((Float) animation.getAnimatedValue());
                if( ((Float)animation.getAnimatedValue())==distance)
                {
                    isScaleing=false;
                }
            }

        });
        anim.start();
    }
    @Override
    protected boolean dealMessage(Message msg) {
        return true;
    }

    @Override
    protected boolean dealInternetErrorMessage(Message msg) {
        return true;
    }

    @Override
    public void init() {

    }
}
