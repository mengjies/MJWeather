package com.mj.weather.account.contract;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.mj.weather.common.base.BasePresenter;
import com.mj.weather.common.base.BaseView;
import com.tbruyelle.rxpermissions2.Permission;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;

/**
 * Created by MengJie on 2017/6/23.
 */

public class SplashContract {

    public interface Presenter extends BasePresenter {
        void initCityDb(Context context);

        void initPermissions(FragmentActivity activity, String[] permissions);

        boolean isInitCityDb();
    }

    public interface View extends BaseView<Presenter> {
        CompletableObserver initDbObserver();

        Observer<? super Permission> initPermsObserver();
    }


}
