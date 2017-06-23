package com.mj.weather.account.model.dp;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MengJie on 2017/1/21.
 */

public class CityDao {

    public static List<CityItem> getCityListByParent(int parentId) {
        return DataSupport.where("ParentID = ?", parentId + "").find(CityItem.class);
    }

    public static List<String> getNameArrayList(List<CityItem> list) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (CityItem city : list) {
            arrayList.add(city.getTheName());
        }
        return arrayList;
    }

    public static boolean hasChildren(int areaID) {
        int count = DataSupport.where("ParentID = ?", areaID + "").count(CityItem.class);
        return count != 0;
    }

    public static CityItem getCityById(int areaID) {
        return DataSupport.where("AreaId = ?", areaID + "").findFirst(CityItem.class);
    }
}
