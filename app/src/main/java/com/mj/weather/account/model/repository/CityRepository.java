package com.mj.weather.account.model.repository;

import com.mj.weather.account.model.dp.CityDao;
import com.mj.weather.account.model.dp.CityItem;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by MengJie on 2017/6/23.
 */
public class CityRepository {
    private static CityRepository ourInstance = new CityRepository();

    public static CityRepository getInstance() {
        return ourInstance;
    }

    private CityRepository() {
    }

    public List<CityItem> getCityListByParent(int parentId) {
        return CityDao.getCityListByParent(parentId);
    }

    public List<String> getNameArrayList(List<CityItem> dataList) {
        return CityDao.getNameArrayList(dataList);
    }

    public boolean hasChildren(int areaID) {
        return CityDao.hasChildren(areaID);
    }

    public CityItem getCityById(int areaID) {
        return CityDao.getCityById(areaID);
    }
}
