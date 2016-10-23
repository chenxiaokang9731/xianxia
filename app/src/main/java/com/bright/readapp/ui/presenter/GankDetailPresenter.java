package com.bright.readapp.ui.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bright.readapp.R;
import com.bright.readapp.bean.gank.GankData;
import com.bright.readapp.ui.adapter.GankDetailAdapter;
import com.bright.readapp.ui.base.BasePresenter;
import com.bright.readapp.ui.view.IGankDetailView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenxiaokang on 2016/10/22.
 */
public class GankDetailPresenter extends BasePresenter<IGankDetailView>{

    private IGankDetailView gankDetailView;
    private Context mContext;
    private RecyclerView recyclerView;
    private GankDetailAdapter mAdapter;

    public GankDetailPresenter(Context context){
        mContext = context;
    }

    public void getGankDetail(int year, int month, int day){

        gankDetailView = getView();
        recyclerView = gankDetailView.getRecyclerView();

        gankApi.getGankData(year, month, day)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(
                       gankData ->{displayGankData(gankData);}, this::loadError
               );
    }

    private void displayGankData(GankData ganData) {

        mAdapter = new GankDetailAdapter(mContext, ganData.results.getAllResults());
        recyclerView.setAdapter(mAdapter);
        gankDetailView.setRefreshState(false);

    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(mContext, R.string.load_error, Toast.LENGTH_SHORT).show();
    }
}
