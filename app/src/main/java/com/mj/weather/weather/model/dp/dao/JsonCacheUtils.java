package com.mj.weather.weather.model.dp.dao;

import com.mj.weather.weather.model.dp.entity.JsonCache;

import org.litepal.crud.DataSupport;

/**
 * Created by MengJie on 2017/2/4.
 */

public class JsonCacheUtils {

    public static JsonCache getJsonCache(String url) {
        return DataSupport.where("url=?", url).findFirst(JsonCache.class);
    }

    public static void save(String url, String json) {
        JsonCache cache = getJsonCache(url);
        if (cache == null) {
            cache = new JsonCache();
        }
        cache.setUrl(url);
        cache.setJson(json);
        cache.setLastModified(System.currentTimeMillis());
        cache.save();
    }
}
