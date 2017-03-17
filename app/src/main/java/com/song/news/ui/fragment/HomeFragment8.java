package com.song.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.news.base.BaseFragment;

/**
 * Created by song on 2017/3/15.
 * Email：815464927@qq.com
 */

public class HomeFragment8 extends BaseFragment {

    private TextView homeView8=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if(homeView8 == null) {
            homeView8 = new TextView(getContext());
            homeView8.setText(HomeFragment8.class.getName());
        }
        return homeView8;
    }

    @Override
    protected void viewCreated() {
        //ToastUtils.showToast(getActivity(),HomeFragment8.class.getName());
        //TODO 从服务器获取数据
    }
}
