package com.song.news.ui.activity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.song.news.R;
import com.song.news.utils.ToastUtils;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 用JieCaoVideoPlayer播放器播放视频
 * github地址为：https://github.com/lipangit/JieCaoVideoPlayer
 */

public class VideoActivity extends AppCompatActivity {

    //重力感应进入全屏
    private JCVideoPlayer.JCAutoFullscreenListener sensorEventListener;
    private SensorManager sensorManager;
    private String url = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
    private EditText mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
    }

    private void initView() {
        mUrl = (EditText)findViewById(R.id.et_url);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
    }

    public void start(View view){

        url = mUrl.getText().toString().trim();
        if(url.length()<=0) {
            ToastUtils.showToast(this,"请输检查播放地址是否正确");
            return;
        }

        //直接进入全屏播放
        JCVideoPlayerStandard.startFullscreen(this, JCVideoPlayerStandard.class, url, "");
    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            finish();
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();

        //重力感应配置
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重力感应配置
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
}
