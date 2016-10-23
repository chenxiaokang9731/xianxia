package com.bright.readapp.ui.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public interface IGankView {
    RecyclerView getRecyclerView();
    GridLayoutManager getLayoutManager();
    void setRefreshState(boolean state);
}
