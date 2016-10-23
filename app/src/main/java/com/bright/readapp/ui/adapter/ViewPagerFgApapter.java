package com.bright.readapp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bright.readapp.ui.base.BaseFragment;

import java.util.List;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public class ViewPagerFgApapter extends FragmentPagerAdapter{

    private List<BaseFragment> mFragmentLists;

    public ViewPagerFgApapter(FragmentManager fm, List<BaseFragment> fragmentLists) {
        super(fm);
        mFragmentLists = fragmentLists;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentLists.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentLists.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "知乎";
            case 1:
                return "干货";
            case 2:
                return "好奇心日报";
        }
        return null;
    }
}
