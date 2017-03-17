package com.song.news.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.news.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

public class SplashActivity extends AppCompatActivity {

    private TextView mSkip;
    private ImageView iv;
    private Subscription mSubscription;
    private int countDownTime = 5;//倒计时间
    private String imageUrl = "https://timgsa.baidu.com/timg?image&quality=" +
            "80&size=b9999_10000&sec=1489570061101&di=ea02ec4ffcf82fa3" +
            "9fce56b295de30f0&imgtype=0&src=http%3A%2F%2Fcdnq.duitang." +
            "com%2Fuploads%2Fitem%2F201407%" +
            "2F27%2F20140727123545_jXhkT.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        loadData();
    }

    private void initView() {
        mSkip = (TextView) findViewById(R.id.tv_skip);
        iv = (ImageView) findViewById(R.id.iv_splash);
    }

    /**
     * 加载图片 开启倒计时
     */
    private void loadData() {
        mSubscription = countDown(countDownTime)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Glide.with(SplashActivity.this).load(imageUrl).into(iv);
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        //倒计结束
                        mSkip.setVisibility(View.INVISIBLE);
                        goMainActivity();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        //正在倒计时
                        mSkip.setText(TextUtils.concat(integer + "s跳过"));
                    }
                });

    }

    /**
     * 倒计时
     *
     * @param time 倒计时间
     * @return Observable
     */
    private Observable<Integer> countDown(int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long aLong) {
                        return countTime - aLong.intValue();
                    }
                }).take(time + 1);
    }


    public void onSkip(View view) {
        goMainActivity();
    }

    private void goMainActivity() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
