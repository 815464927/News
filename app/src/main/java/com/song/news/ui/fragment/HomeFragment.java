package com.song.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.news.R;
import com.song.news.base.BaseFragment;
import com.song.news.service.entity.NewsChannel;
import com.song.news.service.presenter.NewsChannalPresenter;
import com.song.news.service.view.NewsChannalView;

/**
 * Created by song on 2017/3/15.
 * Email：815464927@qq.com
 */

public class HomeFragment extends BaseFragment {

    private TextView tv;
    private View homeView=null;
    private NewsChannalPresenter mNewsChannalPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(homeView==null) {
            homeView = LayoutInflater.from(getContext()).inflate(R.layout.home_fragment,
                    container, false);
            initData();
            initView(homeView);
        }
        return homeView;
    }

    private void initView(View v){
        tv = (TextView) v.findViewById(R.id.tv_test);
    }

    private void initData() {
        mNewsChannalPresenter = new NewsChannalPresenter(getActivity());
        mNewsChannalPresenter.attachView(new NewsChannalView() {
            @Override
            public void onSucess(NewsChannel newsChannal) {
                Log.d("--->NewsChannel",newsChannal.toString());
                tv.setText(newsChannal.toString());
                //TODO 写适配页面
            }

            @Override
            public void onError(String result) {
                Log.d("--->NewsChannel",result);
            }
        });
    }

    @Override
    protected void viewCreated() {
        //请求网络
        mNewsChannalPresenter.getNewsChannal();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNewsChannalPresenter.onStop();
    }
}
