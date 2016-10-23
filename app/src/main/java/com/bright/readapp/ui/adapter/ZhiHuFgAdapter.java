package com.bright.readapp.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bright.readapp.R;
import com.bright.readapp.bean.zhihu.Stories;
import com.bright.readapp.bean.zhihu.TopStories;
import com.bright.readapp.bean.zhihu.Zhihu;
import com.bright.readapp.ui.activity.ZhihuWebActivity;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public class ZhiHuFgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private int state = 1;
    public static final int HAS_NONE = 0;
    public static final int HAS_MORE = 1;
    private static final int ITEM_TOP = -1;
    private static final int ITEM_FOOTER = -2;

    private Zhihu mZhihu;
    private Context mContext;
    private LayoutInflater inflater;
    private ScheduledExecutorService mScheduledExecutorService;
    private int topPagerSize;
    private int currentPage = 0;

    private ViewPager vp_top;

    public ZhiHuFgAdapter(Context context, Zhihu zhihu){
        mZhihu = zhihu;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TOP){
            View view = inflater.inflate(R.layout.item_zhihu_top, parent, false);
            return new TopViewHolder(view);
        }else if (viewType == ITEM_FOOTER){
            View view = inflater.inflate(R.layout.item_zhihu_footer, parent, false);
            return new FooterViewHolder(view);
        }else {
            View view = inflater.inflate(R.layout.item_zhihu_itemview, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof TopViewHolder){
            ((TopViewHolder)holder).bindTopItem(mZhihu.getTop_stories());
        }else if (holder instanceof FooterViewHolder){
            ((FooterViewHolder)holder).bindItem();
        }else if (holder instanceof ItemViewHolder){
            ((ItemViewHolder)holder).bindItem(mZhihu.getStories().get(position-1));
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            //头部
            return ITEM_TOP;
        }else if(position+1 == getItemCount()){
            //底部
            return ITEM_FOOTER;
        }else {
            //Item列表
            return position;
        }
    }

    @Override
    public int getItemCount() {
        return mZhihu.getStories().size() + 2;
    }

    public class TopViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.rly)
        RelativeLayout rly;
        @Bind(R.id.tv_top_info)
        TextView tv_top_info;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            vp_top = (ViewPager) itemView.findViewById(R.id.vp_top);
        }

        public void bindTopItem(List<TopStories> topStorieLists){
            vp_top.setAdapter(new ZhihuTopAdapter(topStorieLists, mContext));

            tv_top_info.setText(topStorieLists.get(0).getTitle());

            topPagerSize = topStorieLists.size();

            vp_top.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tv_top_info.setText(topStorieLists.get(position%topPagerSize).getTitle());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.pb_footer)
        ProgressBar pb_footer;
        @Bind(R.id.tv_footer)
        TextView tv_footer;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(){
            switch (state){
                case HAS_MORE:
                    tv_footer.setText("正在加载");
                    pb_footer.setVisibility(View.VISIBLE);
                    break;

                case HAS_NONE:
                    tv_footer.setText("没有更多");
                    pb_footer.setVisibility(View.GONE);
                    break;
            }
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_stories_title)
        TextView tv_stories_title;
        @Bind(R.id.iv_stories_img)
        ImageView iv_stories_img;
        @Bind(R.id.card_stories)
        CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Stories stories){
            tv_stories_title.setText(stories.getTitle());

            String imgUrl = stories.getImages()[0];
            Glide.with(mContext).load(imgUrl).centerCrop().into(iv_stories_img);

            cardView.setOnClickListener(v->{
                mContext.startActivity(ZhihuWebActivity.newIntent(mContext, stories.getId()));
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if(holder instanceof TopViewHolder){
            startAutoRun();
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if(holder instanceof TopViewHolder){
            stopAutoRun();
        }
    }

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            vp_top.setCurrentItem(currentPage);
        }
    };

    /**
     *  自动轮播任务
     */
    public void startAutoRun(){
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(currentPage < topPagerSize-1){
                    currentPage ++;
                }else {
                    currentPage = 0;
                }
                mHandler.obtainMessage().sendToTarget();
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    /**
     * 停止自动轮播
     */
    public void stopAutoRun(){
        if(mScheduledExecutorService != null){
            mScheduledExecutorService.shutdown();
            mScheduledExecutorService = null;
        }
    }

    public void updateState(int state){
        this.state = state;
        notifyDataSetChanged();
    }
}
