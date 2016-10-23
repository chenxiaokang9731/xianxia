package com.bright.readapp.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bright.readapp.R;
import com.bright.readapp.ui.base.BaseFragment;
import com.bright.readapp.ui.presenter.ZhihuPresenter;
import com.bright.readapp.ui.view.IZhiHuView;

import butterknife.Bind;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public class ZhiHuFragment extends BaseFragment<IZhiHuView, ZhihuPresenter> implements IZhiHuView{

    @Bind(R.id.rv_content)
    RecyclerView rvContent;

    private LinearLayoutManager manager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(()->{
            mPresenter.getLatestNews();
            mPresenter.scrollGetZhihuNews();
        }, 1000);
    }

    @Override
    protected void initView(View rootView) {
        manager = new LinearLayoutManager(getContext());
        rvContent.setLayoutManager(manager);
    }

    @Override
    protected void requestDataRefresh() {
        new Handler().postDelayed(()->{mPresenter.getLatestNews();}, 1000);
    }

    @Override
    protected ZhihuPresenter createPresenter() {
        return new ZhihuPresenter(getContext());
    }

    @Override
    protected int getFragmentContentViewId() {
        return R.layout.fragment_zhihu;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return rvContent;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return manager;
    }

    @Override
    public void setRefreshState(boolean state) {
        if(mRefreshLayout != null){
            mRefreshLayout.setRefreshing(false);
        }
    }
}
