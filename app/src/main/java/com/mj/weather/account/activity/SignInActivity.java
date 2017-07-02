package com.mj.weather.account.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.mj.weather.WeatherApplicationLike;
import com.mj.weather.R;
import com.mj.weather.account.component.DaggerSignInComponent;
import com.mj.weather.account.module.SignInViewModule;
import com.mj.weather.account.presenter.SignInPresenter;
import com.mj.weather.account.view.SignInFragment;
import com.mj.weather.common.base.BaseActivity;
import com.mj.weather.common.common.ActivityUtils;

import javax.inject.Inject;

public class SignInActivity extends BaseActivity {
    private static final String TAG = "SignInActivity";
    private static final String CURRENT_STATE_KEY = "state_key";

    @Inject
    SignInPresenter signInPresenter;

    public static void actionStart(Activity act) {
        Intent intent = new Intent(act, SignInActivity.class);
        act.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("注册");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //view
        SignInFragment signInFragment = (SignInFragment) getSupportFragmentManager().findFragmentById(R.id.signInFrame);
        if (signInFragment == null) {
            signInFragment = SignInFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), signInFragment, R.id.signInFrame);
        }

        //dagger
        DaggerSignInComponent.builder()
                .signInViewModule(new SignInViewModule(signInFragment))
                .userRepositoryComponent(WeatherApplicationLike.getInstance().getUserRepositoryComponent())
                .build()
                .inject(this);

        //saveInstanceState
        if (savedInstanceState != null) {
            // do something
            String str = savedInstanceState.getString(CURRENT_STATE_KEY);

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(CURRENT_STATE_KEY, "something");
        super.onSaveInstanceState(outState, outPersistentState);
    }

}
