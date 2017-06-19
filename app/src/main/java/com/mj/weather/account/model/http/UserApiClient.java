package com.mj.weather.account.model.http;

import com.mj.weather.account.model.http.service.UserService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MengJie on 2017/6/19.
 */

public class UserApiClient {

    public static UserService userService;

    public static void init() {
        userService = initService(UserService.class);
    }

    private static <T> T initService(Class<T> clazz) {
        Retrofit retorfit = new Retrofit.Builder()
                .baseUrl(UserProtocol.host)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retorfit.create(clazz);
    }

}
