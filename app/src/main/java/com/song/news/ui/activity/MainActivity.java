package com.song.news.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.song.news.R;
import com.song.news.base.BaseActivity;
import com.song.news.service.entity.NewsChannel;
import com.song.news.service.presenter.NewsChannalPresenter;
import com.song.news.service.view.NewsChannalView;
import com.song.news.ui.adapter.MainPagerAdapter;
import com.song.news.ui.fragment.NewsFragment;
import com.song.news.ui.widget.ColorTrackTabViewIndicator;
import com.song.news.ui.widget.ColorTrackView;
import com.song.news.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private NewsChannalPresenter mNewsChannalPresenter;
    private ColorTrackTabViewIndicator tab;
    private ViewPager vp;
    private ArrayList<String> titles = new ArrayList<>();//获取新闻频道列表
    private List<Fragment> fragments = new ArrayList<>();//管理fragment的list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
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
        //初始化Fragment
        fragments.clear();
        for (int i = 0;i<titles.size();i++) {
            NewsFragment frg = new NewsFragment();
            frg.setNewschannal(titles.get(i));
            fragments.add(frg);
        }

        vp.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tab.setTitles(titles, new ColorTrackTabViewIndicator.CorlorTrackTabBack() {
            @Override
            public void onClickButton(Integer position, ColorTrackView colorTrackView) {
                vp.setCurrentItem(position, false);
            }
        });

        vp.setOffscreenPageLimit(titles.size());
        tab.setupViewPager(vp);
    }

    public void floatingActionButtonOnclick(View view) {
        startActivity(new Intent(this,HeaderZoomActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this,AboutActivity.class));
                overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
                break;
            case R.id.action_settings:
                ToastUtils.showToast(this, "setting");
                break;
            case R.id.action_area:
                ToastUtils.showToast(this, "地区新闻");
                break;
            case R.id.action_clear:
                ToastUtils.showToast(this, "清除缓存");
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNewsChannalPresenter.onStop();
    }
}
