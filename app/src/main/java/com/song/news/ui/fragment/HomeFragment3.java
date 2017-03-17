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

public class HomeFragment3 extends BaseFragment {

    private TextView homeView3=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if(homeView3 == null) {
            homeView3 = new TextView(getContext());
            homeView3.setText(HomeFragment3.class.getName());
        }
        return homeView3;
    }

    @Override
    protected void viewCreated() {
        //ToastUtils.showToast(getActivity(),HomeFragment3.class.getName());
        //TODO 从服务器获取数据
    }
}
