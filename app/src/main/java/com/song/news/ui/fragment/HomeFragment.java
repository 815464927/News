package com.song.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.song.news.R;
import com.song.news.base.BaseFragment;
import com.song.news.service.entity.News;
import com.song.news.service.presenter.NewsPresenter;
import com.song.news.service.view.NewsView;
import com.song.news.ui.adapter.HomeFragmentAdapter;

import java.util.ArrayList;

/**
 * Created by song on 2017/3/15.
 * Email：815464927@qq.com
 */

public class HomeFragment extends BaseFragment {

    private View homeView=null;
    private RecyclerView mRecyclerView;
    private HomeFragmentAdapter mAdapter;
    private NewsPresenter mNewsPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(homeView==null) {
            homeView = LayoutInflater.from(getContext()).inflate(R.layout.home_fragment,
                    container, false);
            initView(homeView);
            initData();
        }
        return homeView;
    }

    private void initView(View v){
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
    }

    private void initData() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        mNewsPresenter = new NewsPresenter(getActivity());
        mNewsPresenter.attachView(new NewsView() {
            @Override
            public void onSucess(ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean>
                                         listData, int allPage) {
                closeDialog();
                // TODO 刷新适配器
                mAdapter = new HomeFragmentAdapter(getActivity(),listData);
                mRecyclerView.setAdapter(mAdapter);
            }


            @Override
            public void onError(String result) {
                closeDialog();
            }
        });
    }

    @Override
    protected void viewCreated() {
        mNewsPresenter.getNews("国内焦点",1);
        showDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNewsPresenter.onStop();
    }
}
