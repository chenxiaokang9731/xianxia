package com.bright.readapp.api;

import com.bright.readapp.ui.application.App;
import com.bright.readapp.util.StateUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public class ApiRetrofit {

    public ZhihuApi ZhihuApiService;
    public GankApi     gankService;
    public DailyApi dailyApiService;

    public static final String ZHIHU_BASE_URL = "http://news-at.zhihu.com/api/4/";
    public static final String GANK_BASE_URL = "http://gank.io/api/";
    public static final String DAILY_BASE_URL = "http://app3.qdaily.com/app3/";

    public GankApi getGankService(){
        return gankService;
    }

    public ZhihuApi getZhihuApiService(){
        return ZhihuApiService;
    }

    public DailyApi getDailyApiService(){
        return dailyApiService;
    }

    public ApiRetrofit(){

        File httpCacheDirectory = new File(App.mContext.getCacheDir(), "xianxia");
        int cacheSize = 10*1024*1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                  .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                  .cache(cache)
                  .build();

        Retrofit retrofit_zhihu = new Retrofit.Builder()
                  .baseUrl(ZHIHU_BASE_URL)
                  .client(client)
                  .addConverterFactory(GsonConverterFactory.create())
                  .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        Retrofit retrofit_gank = new Retrofit.Builder()
                .baseUrl(GANK_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        Retrofit retrofit_daily = new Retrofit.Builder()
                .baseUrl(DAILY_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        ZhihuApiService = retrofit_zhihu.create(ZhihuApi.class);
        gankService = retrofit_gank.create(GankApi.class);
        dailyApiService = retrofit_daily.create(DailyApi.class);
    }

    //cache  实现无网络缓存
    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {

        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();

        Request request = chain.request();
        if (!StateUtils.isNetworkAvailable(App.mContext)) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (StateUtils.isNetworkAvailable(App.mContext)) {
            int maxAge = 0; // read from cache
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };
}
