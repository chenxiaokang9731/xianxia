package com.bright.readapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bright.readapp.R;
import com.bright.readapp.ui.base.BaseActivity;
import com.bright.readapp.ui.presenter.ZhihuWebPresenter;
import com.bright.readapp.ui.view.IZhihuWebView;

import butterknife.Bind;

public class ZhihuWebActivity extends BaseActivity<IZhihuWebView, ZhihuWebPresenter> implements IZhihuWebView{

    private String ID;

    @Bind(R.id.iv_web_img)
    ImageView ivWebImg;
    @Bind(R.id.tv_img_title)
    TextView tvImgTitle;
    @Bind(R.id.tv_img_source)
    TextView tvImgSource;
    @Bind(R.id.web_view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getValue();
        mPresenter.getDetailNews(ID);
    }

    private void getValue() {
        ID = getIntent().getStringExtra("id");
    }

    public static Intent newIntent(Context context, String id){

        Intent intent = new Intent(context, ZhihuWebActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    protected ZhihuWebPresenter createPresenter() {
        return new ZhihuWebPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_zhihu_web;
    }

    @Override
    public ImageView getWebImg() {
        return ivWebImg;
    }

    @Override
    public TextView getImgTitle() {
        return tvImgTitle;
    }

    @Override
    public TextView getImgSource() {
        return tvImgSource;
    }

    @Override
    public WebView getWebView() {
        return webView;
    }
}
