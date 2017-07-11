package com.mj.weather.weather.module;

import com.mj.weather.weather.contract.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MengJie on 2017/6/21.
 */
@Module
public class LoginViewModule {

    private LoginContract.View mView;

    public LoginViewModule(LoginContract.View mView) {
        this.mView = mView;
    }

    @Provides
    LoginContract.View provideLoginContractView() {
        return mView;
    }

}
