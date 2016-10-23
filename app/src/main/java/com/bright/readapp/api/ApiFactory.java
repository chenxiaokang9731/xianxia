package com.bright.readapp.api;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public class ApiFactory {

    private static final Object obj = new Object();
    private static ZhihuApi zhihuApi = null;
    private static GankApi  gankApi = null;
    private static DailyApi dailyApi = null;

    public static ZhihuApi getZhihuApi(){
        if (zhihuApi == null){
            synchronized (obj){
                if (zhihuApi == null){
                    zhihuApi = new ApiRetrofit().getZhihuApiService();
                }
            }
        }
        return zhihuApi;
    }

    public static GankApi getGankApi(){
        if (gankApi == null){
            synchronized (obj){
                if (gankApi == null){
                    gankApi = new ApiRetrofit().getGankService();
                }
            }
        }
        return gankApi;
    }

    public static DailyApi getDailyApi(){
        if (dailyApi == null){
            synchronized (obj){
                if (dailyApi == null){
                    dailyApi = new ApiRetrofit().getDailyApiService();
                }
            }
        }
        return dailyApi;
    }
}
