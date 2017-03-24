package com.song.news.service;

import com.song.news.service.entity.News;
import com.song.news.service.entity.NewsChannel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by song on 2017/3/17.
 * Email：815464927@qq.com
 */

public interface RetrofitService {

    /**
     *
     * https://route.showapi.com/109-35?
     * channelId=
     * &channelName=娱乐焦点
     * &maxResult=20
     * &needAllList=0
     * &needContent=0
     * &needHtml=0
     * &page=1
     * &showapi_appid=11414
     * &showapi_timestamp=20170323160218&title=
     * &showapi_sign=fa5f902e901728a370fcd48c53c879fb
     *
     */
    @GET("109-35")
    Observable<News> getNews(@Query("channelName") String channelName,
                             @Query("page") int page,
                             @Query("showapi_appid") String showapi_appid,
                             @Query("showapi_timestamp") String showapi_timestamp,
                             @Query("showapi_sign") String showapi_sign);

    /**
     * https://route.showapi.com/109-34?
     * showapi_appid=11414
     * &showapi_timestamp=20170317163322
     * &showapi_sign=7564e104b726ae92148d4c6dbdb44b68
     * @return
     */
    @GET("109-34")
    Observable<NewsChannel> getNewsChannal(@Query("showapi_appid") String showapi_appid,
                                           @Query("showapi_timestamp") String showapi_timestamp,
                                           @Query("showapi_sign") String showapi_sign);

}
