package com.bright.readapp.api;

import com.bright.readapp.bean.zhihu.Zhihu;
import com.bright.readapp.bean.zhihu.ZhihuNewsDetail;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public interface ZhihuApi {

    @GET("news/before/{time}")
    Observable<Zhihu> getBeforetNews(@Path("time") String time);

    @GET("news/latest")
    Observable<Zhihu> getLatestNews();

    @GET("news/{id}")
    Observable<ZhihuNewsDetail> getZhihuDetailNews(@Path("id") String id);
}
