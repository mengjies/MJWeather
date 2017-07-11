package com.mj.weather.weather.model.http.service;

import com.mj.weather.weather.model.http.UserProtocol;
import com.mj.weather.weather.model.http.entity.UserBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MengJie on 2017/6/19.
 */

public interface UserService {

    /**
     * 注册
     *
     * @param key
     * @param username
     * @param password
     * @param email
     * @return
     */
    @GET(UserProtocol.port_register)
    Observable<UserBean.RspRegister> register(@Query("key") String key, @Query("username") String username, @Query("password") String password, @Query("email") String email);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @GET(UserProtocol.port_login)
    Observable<UserBean.RspLogin> login(@Query("key") String key, @Query("username") String username, @Query("password") String password);

    /**
     * @param key
     * @param token
     * @param uid
     * @param item
     * @param value
     * @return
     */
    @GET(UserProtocol.port_profile_put)
    Observable<UserBean.RspProfilePut> profilePut(@Query("key") String key, @Query("token") String token, @Query("uid") String uid, @Query("item") String item, @Query("value") String value);

    /**
     * @param key
     * @param token
     * @param uid
     * @param item
     * @return
     */
    @GET(UserProtocol.port_profile_query)
    Observable<UserBean.RspProfileQuery> profileQuery(@Query("key") String key, @Query("token") String token, @Query("uid") String uid, @Query("item") String item);

    /**
     * @param key
     * @param token
     * @param uid
     * @param item
     * @return
     */
    @GET(UserProtocol.port_profile_del)
    Observable<UserBean.RspProfileDel> profileDel(@Query("key") String key, @Query("token") String token, @Query("uid") String uid, @Query("item") String item);

    /**
     * @param key
     * @param token
     * @param uid
     * @param item
     * @param value
     * @return
     */
    @GET(UserProtocol.port_data_put)
    Observable<UserBean.RspDataPut> dataPut(@Query("key") String key, @Query("token") String token, @Query("uid") String uid, @Query("item") String item, @Query("value") String value);

    /**
     * @param key
     * @param token
     * @param uid
     * @param item
     * @return
     */
    @GET(UserProtocol.port_data_query)
    Observable<UserBean.RspDataQuery> dataQuery(@Query("key") String key, @Query("token") String token, @Query("uid") String uid, @Query("item") String item);

    /**
     * @param key
     * @param token
     * @param uid
     * @param item
     * @return
     */
    @GET(UserProtocol.port_data_del)
    Observable<UserBean.RspDataDel> dataDel(@Query("key") String key, @Query("token") String token, @Query("uid") String uid, @Query("item") String item);

    /**
     * @param key
     * @param username
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @GET(UserProtocol.port_password_change)
    Observable<UserBean.RspPasswordChange> passwordChange(@Query("key") String key, @Query("username") String username, @Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);

    /**
     * @param key
     * @param username
     * @param vcode
     * @param newPassword
     * @return
     */
    @GET(UserProtocol.port_password_retrieve)
    Observable<UserBean.RspPasswordRetrieve> passwordReset(@Query("key") String key, @Query("username") String username, @Query("vcode") String vcode, @Query("newPassword") String newPassword);

    /**
     * @param key
     * @param username
     * @return
     */
    @GET(UserProtocol.port_password_retrieve)
    Observable<UserBean.RspPasswordRetrieve> passwordRetrieve(@Query("key") String key, @Query("username") String username);

}
