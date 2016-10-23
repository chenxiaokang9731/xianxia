package com.bright.readapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bright.readapp.R;
import com.bright.readapp.ui.base.BaseActivity;
import com.bright.readapp.ui.presenter.GankDetailPresenter;
import com.bright.readapp.ui.view.IGankDetailView;

import butterknife.Bind;

public class GankDetailActivity extends BaseActivity<IGankDetailView, GankDetailPresenter> implements IGankDetailView {

    private int year, month, day;

    @Bind(R.id.content_list)
    RecyclerView contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentList.setLayoutManager(new LinearLayoutManager(this));

        setTitle("Gank の 今日特供");
        getData();
        mPresenter.getGankDetail(year, month, day);
    }

    private void getData() {
        year = getIntent().getIntExtra("year", -1);
        month = getIntent().getIntExtra("month", -1);
        day = getIntent().getIntExtra("day", -1);
    }

    public static Intent newInstant(Context context, int year, int month, int day){
        Intent intent = new Intent(context, GankDetailActivity.class);
        intent.putExtra("year",year);
        intent.putExtra("month", month);
        intent.putExtra("day", day);

        return intent;
    }

    @Override
    public void requestDataRefresh() {new Handler().postDelayed(()->{setRefreshState(false);}, 1000);}

    @Override
    protected GankDetailPresenter createPresenter() {
        return new GankDetailPresenter(this);
    }

    @Override
    protected boolean canBack() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_gank_detail;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return contentList;
    }

    @Override
    public void setRefreshState(boolean state) {
        if(mRefreshLayout != null){
            mRefreshLayout.setRefreshing(state);
        }
    }
}
