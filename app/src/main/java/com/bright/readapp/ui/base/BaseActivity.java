package com.bright.readapp.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;

import com.bright.readapp.R;

import butterknife.ButterKnife;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public abstract class BaseActivity<V, T extends BasePresenter> extends AppCompatActivity{

    public T mPresenter;
    private Toolbar mToolbar;
    private AppBarLayout mAppBar;
    protected SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);

        if(createPresenter() != null){
            mPresenter = createPresenter();
            mPresenter.attachView((V)this);
        }

        mAppBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if(mToolbar != null && mAppBar != null){
            setSupportActionBar(mToolbar);

            if(canBack()){
                ActionBar actionBar = getSupportActionBar();
                if(actionBar != null){
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
            }

            if(Build.VERSION.SDK_INT > 21){
                mAppBar.setElevation(10.6f);
            }
        }

        if (isCanRefresh()) initReflesh();
    }

    protected void initReflesh(){
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        if(mRefreshLayout != null){
            mRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                    R.color.refresh_progress_2,R.color.refresh_progress_3);
            mRefreshLayout.setProgressViewOffset(true, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,getResources().getDisplayMetrics()));
            mRefreshLayout.setOnRefreshListener(this::requestDataRefresh);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
//            onBackPressed();
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void requestDataRefresh() {
    }

    protected boolean isCanRefresh(){
        return true;
    }

    protected boolean canBack(){
        return false;
    }

    protected abstract T createPresenter();

    protected abstract int getContentViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ButterKnife.unbind(this);
        if(mPresenter!=null) {
            mPresenter.detachView();
        }
    }
}
