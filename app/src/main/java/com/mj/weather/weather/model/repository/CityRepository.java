package com.mj.weather.weather.model.repository;

import android.content.Context;
import android.os.Process;

import com.mj.weather.weather.model.dp.dao.CityDao;
import com.mj.weather.weather.model.dp.entity.CityItem;
import com.mj.weather.common.util.JsonUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by MengJie on 2017/6/23.
 */
public class CityRepository {
    private static CityRepository ourInstance = new CityRepository();

    private CityRepository() {
    }

    public static CityRepository getInstance() {
        return ourInstance;
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
    public Observable<String> initCityDb(final Context context) {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Logger.d("pid:" + Process.myPid() + "\n" + "tid:" + Process.myTid() + "\n" + "uid:" + Process.myUid() + "\n");
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
                return "initDbFinish";
            }
        });
    }

    public Observable<Boolean> isInitCityDb() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Logger.d("pid:" + Process.myPid() + "\n" + "tid:" + Process.myTid() + "\n" + "uid:" + Process.myUid() + "\n");
                return CityDao.isInit();
            }
        });
    }
}
