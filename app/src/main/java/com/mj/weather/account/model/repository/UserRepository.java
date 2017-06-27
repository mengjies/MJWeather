package com.mj.weather.account.model.repository;

import android.os.Process;

import com.mj.weather.account.model.dp.UserDao;
import com.mj.weather.account.model.http.UserApiClient;
import com.mj.weather.account.model.http.UserProtocol;
import com.mj.weather.account.model.http.entity.UserBean;

import io.reactivex.Observable;

/**
 * Created by MengJie on 2017/6/19.
 */
public class UserRepository {


    /**
     * 注册
     *
     * @param username
     * @param password
     * @param email
     * @return
     */
    public Observable<UserBean.RspRegister> register(String username, String password, String email) {
        //判断网络

        //网络
        Observable<UserBean.RspRegister> rspRegister = UserApiClient.userService.register(UserProtocol.key, username, password, email);
        return rspRegister;
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    public Observable<UserBean.RspLogin> login(String username, String password) {
        String s = Process.myPid() + " - " + Process.myTid() + " - " + Process.myUid();
        //判断网络

        //网络
        Observable<UserBean.RspLogin> rspLogin = UserApiClient.userService.login(UserProtocol.key, username, password);
        return rspLogin;
    }

    /**
     * 保存User数据
     *
     * @param username
     * @param password
     * @param rspLogin
     */
    public void saveUserData(String username, String password, UserBean.RspLogin rspLogin) {
        UserDao.saveUserData(username, password, rspLogin);
    }
}
