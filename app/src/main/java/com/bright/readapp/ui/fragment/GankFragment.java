package com.bright.readapp.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bright.readapp.R;
import com.bright.readapp.ui.base.BaseFragment;
import com.bright.readapp.ui.presenter.GankPresenter;
import com.bright.readapp.ui.view.IGankView;

import butterknife.Bind;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public class GankFragment extends BaseFragment<IGankView, GankPresenter> implements IGankView{

    @Bind(R.id.content_list)
    RecyclerView content_list;

    private GridLayoutManager manager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(()->{
            mPresenter.getMeizhiData();
            mPresenter.scrollGetZhihuNews();
        }, 1000);
    }

    @Override
    protected void initView(View rootView) {
        manager = new GridLayoutManager(getContext(), 2);
        content_list.setLayoutManager(manager);
    }

    @Override
    protected void requestDataRefresh() {
        new Handler().postDelayed(()->{
            mPresenter.getMeizhiData();
        }, 1000);
    }

    @Override
    protected GankPresenter createPresenter() {
        return new GankPresenter(getContext());
    }

    @Override
    protected int getFragmentContentViewId() {
        return R.layout.fragment_gank;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return content_list;
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return manager;
    }

    @Override
    public void setRefreshState(boolean state) {
        if(mRefreshLayout != null){
            mRefreshLayout.setRefreshing(state);
        }
    }
}
