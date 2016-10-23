package com.bright.readapp.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.bright.readapp.R;
import com.bright.readapp.ui.base.BaseActivity;
import com.bright.readapp.ui.base.BasePresenter;
import com.bright.readapp.ui.widget.SplashView;

import java.util.Random;

import butterknife.Bind;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.splash_view)
    SplashView splashView;
    @Bind(R.id.tv_spalsh_info)
    TextView tvSpalshInfo;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/rm_albion.ttf");
        tvSpalshInfo.setTypeface(typeface);

        startSpalsh();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void startSpalsh(){
        mHandler.postDelayed(this::endSplash, 1000+new Random().nextInt(2000));
    }

    private void endSplash(){
        splashView.splashAndDisappear(new SplashView.ISplashListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onUpdate(float completionFraction) {

            }

            @Override
            public void onEnd() {
                goToMain();
            }
        });
    }

    public void goToMain(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }
}
