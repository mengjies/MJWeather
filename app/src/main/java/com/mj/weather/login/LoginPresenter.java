package com.mj.weather.login;

import android.support.annotation.NonNull;

import static com.mj.weather.utils.Proconditions.checkNotNull;

/**
 * Created by MengJie on 2017/2/18.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mLoginView;

    public LoginPresenter(@NonNull LoginContract.View loginView) {
        mLoginView = checkNotNull(loginView);
        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
