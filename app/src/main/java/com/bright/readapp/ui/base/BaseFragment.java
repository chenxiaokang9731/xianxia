package com.bright.readapp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bright.readapp.R;

import butterknife.ButterKnife;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment{

    protected T mPresenter;
    protected SwipeRefreshLayout mRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getFragmentContentViewId(), container, false);
        ButterKnife.bind(this, rootView);
        initView(rootView);
        if(isCanRefresh()) initReflesh(rootView);
        return rootView;
    }

    protected void initReflesh(View view){
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        if(mRefreshLayout != null){
            mRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                    R.color.refresh_progress_2,R.color.refresh_progress_3);
            mRefreshLayout.setProgressViewOffset(true, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,getResources().getDisplayMetrics()));
            mRefreshLayout.setOnRefreshListener(this::requestDataRefresh);
        }
    }
    protected boolean isCanRefresh(){
        return true;
    }
    protected void requestDataRefresh(){

    }



    protected void initView(View rootView){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null) {
            mPresenter.detachView();
        }
    }

    protected abstract T createPresenter();

    protected abstract int getFragmentContentViewId();
}
