package com.mj.weather.account.model.http;

import com.mj.weather.account.model.http.service.HeService;
import com.mj.weather.account.model.http.service.UserService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MengJie on 2017/6/18.
 */

public class ApiClient {

    /**
     * HeService
     */
    public static HeService heService;
    public static UserService userService;

    public static void init() {
        heService = initService(HeService.class, HeProtocol.host);
        userService = initService(UserService.class, UserProtocol.host);
    }

    private static <T> T initService(Class<T> clazz, String host) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


}
