package com.bright.readapp.ui.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public class App extends Application{

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
