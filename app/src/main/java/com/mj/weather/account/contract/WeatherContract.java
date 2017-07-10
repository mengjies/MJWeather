package com.mj.weather.account.contract;

import com.mj.weather.common.base.BasePresenter;
import com.mj.weather.common.base.BaseView;
import com.mj.weather.account.model.http.entity.HeBean;

import io.reactivex.Observer;
import io.reactivex.functions.Consumer;

/**
 * Created by MengJie on 2017/6/25.
 */

public class WeatherContract {

    public interface Presenter extends BasePresenter {
        void getWeather(String cityName, String districtName);

        void getWeatherCache();
    }

    public interface View extends BaseView<Presenter> {
        Observer<? super HeBean.RspWeather> weatherObserver();

        Observer<? super HeBean.RspWeather> weatherCacheObserver();

        void setCity(String city, String district);

        void stopRefresh();
    }


}
