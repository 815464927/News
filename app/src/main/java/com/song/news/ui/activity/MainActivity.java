package com.song.news.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.song.news.R;
import com.song.news.service.entity.NewsChannel;
import com.song.news.service.presenter.NewsChannalPresenter;
import com.song.news.service.view.NewsChannalView;
import com.song.news.ui.adapter.HomePagerAdapter;
import com.song.news.ui.fragment.HomeFragment;
import com.song.news.ui.widget.ColorTrackTabViewIndicator;
import com.song.news.ui.widget.ColorTrackView;
import com.song.news.ui.widget.ProgressHUD;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressHUD mProgressHUD;//对话框进度条
    private NewsChannalPresenter mNewsChannalPresenter;
    private TextView publicTitle;
    private ColorTrackTabViewIndicator tab;
    private ViewPager vp;
    private ArrayList<String> titles = new ArrayList<>();//获取新闻频道列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        publicTitle = (TextView) findViewById(R.id.public_title);
        tab = (ColorTrackTabViewIndicator) findViewById(R.id.tab);
        vp = (ViewPager) findViewById(R.id.vp);
        final View tabChild = tab.getChildAt(0);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        tabChild.measure(w, h);//重新测量
        //设置最小宽度，使其可以在滑动一部分距离
        tabChild.setMinimumWidth(tabChild.getMeasuredWidth() + tab.getTabWidth());
    }

    private void initData() {
        publicTitle.setText("新闻导航");

        mNewsChannalPresenter = new NewsChannalPresenter(this);
        mNewsChannalPresenter.attachView(new NewsChannalView() {
            @Override
            public void onSucess(List<NewsChannel.ShowapiResBodyBean.ChannelListBean> newsChannal) {
                closeDialog();
                titles.clear();
                for(int i=0;i<newsChannal.size();i++){
                    titles.add(newsChannal.get(i).getName());
                }
                prepreAdapterViewPager();
            }

            @Override
            public void onError(String result) {
                closeDialog();
            }
        });
        //请求网络
        mNewsChannalPresenter.getNewsChannal();
        showDialog();

    }

    private void prepreAdapterViewPager(){
        List<Fragment> fragments = new ArrayList<>();
        //初始化数据
//        HomeFragment fragment = new HomeFragment();
//        fragments.add(fragment);
//        VideoFragment fragment1 = new VideoFragment();
//        fragments.add(fragment1);
//        HomeFragment2 fragment2 = new HomeFragment2();
//        fragments.add(fragment2);
//        HomeFragment3 fragment3 = new HomeFragment3();
//        fragments.add(fragment3);
//        HomeFragment4 fragment4 = new HomeFragment4();
//        fragments.add(fragment4);
//        HomeFragment5 fragment5 = new HomeFragment5();
//        fragments.add(fragment5);
//        HomeFragment6 fragment6 = new HomeFragment6();
//        fragments.add(fragment6);
//        HomeFragment7 fragment7 = new HomeFragment7();
//        fragments.add(fragment7);
//        HomeFragment8 fragment8 = new HomeFragment8();
//        fragments.add(fragment8);
//        HomeFragment9 fragment9 = new HomeFragment9();
//        fragments.add(fragment9);

        for (int i = 0;i<titles.size();i++) {
            HomeFragment frg = new HomeFragment();
            fragments.add(frg);
        }


        vp.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), fragments, titles));
        tab.setTitles(titles, new ColorTrackTabViewIndicator.CorlorTrackTabBack() {
            @Override
            public void onClickButton(Integer position, ColorTrackView colorTrackView) {
                vp.setCurrentItem(position, false);
            }
        });

        vp.setOffscreenPageLimit(titles.size());
        tab.setupViewPager(vp);
    }

    public void addOncick(View view) {
        startActivity(new Intent(this,ChannelActivity.class));
    }

    public void floatingActionButtonOnclick(View view) {
        startActivity(new Intent(this,HeaderZoomActivity.class));
    }

    /**
     * 显示进度条
     */
    protected void showDialog() {
        if (null == mProgressHUD) {
            mProgressHUD = ProgressHUD.show(this,
                    getString(R.string.loading), true, false, null);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNewsChannalPresenter.onStop();
    }
}
