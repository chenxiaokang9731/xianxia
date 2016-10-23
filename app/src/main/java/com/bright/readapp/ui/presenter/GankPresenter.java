package com.bright.readapp.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.Toast;

import com.bright.readapp.R;
import com.bright.readapp.bean.gank.Gank;
import com.bright.readapp.bean.gank.Meizhi;
import com.bright.readapp.bean.gank.Video;
import com.bright.readapp.ui.adapter.GankAdapter;
import com.bright.readapp.ui.base.BasePresenter;
import com.bright.readapp.ui.view.IGankView;
import com.bright.readapp.util.DateUtils;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public class GankPresenter extends BasePresenter<IGankView>{

    private IGankView gankView;
    private Context mContext;
    private int page = 1;
    private RecyclerView recyclerView;
    private GridLayoutManager manager;
    private boolean isScrollLoad = false;
    private GankAdapter gankAdapter;
    private List<Gank> mGankLists;
    private int mLastViewPostition = 0;

    public GankPresenter(Context context){
        mContext = context;
    }

    public void getMeizhiData(){

        if(!isScrollLoad){
            gankView = getView();
            manager = gankView.getLayoutManager();
            recyclerView = gankView.getRecyclerView();
        }

        if(isScrollLoad){
            page += 1;
        }

        Observable.zip(gankApi.getMeizhiData(page), gankApi.getVideoData(page), this::creatDesc)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(meizhi -> {
                    displayMeizhi(meizhi);
               }, this::loadError);
    }

    /**
     *
     * @param meizhi list
     * @param video  list
     * @return
     */
    private Meizhi creatDesc(Meizhi meizhi, Video video) {
        for (Gank gankmeizhi : meizhi.getResults()) {
            gankmeizhi.desc = gankmeizhi.desc + " " +
                    getVideoDesc(gankmeizhi.getPublishedAt(), video.getResults());
        }
        return meizhi;
    }

    //匹配同一天的福利描述和视频描述
    private String getVideoDesc(Date publishedAt, List<Gank> results) {
        String videoDesc = "";
        for (int i = 0; i < results.size(); i++) {
            Gank video = results.get(i);
            if (video.getPublishedAt() == null) video.setPublishedAt(video.getCreatedAt());
            if (DateUtils.isSameDate(publishedAt, video.getPublishedAt())) {
                videoDesc = video.getDesc();
                break;
            }
        }
        return videoDesc;
    }

    public void displayMeizhi(Meizhi mz){
        if(isScrollLoad){
            if(mGankLists == null){
                gankView.setRefreshState(false);
                Toast.makeText(mContext, "oo,没得了~", Toast.LENGTH_SHORT).show();
                return;
            }else {
                mGankLists.addAll(mz.getResults());
            }
            gankAdapter.notifyDataSetChanged();
        }else {
            mGankLists = mz.getResults();
            gankAdapter = new GankAdapter(mContext, mGankLists);
            recyclerView.setAdapter(gankAdapter);
        }
        gankView.setRefreshState(false);
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
                            new Handler().postDelayed(()->{
                                gankView.setRefreshState(true);
                                getMeizhiData();
                            },1000);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
//        gankFgView.setDataRefresh(false);
        Toast.makeText(mContext, R.string.load_error, Toast.LENGTH_SHORT).show();
    }
}
