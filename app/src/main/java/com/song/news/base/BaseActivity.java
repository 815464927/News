package com.song.news.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.song.news.R;
import com.song.news.ui.widget.ProgressHUD;

public class BaseActivity extends AppCompatActivity {

    protected ProgressHUD mProgressHUD;//对话框进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

}
