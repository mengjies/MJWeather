package com.mj.weather.account.module;

import com.mj.weather.account.contract.SignInContract;

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
