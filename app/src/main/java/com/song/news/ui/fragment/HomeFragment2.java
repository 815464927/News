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

public class HomeFragment2 extends BaseFragment {

    private TextView homeView2=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if(homeView2 == null) {
            homeView2 = new TextView(getContext());
            homeView2.setText(HomeFragment2.class.getName());
        }
        return homeView2;
    }

    @Override
    protected void viewCreated() {
        //ToastUtils.showToast(getActivity(),HomeFragment2.class.getName());
        //TODO 从服务器获取数据
    }
}
