package com.mj.weather.account.model.dp;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by MengJie on 2017/2/4.
 */

public class JsonCache extends DataSupport {
    @Column(unique = true)
    private String url;
    private String json;
    private Long lastModified;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }
}
