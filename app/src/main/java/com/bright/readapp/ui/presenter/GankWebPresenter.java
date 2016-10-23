package com.bright.readapp.ui.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bright.readapp.ui.base.BasePresenter;
import com.bright.readapp.ui.view.IGankWebView;

/**
 * Created by chenxiaokang on 2016/10/22.
 */
public class GankWebPresenter extends BasePresenter<IGankWebView>{

    private Activity activity;

    public GankWebPresenter(Activity activity){
        this.activity = activity;
    }

    public void setWebView(String url){

        WebSettings settings = getView().getWebView().getSettings();
        settings.setJavaScriptEnabled(true);   //设置支持javascript
        settings.setBuiltInZoomControls(true); //显示放大缩小按钮
        settings.setUseWideViewPort(true);     //设置支持放大缩小

        getView().getWebView().setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                getView().getPreogressBar().setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getView().getPreogressBar().setVisibility(View.GONE);
            }
        });

        getView().getWebView().setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                getView().getPreogressBar().setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                activity.setTitle(title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {

            }
        });

        getView().getWebView().loadUrl(url);
    }
}
