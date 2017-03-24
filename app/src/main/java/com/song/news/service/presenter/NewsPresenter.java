package com.song.news.service.presenter;

import android.content.Context;

import com.song.news.service.entity.News;
import com.song.news.service.manager.Manager;
import com.song.news.service.view.NewsView;
import com.song.news.service.view.View;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by song on 2017/3/17.
 * Email：815464927@qq.com
 */

public class NewsPresenter implements Presenter {

    private Context mContext;
    private Manager mManager;
    private CompositeSubscription mCompositeSubscription;
    private News mNews;
    private NewsView mNewsView;

    public NewsPresenter(Context mContext) {
        this.mContext = mContext;
        onCreate();
    }

    @Override
    public void onCreate() {
        mManager = new Manager(mContext);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void attachView(View view) {
        mNewsView = (NewsView) view;
    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 获取新闻列表
     * @param channalName 新闻频道名字
     * @param page 请求的页码
     */
    public void getNews(String channalName,int page) {
        mCompositeSubscription.add(mManager.getNews(channalName,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<News>() {
                    @Override
                    public void onCompleted() {
                        if (null != mNews) {
                            ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data =
                                    new ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean>();
                            for (int i = 0; i < mNews.getShowapi_res_body().getPagebean().getContentlist().size();
                                 i++) {
                                data.add(mNews.getShowapi_res_body().getPagebean().getContentlist().get(i));
                            }
                            mNewsView.onSucess(data, mNews.getShowapi_res_body().getPagebean().getAllPages());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsView.onError(e.toString().length()>0?e.toString():"error");
                    }

                    @Override
                    public void onNext(News news) {
                        mNews = news;
                    }
                })
        );
    }
}
