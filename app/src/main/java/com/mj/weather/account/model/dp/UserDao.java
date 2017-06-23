package com.mj.weather.account.model.dp;

import com.mj.weather.account.model.http.entity.UserBean;

import org.litepal.crud.DataSupport;

/**
 * Created by MengJie on 2017/6/20.
 */

public class UserDao {

    /**
     * 保存用户数据
     * @param username
     * @param password
     * @param rspLogin
     */
    public static void saveUserData(String username, String password, UserBean.RspLogin rspLogin) {
        User user = DataSupport.findFirst(User.class);
        if (user == null) {
            user = new User();
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setToken(rspLogin.result.token);
        user.setUid(rspLogin.result.uid);
        user.save();
    }

}
