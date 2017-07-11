package com.mj.weather.weather.contract;

import com.mj.weather.weather.model.http.entity.UserBean;
import com.mj.weather.common.base.BasePresenter;
import com.mj.weather.common.base.BaseView;

import io.reactivex.Observer;

/**
 * Created by MengJie on 2017/6/20.
 */

public class SignInContract {

    public interface Presenter extends BasePresenter {

        void register(String username, String password, String email);
    }

    public interface View extends BaseView<Presenter> {

        Observer<UserBean.RspRegister> registerObserver();
    }
}
