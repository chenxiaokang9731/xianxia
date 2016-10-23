package com.bright.readapp.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.Toast;

import com.bright.readapp.R;
import com.bright.readapp.bean.zhihu.Zhihu;
import com.bright.readapp.ui.adapter.ZhiHuFgAdapter;
import com.bright.readapp.ui.base.BasePresenter;
import com.bright.readapp.ui.view.IZhiHuView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public class ZhihuPresenter extends BasePresenter<IZhiHuView>{

    private Context mContext;
    private IZhiHuView zhiHuView;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private boolean isScrollLoad = false;
    private ZhiHuFgAdapter zhiHuFgAdapter;
    private int mLastViewPostition;
    private Zhihu mZhihu;

    public ZhihuPresenter(Context context){
        mContext = context;
    }

    public void getLatestNews(){

        zhiHuView = getView();
        recyclerView = zhiHuView.getRecyclerView();
        manager = zhiHuView.getLayoutManager();

        zhihuApi.getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(zhihu -> {
                    displayZhihuNews(zhihu);
                }, this::loadError);
    }

    public void getBeforeNews(String time){

        zhiHuView = getView();
        recyclerView = zhiHuView.getRecyclerView();
        manager = zhiHuView.getLayoutManager();

        zhihuApi.getBeforetNews(time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(zhihu -> {
                    displayZhihuNews(zhihu);
                }, this::loadError);
    }

    String time;
    private void displayZhihuNews(Zhihu zhihu){

        if(isScrollLoad){
            if(time == null){
                System.out.println("没有更多加载");
                zhiHuFgAdapter.updateState(zhiHuFgAdapter.HAS_NONE);
                return;
            }else {
                mZhihu.getStories().addAll(zhihu.getStories());
            }
            zhiHuFgAdapter.notifyDataSetChanged();
        }else {
            mZhihu = zhihu;
            zhiHuFgAdapter = new ZhiHuFgAdapter(mContext, mZhihu);
            recyclerView.setAdapter(zhiHuFgAdapter);
        }
        zhiHuView.setRefreshState(false);
        time = zhihu.getDate();
    }

    public void scrollGetZhihuNews(){

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(recyclerView != null && manager != null){
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                        mLastViewPostition = manager.findLastVisibleItemPosition();
                        if(mLastViewPostition + 1 == manager.getItemCount()){

                            isScrollLoad = true;
                            zhiHuFgAdapter.updateState(zhiHuFgAdapter.HAS_MORE);
                            new Handler().postDelayed(()->getBeforeNews(time), 1000);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

//                mLastViewPostition = manager.findLastVisibleItemPosition();
            }
        });
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(mContext, R.string.load_error, Toast.LENGTH_SHORT).show();
    }
}
