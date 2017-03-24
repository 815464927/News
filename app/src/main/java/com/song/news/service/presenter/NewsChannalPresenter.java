package com.song.news.service.presenter;

import android.content.Context;

import com.song.news.service.entity.NewsChannel;
import com.song.news.service.manager.Manager;
import com.song.news.service.view.NewsChannalView;
import com.song.news.service.view.View;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by song on 2017/3/17.
 * Email：815464927@qq.com
 */

public class NewsChannalPresenter implements Presenter {

    private Context mContext;
    private Manager mManager;
    private CompositeSubscription mCompositeSubscription;
    private NewsChannel mNewsChannel;
    private NewsChannalView mNewsChannalView;

    public NewsChannalPresenter(Context mContext) {
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
        mNewsChannalView = (NewsChannalView) view;
    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 获取新闻频道
     */
    public void getNewsChannal() {
        mCompositeSubscription.add(mManager.getNewsChannal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsChannel>() {
                    @Override
                    public void onCompleted() {
                        if(null!= mNewsChannel){
                            List<NewsChannel.ShowapiResBodyBean.ChannelListBean> data
                                    = new ArrayList<NewsChannel.ShowapiResBodyBean.ChannelListBean>();
                            data.clear();
                            for(int i=0;i<mNewsChannel.getShowapi_res_body().getChannelList().size();
                                i++) {
                                data.add(mNewsChannel.getShowapi_res_body().getChannelList().get(i));
                            }
                            mNewsChannalView.onSucess(data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsChannalView.onError(e.toString().length()>0?e.toString():"error");
                    }

                    @Override
                    public void onNext(NewsChannel newsChannel) {
                        mNewsChannel = newsChannel;
                    }
                })
        );
    }
}
