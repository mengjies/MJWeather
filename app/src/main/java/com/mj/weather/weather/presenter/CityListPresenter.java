package com.mj.weather.weather.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.mj.weather.WeatherApplicationLike;
import com.mj.weather.common.util.LocationManager;
import com.mj.weather.common.util.NetWorkUtils;
import com.mj.weather.common.util.ToastUtils;
import com.mj.weather.weather.contract.CityListContract;
import com.mj.weather.weather.model.dp.entity.CityItem;
import com.mj.weather.weather.model.repository.CityRepository;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MengJie on 2017/6/27.
 */

public class CityListPresenter implements CityListContract.Presenter {
    private CityListContract.View mCityView;
    private CityRepository mCityRepository;

    @Inject
    CityListPresenter(CityListContract.View mCityView, CityRepository mCityRepository) {
        this.mCityView = mCityView;
        this.mCityRepository = mCityRepository;

        mCityView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public List<CityItem> getCityListByParent(int parentId) {
        return mCityRepository.getCityListByParent(parentId);
    }

    @Override
    public List<String> getNameArrayList(List<CityItem> dataList) {
        return mCityRepository.getNameArrayList(dataList);
    }

    @Override
    public boolean hasChildren(int areaID) {
        return mCityRepository.hasChildren(areaID);
    }

    @Override
    public CityItem getCityById(int parentID) {
        return mCityRepository.getCityById(parentID);
    }

    @Override
    public void startLocation(Activity act) {
        if (!NetWorkUtils.isNetworkAvailable(WeatherApplicationLike.getContext())) {
            ToastUtils.showToast("网络异常");
            return;
        }
        String[] perms = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        };
        new RxPermissions(act)
                .request(perms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            LocationManager.startLocation();
                        }
                    }
                });
    }


}
