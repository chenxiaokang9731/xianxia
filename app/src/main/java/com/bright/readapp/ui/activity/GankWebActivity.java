package com.bright.readapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.bright.readapp.R;
import com.bright.readapp.ui.base.BaseActivity;
import com.bright.readapp.ui.presenter.GankWebPresenter;
import com.bright.readapp.ui.view.IGankWebView;

import butterknife.Bind;

public class GankWebActivity extends BaseActivity<IGankWebView, GankWebPresenter> implements IGankWebView {

    @Bind(R.id.pb_gank_web)
    ProgressBar pbGankWeb;
    @Bind(R.id.wv_gank_web)
    WebView wvGankWeb;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Gank の 今日特供");
        getData();
        mPresenter.setWebView(url);
    }

    public void getData(){
        url = getIntent().getStringExtra("url");
    }

    public static Intent newIntent(Context context, String url){
        Intent intent = new Intent(context, GankWebActivity.class);
        intent.putExtra("url",url);
        return intent;
    }

    @Override
    protected GankWebPresenter createPresenter() {
        return new GankWebPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_gank_web;
    }

    @Override
    public ProgressBar getPreogressBar() {
        return pbGankWeb;
    }

    @Override
    public WebView getWebView() {
        return wvGankWeb;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wvGankWeb.destroy();
    }

    @Override
    protected boolean canBack() {
        return true;
    }
}
