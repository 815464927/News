package com.song.news.service.view;

import com.song.news.service.entity.NewsChannel;

import java.util.List;

/**
 * Created by song on 2017/3/17.
 * Email：815464927@qq.com
 */

public interface NewsChannalView extends View{
    void onSucess(List<NewsChannel.ShowapiResBodyBean.ChannelListBean> newsChannalData);
    void onError(String result);
}
