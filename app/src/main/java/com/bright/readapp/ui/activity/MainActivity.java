package com.bright.readapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.bright.readapp.R;
import com.bright.readapp.ui.adapter.ViewPagerFgApapter;
import com.bright.readapp.ui.base.BaseActivity;
import com.bright.readapp.ui.base.BaseFragment;
import com.bright.readapp.ui.base.BasePresenter;
import com.bright.readapp.ui.fragment.DailyFragment;
import com.bright.readapp.ui.fragment.GankFragment;
import com.bright.readapp.ui.fragment.ZhiHuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tablayout_main)
    TabLayout tablayoutMain;
    @Bind(R.id.vp_main)
    ViewPager vpMain;

    private List<BaseFragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.today_news:
                String github_trending = "https://github.com/trending";
                startActivity(GankWebActivity.newIntent(this,github_trending));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initView() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new ZhiHuFragment());
        fragmentList.add(new GankFragment());
        fragmentList.add(new DailyFragment());
        vpMain.setOffscreenPageLimit(3);  //设置至少3个fragment，防止重复创建和销毁，造成内存溢出
        vpMain.setAdapter(new ViewPagerFgApapter(getSupportFragmentManager(), fragmentList));
        tablayoutMain.setupWithViewPager(vpMain);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
}
