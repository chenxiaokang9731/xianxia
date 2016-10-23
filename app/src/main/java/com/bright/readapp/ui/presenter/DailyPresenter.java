package com.bright.readapp.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bright.readapp.R;
import com.bright.readapp.bean.daily.DailyTimeLine;
import com.bright.readapp.ui.adapter.DailyFgAdapter;
import com.bright.readapp.ui.base.BasePresenter;
import com.bright.readapp.ui.view.IDailyView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public class DailyPresenter extends BasePresenter<IDailyView>{

    private Context mContext;
    private boolean isScrollLoad = false;
    private DailyFgAdapter dailyFgAdapter;
    private DailyTimeLine timeLine;
    private String next_pager;
    private int lastVisibleItem;
    private String has_more;

    public DailyPresenter(Context context){
        mContext = context;
    }

    public void getDailyTimeLineInfo(String num){

        dailyApi.getDailyTimeLine(num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dailyTimeLine -> {
                    displayDailyTimeLineInfo(dailyTimeLine);
                }, this::loadError);
    }

    public void displayDailyTimeLineInfo(DailyTimeLine dailyTimeLine){
        if(dailyTimeLine.getResponse().getLast_key()!=null){
            next_pager = dailyTimeLine.getResponse().getLast_key();
        }
        has_more = dailyTimeLine.getResponse().getHas_more();
        if(isScrollLoad){
            if (dailyTimeLine.getResponse().getFeeds() == null) {
                dailyFgAdapter.updateLoadStatus(dailyFgAdapter.LOAD_NONE);
                getView().setRefreshState(false);
                return;
            }
            else {
                timeLine.getResponse().getFeeds().addAll(dailyTimeLine.getResponse().getFeeds());
            }
            dailyFgAdapter.notifyDataSetChanged();
        }else {
            timeLine = dailyTimeLine;
            dailyFgAdapter = new DailyFgAdapter(mContext, timeLine.getResponse());
            getView().getRecyclerView().setAdapter(dailyFgAdapter);
        }
        getView().setRefreshState(false);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(mContext, R.string.load_error, Toast.LENGTH_SHORT).show();
    }

    public void scrollGetDailyData(){
        getView().getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = getView().getLayoutManager()
                            .findLastVisibleItemPosition();
                    if (getView().getLayoutManager().getItemCount() == 1) {
                        dailyFgAdapter.updateLoadStatus(dailyFgAdapter.LOAD_NONE);
                        return;
                    }
                    if (lastVisibleItem + 1 == getView().getLayoutManager()
                            .getItemCount()) {
                        if(has_more.equals("true")) {
                            isScrollLoad = true;
                        }
                        new Handler().postDelayed(() -> getDailyTimeLineInfo(next_pager), 1000);
                    }
                }
            }
        });
    }
}
