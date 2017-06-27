package com.mj.weather.account.contract;

import com.mj.weather.common.base.BasePresenter;
import com.mj.weather.common.base.BaseView;
import com.mj.weather.account.model.http.entity.HeBean;

import io.reactivex.Observer;

/**
 * Created by MengJie on 2017/6/25.
 */

public class WeatherContract {

    public interface Presenter extends BasePresenter {
        void getWeather(String cityName);

        void getWeatherCache();
    }

    public interface View extends BaseView<Presenter> {
        Observer<? super HeBean.RspWeather> weatherObserver();

        void setTitle(String city);
    }


}
