package com.bright.readapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bright.readapp.R;
import com.bright.readapp.bean.gank.Gank;
import com.bright.readapp.ui.activity.GankWebActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxiaokang on 2016/10/22.
 */
public class GankDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<Gank> mGankList;
    private LayoutInflater inflater;

    public GankDetailAdapter(Context context, List<Gank> gankList) {
        mContext = context;
        mGankList = gankList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_gank_detail, parent, false);

        return new GankDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof GankDetailViewHolder) {
            Gank gank = mGankList.get(position);
            ((GankDetailViewHolder) holder).bindItem(gank);
        }

    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }

    public class GankDetailViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_type_bg)
        ImageView ivTypeBg;
        @Bind(R.id.iv_type)
        ImageView ivType;
        @Bind(R.id.tv_type)
        TextView tvType;
        @Bind(R.id.tv_desc)
        TextView tvDesc;
        @Bind(R.id.tv_who)
        TextView tvWho;
        @Bind(R.id.card_gank)
        CardView cardGank;

        public GankDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Gank gank) {
            switch (gank.getType()){
                case "Android":
                    Glide.with(mContext).load(R.drawable.android).centerCrop().into(ivType);
                    ivTypeBg.setBackgroundResource(R.color.android);
                    break;
                case "iOS":
                    Glide.with(mContext).load(R.drawable.ios).centerCrop().into(ivType);
                    ivTypeBg.setBackgroundResource(R.color.ios);
                    break;
                case "休息视频":
                    Glide.with(mContext).load(R.drawable.video).centerCrop().into(ivType);
                    ivTypeBg.setBackgroundResource(R.color.休息视频);
                    break;
                case "前端":
                    Glide.with(mContext).load(R.drawable.web).centerCrop().into(ivType);
                    ivTypeBg.setBackgroundResource(R.color.前端);
                    break;
                case "拓展资源":
                    Glide.with(mContext).load(R.drawable.tuozhan).centerCrop().into(ivType);
                    ivTypeBg.setBackgroundResource(R.color.拓展资源);
                    break;
                case "瞎推荐":
                    Glide.with(mContext).load(R.drawable.tuijian).centerCrop().into(ivType);
                    ivTypeBg.setBackgroundResource(R.color.瞎推荐);
                    break;
            }

            tvType.setText("来自话题 : "+gank.getType());
            tvDesc.setText(gank.getDesc());
            tvWho.setText("via : "+gank.getWho());

            cardGank.setOnClickListener(v->{

                Intent intent = GankWebActivity.newIntent(mContext, gank.getUrl());
                mContext.startActivity(intent);

            });
        }
    }
}
