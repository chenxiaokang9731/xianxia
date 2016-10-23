package com.bright.readapp.ui.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public interface IDailyView{

    LinearLayoutManager getLayoutManager();
    RecyclerView getRecyclerView();
    void setRefreshState(boolean state);

}
