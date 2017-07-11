package com.mj.weather.weather.contract;

import com.mj.weather.common.base.BasePresenter;
import com.mj.weather.common.base.BaseView;
import com.mj.weather.weather.model.http.entity.HeBean;

import io.reactivex.Observer;

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
