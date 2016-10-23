package com.bright.readapp.ui.view;

import android.support.v7.widget.RecyclerView;

/**
 * Created by chenxiaokang on 2016/10/22.
 */
public interface IGankDetailView {

    RecyclerView getRecyclerView();
    void setRefreshState(boolean state);

}
