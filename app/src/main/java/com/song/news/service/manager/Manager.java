package com.song.news.service.manager;

import android.content.Context;

import com.song.news.api.API;
import com.song.news.service.ReterfitHelper;
import com.song.news.service.RetrofitService;
import com.song.news.service.entity.News;
import com.song.news.service.entity.NewsChannel;

import rx.Observable;

/**
 * Created by song on 2017/3/17.
 * Email：815464927@qq.com
 */

public class Manager {
    private RetrofitService mRetrofitService;
    public Manager(Context context){
        this.mRetrofitService = ReterfitHelper.getInstance(context).getService();
    }

    /**
     * 获取新闻频道
     * @return Observable<NewsChannel>
     */
    public Observable<NewsChannel> getNewsChannal(){
        return mRetrofitService.getNewsChannal(API.APP_ID,
                String.valueOf(System.currentTimeMillis()), API.APP_KEY);
    }

    /**
     * 请求新闻列表
     * @param channalName 频道名字
     * @param page 请求页码
     * @return Observe<News>
     */
    public Observable<News> getNews(String channalName,int page){
        return mRetrofitService.getNews(channalName,page,API.APP_ID,
                String.valueOf(System.currentTimeMillis()), API.APP_KEY);
    }
}
