package com.mj.weather.weather.http;

import com.mj.weather.common.util.JsonUtils;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;


import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by MengJie on 2017/1/20.
 */

public class HttpUtils {
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private static String webUrl = "";
    private static OkHttpClient okHttpClient;
    private static HttpUtils httpUtils = new HttpUtils();

    public static HttpUtils getHttpUtils() {
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        return httpUtils;
    }

    public static HttpUtils getHttpsUtils() {
        okHttpClient = new OkHttpClient();
        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory(new InputStream[]{null});
        okHttpClient.newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .hostnameVerifier(SSLUtils.UnSafeHostnameVerifier)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        return httpUtils;
    }



    public void sendRequest(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public <T> void sendRequest(T obj, Callback callback) {
        String json = JsonUtils.toJson(obj);
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, json);
        Request request = new Request.Builder()
                .url(webUrl)
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public <T> void sendRequest(String url, T obj, Callback callback) {
        String json = JsonUtils.toJson(obj);
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
