package com.bright.readapp.ui.presenter;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bright.readapp.R;
import com.bright.readapp.bean.zhihu.ZhihuNewsDetail;
import com.bright.readapp.ui.base.BasePresenter;
import com.bright.readapp.ui.view.IZhihuWebView;
import com.bumptech.glide.Glide;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenxiaokang on 2016/10/19.
 */
public class ZhihuWebPresenter extends BasePresenter<IZhihuWebView>{

    private Context mContext;

    public ZhihuWebPresenter(Context context){
        mContext = context;
    }

    public void getDetailNews(String id){
        zhihuApi.getZhihuDetailNews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news -> {
                    setWebView(news);
                }, this::loadError);
    }

    private void setWebView(ZhihuNewsDetail news) {
        ImageView webImg = getView().getWebImg();
        TextView titleImg = getView().getImgTitle();
        TextView sourceImg = getView().getImgSource();
        WebView  webView = getView().getWebView();

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        String head = "<head>\n" +
                "\t<link rel=\"stylesheet\" href=\""+news.getCss()[0]+"\"/>\n" +
                "</head>";
        String img = "<div class=\"headline\">";
        String html =head + news.getBody().replace(img," ");
        webView.loadDataWithBaseURL(null,html,"text/html","utf-8",null);

        Glide.with(mContext).load(news.getImage()).centerCrop().into(webImg);

        titleImg.setText(news.getTitle());

        sourceImg.setText("图片 "+news.getImage_source());
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(mContext, R.string.load_error, Toast.LENGTH_SHORT).show();
    }
}
