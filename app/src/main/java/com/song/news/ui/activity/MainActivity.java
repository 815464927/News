package com.song.news.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.song.news.R;
import com.song.news.ui.adapter.HomePagerAdapter;
import com.song.news.ui.fragment.HomeFragment;
import com.song.news.ui.fragment.VideoFragment;
import com.song.news.ui.fragment.HomeFragment2;
import com.song.news.ui.fragment.HomeFragment3;
import com.song.news.ui.fragment.HomeFragment4;
import com.song.news.ui.fragment.HomeFragment5;
import com.song.news.ui.fragment.HomeFragment6;
import com.song.news.ui.fragment.HomeFragment7;
import com.song.news.ui.fragment.HomeFragment8;
import com.song.news.ui.fragment.HomeFragment9;
import com.song.news.ui.widget.ColorTrackTabViewIndicator;
import com.song.news.ui.widget.ColorTrackView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView publicTitle;
    private ColorTrackTabViewIndicator tab;
    private ViewPager vp;
    private String[] titles = new String[]{"推荐", "视频", "热点",
            "社会", "娱乐", "科技", "汽车", "体育", "财经", "军事"};
            //"国际", "时尚", "游戏", "旅游", "历史", "探索", "美食", "育儿", "养生", "故事", "美文"};

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
    }

    private void initData() {
        publicTitle.setText("新闻导航");
        List<Fragment> fragments = new ArrayList<>();
        //初始化数据
        HomeFragment fragment = new HomeFragment();
        fragments.add(fragment);
        VideoFragment fragment1 = new VideoFragment();
        fragments.add(fragment1);
        HomeFragment2 fragment2 = new HomeFragment2();
        fragments.add(fragment2);
        HomeFragment3 fragment3 = new HomeFragment3();
        fragments.add(fragment3);
        HomeFragment4 fragment4 = new HomeFragment4();
        fragments.add(fragment4);
        HomeFragment5 fragment5 = new HomeFragment5();
        fragments.add(fragment5);
        HomeFragment6 fragment6 = new HomeFragment6();
        fragments.add(fragment6);
        HomeFragment7 fragment7 = new HomeFragment7();
        fragments.add(fragment7);
        HomeFragment8 fragment8 = new HomeFragment8();
        fragments.add(fragment8);
        HomeFragment9 fragment9 = new HomeFragment9();
        fragments.add(fragment9);


        vp.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), fragments, titles));
        tab.setTitles(titles, new ColorTrackTabViewIndicator.CorlorTrackTabBack() {
            @Override
            public void onClickButton(Integer position, ColorTrackView colorTrackView) {
                vp.setCurrentItem(position, false);
            }
        });
        final View tabChild = tab.getChildAt(0);
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        //重新测量
        tabChild.measure(w, h);
        //设置最小宽度，使其可以在滑动一部分距离
        tabChild.setMinimumWidth(tabChild.getMeasuredWidth() + tab.getTabWidth());

        vp.setOffscreenPageLimit(titles.length);
        tab.setupViewPager(vp);
    }

    public void addOncick(View view) {
        startActivity(new Intent(this,ChannelActivity.class));
    }

    public void floatingActionButtonOnclick(View view) {
        startActivity(new Intent(this,HeaderZoomActivity.class));
    }
}
