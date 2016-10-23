package com.bright.readapp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bright.readapp.R;
import com.bright.readapp.bean.gank.Gank;
import com.bright.readapp.ui.activity.GankDetailActivity;
import com.bright.readapp.ui.activity.PhotoActivity;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenxiaokang on 2016/10/19.
 */
public class GankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Gank> mGankList;
    private LayoutInflater inflater;

    public GankAdapter(Context context, List<Gank> gankList) {
        mContext = context;
        mGankList = gankList;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gank_meizi, parent, false);
        return new GanViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof GanViewHolder){
            ((GanViewHolder)holder).bindItem(mGankList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }

    public class GanViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_meizhi)
        ImageView iv_meizhi;
        @Bind(R.id.tv_meizhi_title)
        TextView tv_meizhi_title;
        @Bind(R.id.card_meizhi)
        CardView cardMeizhi;

        public GanViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Gank gank){
            tv_meizhi_title.setText(gank.getDesc());
            Glide.with(mContext).load(gank.getUrl()).centerCrop().into(iv_meizhi);

            //点击了图片
            iv_meizhi.setOnClickListener(v -> {
                Intent intent = PhotoActivity.newIntent(mContext, gank.getUrl(), gank.getDesc());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, iv_meizhi, PhotoActivity.TRANSIT_PIC);

                ActivityCompat.startActivity((Activity)mContext, intent, optionsCompat.toBundle());
            });

            //点击了CardView
            cardMeizhi.setOnClickListener(v->{

                long time = gank.getPublishedAt().getTime();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(time);

                Intent intent = GankDetailActivity.newInstant(mContext, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(calendar.DAY_OF_MONTH));
                mContext.startActivity(intent);
            });
        }
    }
}
