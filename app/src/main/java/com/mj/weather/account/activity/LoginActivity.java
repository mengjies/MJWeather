package com.mj.weather.account.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.mj.weather.SampleApplicationLike;
import com.mj.weather.R;
import com.mj.weather.account.component.DaggerLoginComponent;
import com.mj.weather.account.module.LoginViewModule;
import com.mj.weather.account.presenter.LoginPresenter;
import com.mj.weather.account.view.LoginFragment;
import com.mj.weather.common.base.BaseActivity;
import com.mj.weather.common.util.ActivityUtils;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    @Inject
    LoginPresenter loginPresenter;


    public static void actionStart(Activity act) {
        Intent intent = new Intent(act, LoginActivity.class);
        act.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("登录");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //加载view
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.loginFrame);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.loginFrame);
        }

        //dagger build
        DaggerLoginComponent.builder()
                .loginViewModule(new LoginViewModule(loginFragment))
                .userRepositoryComponent(SampleApplicationLike.getInstance().getUserRepositoryComponent())
                .build()
                .inject(this);

    }

    //后退
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
