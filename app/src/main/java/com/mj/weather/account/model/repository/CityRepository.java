package com.mj.weather.account.model.repository;

import android.content.Context;

import com.mj.weather.account.model.dp.dao.CityDao;
import com.mj.weather.account.model.dp.entity.CityItem;
import com.mj.weather.common.util.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

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

    /**
     * @param parentId
     * @return
     */
    public List<CityItem> getCityListByParent(int parentId) {
        return CityDao.getCityListByParent(parentId);
    }

    /**
     * @param dataList
     * @return
     */
    public List<String> getNameArrayList(List<CityItem> dataList) {
        return CityDao.getNameArrayList(dataList);
    }

    /**
     * @param areaID
     * @return
     */
    public boolean hasChildren(int areaID) {
        return CityDao.hasChildren(areaID);
    }

    /**
     * @param areaID
     * @return
     */
    public CityItem getCityById(int areaID) {
        return CityDao.getCityById(areaID);
    }

    /**
     * @param context
     * @return
     */
    public Observable<String> initCityDb(Context context) {
        //获取城市json对象
        String json = "";
        InputStream is = null;
        try {
            is = context.getAssets().open("CityList.JSON");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            json = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //获取城市集合
        List<CityItem> cityItemList = JsonUtils.toObjectArray(json, CityItem[].class);
        //添加到数据库
        CityDao.saveAll(cityItemList);

        return Observable.just("initDbFinish");
    }

    public Observable<Boolean> isInitCityDb() {
        return Observable.just(CityDao.isInit());
    }
}
