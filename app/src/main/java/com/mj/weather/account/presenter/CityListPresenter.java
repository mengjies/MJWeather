package com.mj.weather.account.presenter;

import com.mj.weather.account.contract.CityListContract;
import com.mj.weather.account.model.dp.CityItem;
import com.mj.weather.account.model.repository.CityRepository;

import java.util.List;

import javax.inject.Inject;

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
}
