package com.mj.weather.account.contract;

import android.support.v4.app.FragmentActivity;

import com.mj.weather.common.base.BasePresenter;
import com.mj.weather.common.base.BaseView;

import io.reactivex.Observer;

/**
 * Created by MengJie on 2017/6/23.
 */

public class SplashContract {

    public interface Presenter extends BasePresenter {
        void initCityDb();

        void initPermissions(FragmentActivity activity, String[] permissions);

    }

    public interface View extends BaseView<Presenter> {

        void onDbFinished();

        Observer<? super Object> initDbObserver();

        Observer<? super Boolean> initPermsObserver();
    }


}
