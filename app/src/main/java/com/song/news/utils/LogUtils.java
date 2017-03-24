package com.song.news.utils;

import android.util.Log;

import com.song.news.api.API;

/**
 * Created by song on 2017/2/3.
 * Email：815464927@qq.com
 */

public class LogUtils {

    public static void debug(String tag ,String message){
        if(API.isLog) {
            Log.d("--->"+tag, message.length()>0?message:"null");
        }
    }

}
