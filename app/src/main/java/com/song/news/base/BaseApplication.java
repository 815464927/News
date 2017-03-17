package com.song.news.base;

import android.app.Application;

import com.song.news.utils.SharedPreferencesUtil;

/**
 * Created by song on 2017/3/15.
 * Email：815464927@qq.com
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SharedPreferencesUtil.init(this, "weyye");
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
