package com.mj.weather.account.model.repository;

import com.mj.weather.account.model.http.UserApiClient;
import com.mj.weather.account.model.http.UserProtocol;
import com.mj.weather.account.model.http.entity.UserBean;

import io.reactivex.Observable;

/**
 * Created by MengJie on 2017/6/19.
 */

public class UserRepository {

    /**
     *
     * @param username
     * @param password
     * @param email
     * @return
     */
    public static Observable<UserBean.RspRegister> register(String username, String password, String email) {
        //判断网络

        //网络
        Observable<UserBean.RspRegister> rspRegister =  UserApiClient.userService.register(UserProtocol.key, username, password, email);
        return rspRegister;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public static Observable<UserBean.RspLogin> login(String username, String password) {
        //判断网络

        //网络
        Observable<UserBean.RspLogin> rspLogin = UserApiClient.userService.login(UserProtocol.key, username, password);
        return rspLogin;
    }

}
