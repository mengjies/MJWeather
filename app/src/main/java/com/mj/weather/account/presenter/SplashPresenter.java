package com.mj.weather.account.presenter;

import android.support.v4.app.FragmentActivity;

import com.mj.weather.MyApplication;
import com.mj.weather.account.contract.SplashContract;
import com.mj.weather.account.model.repository.CityRepository;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MengJie on 2017/6/23.
 */

public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View mSplashView;
    private CityRepository mCityRepository;

    @Inject
    public SplashPresenter(SplashContract.View splashView, CityRepository cityRepository) {
        mSplashView = splashView;
        mCityRepository = cityRepository;

        mSplashView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    /**
     *
     */
    @Override
    public void initCityDb() {
        mCityRepository.isInitCityDb()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Boolean>() {
                    @Override
                    public boolean test(@NonNull Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                            mSplashView.onDbFinished();
                        }
                        return aBoolean;
                    }
                })
                .flatMap(new Function<Boolean, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Boolean aBoolean) throws Exception {
                        return mCityRepository.initCityDb(MyApplication.getContext());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSplashView.initDbObserver());

    }

    @Override
    public void initPermissions(FragmentActivity activity, String[] permissions) {
        new RxPermissions(activity)
                .request(permissions)
                .subscribe(mSplashView.initPermsObserver());
    }
}
