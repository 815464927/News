package com.song.news.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.song.news.R;
import com.song.news.ui.widget.ProgressHUD;

/**
 * Created by song on 2017/3/7.
 * Email：815464927@qq.com
 * 懒加载 lazyload Base
 * 所谓的懒加载,其实也就是延迟加载,就是等到该页面的UI展示给用户时,
 * 再加载该页面的数据(从网络、数据库等),而不是依靠ViewPager预加载机制提前加载
 */


public abstract class BaseFragment extends Fragment {

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;
    //对话框进度条
    protected ProgressHUD mProgressHUD;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    private void lazyLoad() {
        //setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,
        // 必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            viewCreated();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }
    /** get Data from servers */
    protected abstract void viewCreated();

    /**
     * 显示进度条
     */
    protected void showDialog() {
        if (null == mProgressHUD) {
            mProgressHUD = ProgressHUD.show(getActivity(),
                    getString(R.string.loading), true, true, null);
        } else {
            mProgressHUD.show();
        }
    }

    /**
     * 显示对话框
     */
    protected void closeDialog() {
        if (null != mProgressHUD) {
            mProgressHUD.dismiss();
        }
    }

}
