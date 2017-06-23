package com.mj.weather.account.module;

import com.mj.weather.account.contract.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MengJie on 2017/6/21.
 *
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
