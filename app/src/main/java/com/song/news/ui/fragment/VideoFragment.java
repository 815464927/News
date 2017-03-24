package com.song.news.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.song.news.R;
import com.song.news.base.BaseFragment;
import com.song.news.ui.activity.VideoActivity;

/**
 * Created by song on 2017/3/15.
 * Email：815464927@qq.com
 */

public class VideoFragment extends BaseFragment {

    private Button goPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment, container, false);
        registerLisener(view);
        return view;
    }

    private void registerLisener(View vie) {
        goPlayer = (Button) vie.findViewById(R.id.btn_go_player);
        goPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),VideoActivity.class));
            }
        });
    }

    @Override
    protected void viewCreated() {
        //ToastUtils.showToast(getActivity(),VideoFragment.class.getName());
        //TODO 从服务器获取数据
    }
}
