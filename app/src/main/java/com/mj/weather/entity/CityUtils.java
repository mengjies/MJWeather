package com.mj.weather.entity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MengJie on 2017/1/21.
 */

public class CityUtils {


    public static List<CityItem> getCityListByParent(int parentId) {
        return DataSupport.where("ParentID = ?", parentId+"").find(CityItem.class);
    }

    public static List<String> getNameArrayList(List<CityItem> list) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (CityItem city : list) {
            arrayList.add(city.getTheName());
        }
        return arrayList;
    }
}
