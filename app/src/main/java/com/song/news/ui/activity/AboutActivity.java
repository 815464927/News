package com.song.news.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.news.R;
import com.song.news.base.BaseActivity;
import com.song.news.ui.widget.CircularImageView;

public class AboutActivity extends BaseActivity {

    //我再github的头像-_-
    private String imageUrl = "https://avatars2.githubusercontent.com/u/19726652?v=3&s=220";
    private CircularImageView iv;
    private Toolbar toobar;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        initData();
    }

    private void initView() {
        iv = (CircularImageView) findViewById(R.id.iv_logo);
        toobar = (Toolbar) findViewById(R.id.toolBar);
        title = (TextView) findViewById(R.id.public_title);
    }

    private void initData() {
        setSupportActionBar(toobar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText(getResources().getText(R.string.action_about));
        Glide.with(this).load(imageUrl).into(iv);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
        return true;
    }

}
