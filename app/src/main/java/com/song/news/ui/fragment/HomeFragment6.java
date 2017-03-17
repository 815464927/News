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

public class HomeFragment6 extends BaseFragment {

    private TextView homeView6=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if(homeView6 == null) {
            homeView6 = new TextView(getContext());
            homeView6.setText(HomeFragment6.class.getName());
        }
        return homeView6;
    }

    @Override
    protected void viewCreated() {
        //ToastUtils.showToast(getActivity(),HomeFragment6.class.getName());
        //TODO 从服务器获取数据
    }
}
