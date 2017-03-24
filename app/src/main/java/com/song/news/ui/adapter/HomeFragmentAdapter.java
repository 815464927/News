package com.song.news.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.news.R;
import com.song.news.service.entity.News;

import java.util.ArrayList;

/**
 * Created by song on 2017/3/23.
 * Email：815464927@qq.com
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.HomeFragemtItemViewHolder>{

    private Context mContext;
    private ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mData;

    public HomeFragmentAdapter(Context mContext, ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public HomeFragemtItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeFragemtItemViewHolder viewHolder = new HomeFragemtItemViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.home_fragment_item,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeFragemtItemViewHolder holder, int position) {
        News.ShowapiResBodyBean.PagebeanBean.ContentlistBean itemData = mData.get(position);
        holder.title.setText(itemData.getTitle());
        holder.source.setText(itemData.getSource());
        holder.time.setText(itemData.getPubDate());
        holder.des.setText(itemData.getDesc());
        if(itemData.isHavePic()) {
            Glide.with(mContext).load(itemData.getImageurls().get(0)).into(holder.iv);
        }
        holder.iv.setVisibility(itemData.isHavePic()?View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class HomeFragemtItemViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView source;
        private TextView time;
        private TextView des;
        private ImageView iv;

        public HomeFragemtItemViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
            source = (TextView) view.findViewById(R.id.tv_source);
            time = (TextView) view.findViewById(R.id.tv_time);
            des = (TextView) view.findViewById(R.id.tv_des);
            iv = (ImageView) view.findViewById(R.id.iv_image);
        }
    }
}
