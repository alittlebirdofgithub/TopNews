package com.example.administrator.news.View.OtherView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.news.R;
import com.example.administrator.news.View.OtherView.callback.InvalidateListener;
import com.example.administrator.news.View.OtherView.type.LoaderView;
import com.example.administrator.news.View.OtherView.util.LoaderGenerator;


/**
 * Created by Tuyen Nguyen on 2/5/17.
 */

public class MKLoader extends View implements InvalidateListener {
  private LoaderView loaderView;

  public MKLoader(Context context) {
    super(context);
    initialize(context, null, 0);
  }

  public MKLoader(Context context, int mk_type, int color) {
    super(context);
    initialize(context, null, 0);
    loaderView = LoaderGenerator.generateLoaderView(mk_type);
    loaderView.setColor(color);
    loaderView.setInvalidateListener(this);
  }

  public MKLoader(Context context, AttributeSet attrs) {
    super(context, attrs);
    initialize(context, attrs, 0);
  }

  public MKLoader(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initialize(context, attrs, defStyleAttr);
  }

  private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
      TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MKLoader);
      int loaderType = typedArray.getInt(R.styleable.MKLoader_mk_type, -1);
      loaderView = LoaderGenerator.generateLoaderView(loaderType);
      loaderView.setColor(typedArray.getColor(R.styleable.MKLoader_mk_color, Color.parseColor("#ffffff")));
      typedArray.recycle();
    loaderView.setInvalidateListener(this);
  }


  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    final int measuredWidth = resolveSize(loaderView.getDesiredWidth(), widthMeasureSpec);
    final int measuredHeight = resolveSize(loaderView.getDesiredHeight(), heightMeasureSpec);

    setMeasuredDimension(measuredWidth, measuredHeight);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    loaderView.setSize(getWidth(), getHeight());
    loaderView.initializeObjects();
    loaderView.setUpAnimation();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    loaderView.draw(canvas);
  }

  @Override
  public void reDraw() {
    invalidate();
  }
}