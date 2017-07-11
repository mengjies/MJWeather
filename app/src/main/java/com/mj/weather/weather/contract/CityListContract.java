package com.mj.weather.weather.contract;

import android.app.Activity;

import com.mj.weather.weather.model.dp.entity.CityItem;
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

        void startLocation(Activity act);
    }

    public interface View extends BaseView<Presenter> {
    }


}
