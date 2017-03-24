package com.song.news.service.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by song on 2017/3/16.
 * Email：815464927@qq.com
 */

public class Channel extends MultiItemEntity {
    public static final int TYPE_MY = 1;
    public static final int TYPE_OTHER = 2;
    public static final int TYPE_MY_CHANNEL = 3;
    public static final int TYPE_OTHER_CHANNEL = 4;
    public String Title;

    public Channel(int type, String title) {
        Title = title;
        itemType = type;
    }
}
