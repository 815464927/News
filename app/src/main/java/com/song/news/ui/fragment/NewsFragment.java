package com.song.news.ui.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.melnykov.fab.FloatingActionButton;
import com.song.news.R;
import com.song.news.base.BaseFragment;
import com.song.news.listener.OnRcvScrollListener;
import com.song.news.service.entity.News;
import com.song.news.service.presenter.NewsPresenter;
import com.song.news.service.view.NewsView;
import com.song.news.ui.adapter.NewsFragmentAdapter;
import com.song.news.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by song on 2017/3/15.
 * Email：815464927@qq.com
 */

public class NewsFragment extends BaseFragment {

    private View homeView = null;
    private RecyclerView mRecyclerView;
    private NewsFragmentAdapter mAdapter;
    private NewsPresenter mNewsPresenter;
    private SwipeRefreshLayout mRefreshLayout;
    private FloatingActionButton mFButton;
    private ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mData = new ArrayList<>();

    //RecycleView是否正在刷新
    private boolean isRefreshing;
    private boolean isLoading;

    private int currentPage = 1;
    private int allPages = 0;
    private String Newschannal = "国内焦点";
    private PopupWindow popuView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (homeView == null) {
            homeView = LayoutInflater.from(getContext()).inflate(R.layout.news_fragment,
                    container, false);
            initView(homeView);
            registerLisener();
            initData();
        }
        return homeView;
    }

    private void registerLisener() {
        //设置下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isRefreshing) {
                    return;
                }
                isRefreshing = true;
                //网络请求
                mNewsPresenter.getNews(Newschannal, 1);
            }
        });
        //底部自动加载更多
        mRecyclerView.addOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                super.onBottom();
                if (!isLoading) {
                    isLoading = true;
                    //网络请求
                    if (currentPage < allPages) {
                        ++currentPage;
                        mNewsPresenter.getNews(Newschannal, currentPage);
                    } else {
                        ToastUtils.showToast(getActivity(), "已经没有新闻了喔");
                    }
                }
            }
        });
        //浮点按钮监听事件
        mFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFButton.hide(true);
                popuView.showAsDropDown(mFButton,100,100);
            }
        });
    }

    private void initView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        mFButton = (FloatingActionButton) v.findViewById(R.id.fb);

        //设置浮点按钮绑定RecyclerView
        mFButton.attachToRecyclerView(mRecyclerView);

        //初始化popuWindow
        popuView = new PopupWindow();
        View popu = LayoutInflater.from(getActivity()).inflate(R.layout.popu_view, null);
        initPopuView(popu);
        popuView.setAnimationStyle(R.style.popu_style);
        popuView.setContentView(popu);
        popuView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popuView.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popuView.setFocusable(true);
        popuView.setBackgroundDrawable(new ColorDrawable(00000000));
    }

    private void initPopuView(View view) {
        view.findViewById(R.id.go_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
                popuView.dismiss();
            }
        });
        view.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(getActivity(),"share");
                popuView.dismiss();
            }
        });
        view.findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(getActivity(),"save");
                popuView.dismiss();
            }
        });
    }

    private void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new NewsFragmentAdapter(getActivity(), mData);
        mRecyclerView.setAdapter(mAdapter);

        mNewsPresenter = new NewsPresenter(getActivity());
        mNewsPresenter.attachView(new NewsView() {
            @Override
            public void onSucess(ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean>
                                         listData, int allPage) {
                closeDialog();
                allPages = allPage;
                mData.clear();
                mData.addAll(listData);
                mAdapter.notifyDataSetChanged();
                //请求结束后处理
                mRefreshLayout.setRefreshing(false);
                isLoading = false;
                isRefreshing = false;
            }

            @Override
            public void onError(String result) {
                closeDialog();
                //请求结束后处理
                ToastUtils.showToast(getActivity(), "获取不到新闻，请刷新页面尝试");
                mRefreshLayout.setRefreshing(false);
                isLoading = false;
                isRefreshing = false;
            }
        });
    }

    public void setNewschannal(String newschannal) {
        Newschannal = newschannal;
    }

    @Override
    protected void viewCreated() {
        mNewsPresenter.getNews(Newschannal, 1);
        mData.clear();
        showDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNewsPresenter.onStop();
    }
}
