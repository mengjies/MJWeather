package com.mj.weather.weather.activity;

import android.os.Bundle;

import com.mj.weather.R;
import com.mj.weather.weather.component.DaggerSplashComponent;
import com.mj.weather.weather.module.SplashViewModule;
import com.mj.weather.weather.presenter.SplashPresenter;
import com.mj.weather.weather.view.fragment.SplashFragment;
import com.mj.weather.common.base.BaseActivity;
import com.mj.weather.common.util.ActivityUtils;

import javax.inject.Inject;

/**
 * 应用启动短暂白屏处理
 * 初始化权限请求
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";

    @Inject
    SplashPresenter splashPresenter;
    private SplashFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //addView
        fragment = (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.splashFrame);
        if (fragment == null) {
            fragment = SplashFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.splashFrame);
        }

        //dagger
        DaggerSplashComponent.builder()
                .splashViewModule(new SplashViewModule(fragment))
                .build()
                .inject(this);

    }
}
