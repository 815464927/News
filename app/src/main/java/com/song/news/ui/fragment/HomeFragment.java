package com.song.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.news.R;
import com.song.news.base.BaseFragment;
import com.song.news.utils.ToastUtils;

/**
 * Created by song on 2017/3/15.
 * Email：815464927@qq.com
 */

public class HomeFragment extends BaseFragment {

    private View homeView=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(homeView==null) {
            homeView = LayoutInflater.from(getContext()).inflate(R.layout.home_fragment, container, false);
        }
        return homeView;
    }

    @Override
    protected void viewCreated() {
        ToastUtils.showToast(getActivity(),HomeFragment.class.getName());
    }
}
