package com.song.news.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.song.news.R;
import com.song.news.dragCallBack.ItemDragHelperCallBack;
import com.song.news.entity.Channel;
import com.song.news.ui.adapter.ChannelAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChannelActivity extends AppCompatActivity implements
        ChannelAdapter.OnChannelDragListener{

    private Toolbar mToolBar;
    private TextView title;
    private RecyclerView mRecyclerView;

    private List<Channel> mDatas = new ArrayList<>();
    private ChannelAdapter mAdapter;
    private final String[] titles = new String[]{"推荐", "视频", "热点", "社会", "娱乐", "科技", "汽车",
            "体育", "财经", "军事", "国际", "时尚", "游戏", "旅游", "历史", "探索", "美食", "育儿", "养生",
            "故事", "美文"};
    private ItemTouchHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        initView();
        initToolBar();
        initData();
    }

    private void initData() {
        generateDatas();
        mAdapter = new ChannelAdapter(mDatas);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = mAdapter.getItemViewType(position);
                return itemViewType == Channel.TYPE_MY_CHANNEL ||
                        itemViewType == Channel.TYPE_OTHER_CHANNEL ? 1 : 4;
            }
        });
        ItemDragHelperCallBack callBack = new ItemDragHelperCallBack(this);
        mHelper = new ItemTouchHelper(callBack);
        mAdapter.setOnChannelDragListener(this);
        //attachRecyclerView
        mHelper.attachToRecyclerView(mRecyclerView);
    }

    //生成频道数据
    private void generateDatas() {
        mDatas.add(new Channel(Channel.TYPE_MY, "我的频道"));
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            mDatas.add(new Channel(Channel.TYPE_MY_CHANNEL, title));
        }
        mDatas.add(new Channel(Channel.TYPE_OTHER, "频道推荐"));
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            mDatas.add(new Channel(Channel.TYPE_OTHER_CHANNEL, title + "推荐"));
        }
    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolBar);
        title = (TextView)findViewById(R.id.public_title);
        title.setText("频道设置");
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onStarDrag(BaseViewHolder baseViewHolder) {
        //开始拖动
        mHelper.startDrag(baseViewHolder);
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
        Channel startChannel = mDatas.get(starPos);
        //先删除之前的位置
        mDatas.remove(starPos);
        //添加到现在的位置
        mDatas.add(endPos, startChannel);
        mAdapter.notifyItemMoved(starPos, endPos);
    }
}
