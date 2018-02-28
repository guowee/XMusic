package com.uowee.xmusic.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.uowee.xmusic.R;

/**
 * Created by GuoWee on 2018/2/28.
 */

public class SplashActivity extends AppCompatActivity {

    private Context mContext;

    private TextView mTitleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 移除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        mTitleView = findViewById(R.id.view_title);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.splash_anim);
        animation.setAnimationListener(new AnimationImpl());
        mTitleView.startAnimation(animation);

    }

    class AnimationImpl implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
