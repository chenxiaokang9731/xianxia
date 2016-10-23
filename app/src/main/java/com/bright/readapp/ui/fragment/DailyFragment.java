package com.bright.readapp.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bright.readapp.R;
import com.bright.readapp.ui.base.BaseFragment;
import com.bright.readapp.ui.presenter.DailyPresenter;
import com.bright.readapp.ui.view.IDailyView;

import butterknife.Bind;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public class DailyFragment extends BaseFragment<IDailyView, DailyPresenter> implements IDailyView {

    @Bind(R.id.content_list)
    RecyclerView contentList;

    private LinearLayoutManager manager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getDailyTimeLineInfo("0");
        mPresenter.scrollGetDailyData();
    }

    @Override
    protected void initView(View rootView) {
        manager = new LinearLayoutManager(getContext());
        contentList.setLayoutManager(manager);
    }

    @Override
    protected void requestDataRefresh() {
        new Handler().postDelayed(()->{setRefreshState(false);}, 1000);
    }

    @Override
    protected DailyPresenter createPresenter() {
        return new DailyPresenter(getContext());
    }

    @Override
    protected int getFragmentContentViewId() {
        return R.layout.fragment_daily;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return manager;
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
