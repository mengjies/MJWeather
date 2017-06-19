package com.mj.weather.account.contract;

import com.mj.weather.account.model.http.entity.UserBean;
import com.mj.weather.base.BasePresenter;
import com.mj.weather.base.BaseView;

import io.reactivex.Observer;

/**
 * Created by MengJie on 2017/2/18.
 */

public class LoginContract {

    public interface Presenter extends BasePresenter {

        void login(String username, String password);

        void saveLoginData(String username, String password, UserBean.RspLogin rspLogin);
    }

    public interface View extends BaseView<Presenter> {

        Observer<UserBean.RspLogin> loginObserver();
    }

}
