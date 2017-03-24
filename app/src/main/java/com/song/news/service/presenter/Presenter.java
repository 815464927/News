package com.song.news.service.presenter;

import com.song.news.service.view.View;

/**
 * Created by song on 2017/3/17.
 * Email：815464927@qq.com
 */

public interface Presenter {
    void onCreate();
    void attachView(View view);
    void onStop();
}
