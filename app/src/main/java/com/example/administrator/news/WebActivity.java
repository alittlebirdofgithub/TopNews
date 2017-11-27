package com.example.administrator.news;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.administrator.news.Util.DataUtil;
import com.example.administrator.news.View.OtherView.MKLoader;

import static com.example.administrator.news.MainActivity.activityContext;

/**
 * Created by Administrator on 2017/4/24.
 */

public class WebActivity extends AppCompatActivity {
    private WebView mWebView;
    private MKLoader mkLoader;
    private FrameLayout rootView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = (WebView) findViewById(R.id.web_view);
        rootView = (FrameLayout) mWebView.getRootView();
        String url = getIntent().getStringExtra("url");
        MainActivity.log(url);
        mWebView.loadUrl(url);
        //得到webView设置
        WebSettings webSettings = mWebView.getSettings();
        //mWebView.goBack();浏览器回退到上一页面
        //mWebView.goForward();浏览器前进到下一页面
        WebChromeClient webChromeClient = new WebChromeClient();
        mWebView.setWebViewClient(new WebViewClient(){
            //网页开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
               showProgressBar();
            }

            //网页结束加载
            @Override
            public void onPageFinished(WebView view, String url) {
                dismissProgressBar();
                super.onPageFinished(view, url);
            }

        });
        webSettings.setJavaScriptEnabled(true);//设置支持javascript
        webSettings.setBuiltInZoomControls(true);//显示缩放按钮(wap网页不支持)
        webSettings.setUseWideViewPort(true);//设置双击释放(wap网页不支持,wap已经做了适配)
    }
    public  void showProgressBar()
    {
        if(mkLoader!=null)
        {
            dismissProgressBar();
        }
        mkLoader = new MKLoader(activityContext,2, Color.WHITE);
        rootView.addView(mkLoader);
        int dip = DataUtil.dipToPix(50,activityContext);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dip,dip);
        layoutParams.gravity= Gravity.CENTER;
        mkLoader.setLayoutParams(layoutParams);
    }
    public  void dismissProgressBar()
    {
        rootView.removeView(mkLoader);
        mkLoader = null;
    }
}
