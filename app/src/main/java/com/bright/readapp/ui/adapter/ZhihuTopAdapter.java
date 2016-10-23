package com.bright.readapp.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bright.readapp.bean.zhihu.TopStories;
import com.bright.readapp.ui.activity.ZhihuWebActivity;
import com.bright.readapp.ui.application.App;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxiaokang on 2016/10/19.
 */
public class ZhihuTopAdapter extends PagerAdapter{

    private List<TopStories> mTopStorieLists;
    private List<ImageView> mImages;
    private int pagersize;

    public ZhihuTopAdapter(List<TopStories> topStoriesLists, Context context){
        mTopStorieLists = topStoriesLists;

        mImages = new ArrayList<>();
        for (int i = 0; i<topStoriesLists.size(); i++){
            TopStories topStories = topStoriesLists.get(i);
            ImageView iv = new ImageView(context);
            iv.setClickable(true);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(lp);

            iv.setOnClickListener(v->{
                context.startActivity(ZhihuWebActivity.newIntent(context, topStories.getId()));
            });

            Glide.with(App.mContext).load(topStories.getImage()).centerCrop().into(iv);
            mImages.add(iv);
        }
        pagersize = mImages.size();
    }

    @Override
    public int getCount() {
        return pagersize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView iv = mImages.get(position);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImages.get(position));
    }
}
