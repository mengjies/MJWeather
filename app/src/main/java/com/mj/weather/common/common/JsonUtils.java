package com.mj.weather.common.common;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.google.gson.Gson;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    private static Gson gson = new Gson();

    /**
     * Object转换为Json
     */
    public static String toJson(Object obj) {
        String ret = "";
        ret = gson.toJson(obj);
        return ret;
    }

    /**
     * Object转换为Base64
     */
    public static String toBase64(Object obj) {
        String ret = "";
        ret = toJson(obj);
        ret = Base64.encodeToString(ret.getBytes(Charset.forName("UTF-8")), Base64.DEFAULT);
        return ret;
    }

    /**
     * Json转换为Object对象
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        T obj = gson.fromJson(json, clazz);
        return obj;
    }

    /**
     * Json转换为集合
     */
    @NonNull
    public static <T> List<T> toObjectArray(String json, Class<T[]> clazz) {
       /* java.lang.reflect.Type type=new TypeToken<T[]>(){}.getType();*/
        T[] arr = new Gson().fromJson(json, clazz);
        return Arrays.asList(arr);
    }
}
