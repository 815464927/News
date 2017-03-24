package com.song.news.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.news.R;
import com.song.news.service.entity.News;
import com.song.news.ui.activity.WebViewActivity;

import java.util.ArrayList;

/**
 * Created by song on 2017/3/23.
 * Email：815464927@qq.com
 */

public class NewsFragmentAdapter extends RecyclerView.Adapter<NewsFragmentAdapter.HomeFragemtItemViewHolder>{

    private Context mContext;
    private ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mData;

    public NewsFragmentAdapter(Context mContext,
                               ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public HomeFragemtItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeFragemtItemViewHolder viewHolder = new HomeFragemtItemViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.news_fragment_item,parent,false));
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(HomeFragemtItemViewHolder holder, int position) {
        News.ShowapiResBodyBean.PagebeanBean.ContentlistBean itemData = mData.get(position);
        holder.title.setText(itemData.getTitle());
        holder.source.setText(TextUtils.concat("来源："+itemData.getSource()));
        holder.time.setText(TextUtils.concat("更新时间："+itemData.getPubDate()));
        holder.des.setText(itemData.getDesc());
        if(itemData.isHavePic()) {
            String imageUrl = itemData.getImageurls().get(0).getUrl();
            if(imageUrl.endsWith(".gif")){
                Glide.with(mContext).load(imageUrl).asGif().into(holder.iv);
            }else {
                Glide.with(mContext).load(imageUrl).into(holder.iv);
            }
        }
        holder.iv.setVisibility(itemData.isHavePic()?View.VISIBLE:View.GONE);
        //设置监听事件
        final int pos = position;
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.webUrl,mData.get(pos).getLink());
                mContext.startActivity(intent);
                Activity mActivity = (Activity) mContext;
                mActivity.overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class HomeFragemtItemViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout item;
        private TextView title;
        private TextView source;
        private TextView time;
        private TextView des;
        private ImageView iv;

        public HomeFragemtItemViewHolder(View view){
            super(view);
            item = (LinearLayout) view.findViewById(R.id.ll_item);
            title = (TextView) view.findViewById(R.id.tv_title);
            source = (TextView) view.findViewById(R.id.tv_source);
            time = (TextView) view.findViewById(R.id.tv_time);
            des = (TextView) view.findViewById(R.id.tv_des);
            iv = (ImageView) view.findViewById(R.id.iv_image);
        }
    }
}
