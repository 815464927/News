package com.song.news.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.song.news.R;
import com.song.news.base.BaseActivity;
import com.song.news.utils.LogUtils;

public class WebViewActivity extends BaseActivity {

    private WebView mVebView;
    private ProgressBar mProgressBar;
    private TextView mTitle;
    private Toolbar mToolBar;
    public static final String webUrl = "webUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        initToolBar();
        InitData();
    }

    private void initView() {
        mVebView = (WebView) findViewById(R.id.wv_activity);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mTitle = (TextView) findViewById(R.id.public_title);
        mToolBar = (Toolbar) findViewById(R.id.toolBar);
    }

    private void InitData() {
        String mUrl = getIntent().getStringExtra(webUrl);

        WebSettings webSettings = mVebView.getSettings();
        //支持JS
        webSettings.setJavaScriptEnabled(true);
        //允许使用数据库api
        webSettings.setDomStorageEnabled(true);
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //支持变焦
        webSettings.setSupportZoom(true);
        //支持缩放
        webSettings.setBuiltInZoomControls(true);
        //支持内容重新布局(4种)
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        //设置进度条
        mVebView.setWebChromeClient(new webClient());
        //设置webVewclient 不调用系统浏览器 在本webView中显示
        mVebView.setWebViewClient(new mWebViewClient());

        mVebView.loadUrl(mUrl);
        showDialog();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(mVebView.canGoBack()){
                mVebView.goBack();
            }else{
                finish();
                overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
            }
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
        return true;
    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    class webClient extends WebChromeClient {
        //设置进度条
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100){
                mProgressBar.setVisibility(View.INVISIBLE);
            }else{
                if(View.INVISIBLE == mProgressBar.getVisibility()){
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            String t = mVebView.getTitle();
            mTitle.setText(t == null ? "":t);
            super.onReceivedTitle(view, title);
        }
    }

    //控制新的连接在当前WebView中打开
    class mWebViewClient extends WebViewClient {

        //网页开始加载
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LogUtils.debug("onStart",url);
            super.onPageStarted(view, url, favicon);
        }
        //网页加载完毕
        @Override
        public void onPageFinished(WebView view, String url) {
            closeDialog();
            super.onPageFinished(view, url);
        }
    }
}
