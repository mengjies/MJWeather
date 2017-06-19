package com.mj.weather.mvp.model.http;

import com.mj.weather.mvp.model.http.service.HeService;

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

    public static void init() {
        heService = initService(HeService.class);
    }

    private static <T> T initService(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HeProtocol.host)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


}
