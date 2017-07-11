package com.mj.weather.weather.module;

import com.mj.weather.weather.contract.SignInContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MengJie on 2017/6/23.
 */
@Module
public class SignInViewModule {

    private SignInContract.View mView;

    public SignInViewModule(SignInContract.View mView) {
        this.mView = mView;
    }

    @Provides
    SignInContract.View provideSignInContractView() {
        return mView;
    }
}
