package com.mj.weather.account.contract;

import android.content.Context;

import com.mj.weather.account.model.dp.entity.CityItem;
import com.mj.weather.common.base.BasePresenter;
import com.mj.weather.common.base.BaseView;

import java.util.List;

/**
 * Created by MengJie on 2017/6/27.
 */

public class CityListContract {

    public interface Presenter extends BasePresenter {
        List<CityItem> getCityListByParent(int parentId);

        List<String> getNameArrayList(List<CityItem> dataList);

        boolean hasChildren(int areaID);

        CityItem getCityById(int parentID);

    }

    public interface View extends BaseView<Presenter> {
    }


}
