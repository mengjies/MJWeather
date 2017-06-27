package com.mj.weather.account.model.dp;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MengJie on 2017/1/21.
 */

public class CityDao {

    /**
     * @param parentId
     * @return
     */
    public static List<CityItem> getCityListByParent(int parentId) {
        return DataSupport.where("ParentID = ?", parentId + "").find(CityItem.class);
    }

    /**
     * @param list
     * @return
     */
    public static List<String> getNameArrayList(List<CityItem> list) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (CityItem city : list) {
            arrayList.add(city.getTheName());
        }
        return arrayList;
    }

    /**
     * @param areaID
     * @return
     */
    public static boolean hasChildren(int areaID) {
        int count = DataSupport.where("ParentID = ?", areaID + "").count(CityItem.class);
        return count != 0;
    }

    /**
     * @param areaID
     * @return
     */
    public static CityItem getCityById(int areaID) {
        return DataSupport.where("AreaId = ?", areaID + "").findFirst(CityItem.class);
    }

    /**
     * @param areaID
     * @return
     */
    public static String getCityNameById(int areaID) {
        CityItem city = getCityById(areaID);
        String cityName = "";
        if (city != null) {
            cityName = city.getTheName();
        }
        return cityName;
    }

    /**
     * @param cityItemList
     */
    public static void saveAll(List<CityItem> cityItemList) {
        DataSupport.saveAll(cityItemList);
    }

    /**
     * 是否需要初始化  已经初始化过就不需要了
     *
     * @return
     */
    public static boolean isInit() {
        int count = DataSupport.count(CityItem.class);
        return count == 0;
    }
}
