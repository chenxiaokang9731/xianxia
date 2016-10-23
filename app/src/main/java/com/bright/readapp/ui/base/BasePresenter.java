package com.bright.readapp.ui.base;

import com.bright.readapp.api.ApiFactory;
import com.bright.readapp.api.DailyApi;
import com.bright.readapp.api.GankApi;
import com.bright.readapp.api.ZhihuApi;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public abstract class BasePresenter<V> {

    protected Reference<V> mViewRef;
    protected ZhihuApi zhihuApi = ApiFactory.getZhihuApi();
    protected GankApi  gankApi = ApiFactory.getGankApi();
    protected DailyApi dailyApi = ApiFactory.getDailyApi();

    public void attachView(V view){
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView(){
        return mViewRef.get();
    }

    public void detachView(){
        if(mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
