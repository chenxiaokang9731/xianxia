package com.bright.readapp.ui.view;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by chenxiaokang on 2016/10/19.
 */
public interface IZhihuWebView {

    ImageView getWebImg();
    TextView  getImgTitle();
    TextView  getImgSource();
    WebView   getWebView();

}
