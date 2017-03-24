package com.song.news.service.view;

import com.song.news.service.entity.News;

import java.util.ArrayList;

/**
 * Created by song on 2017/3/17.
 * Email：815464927@qq.com
 */

public interface NewsView extends View{
    void onSucess(ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> listData,
                  int allPage);
    void onError(String result);
}
