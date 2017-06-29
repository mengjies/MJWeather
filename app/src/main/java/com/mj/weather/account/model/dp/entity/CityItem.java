package com.mj.weather.account.model.dp.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by MengJie on 2017/1/20.
 */

public class CityItem extends DataSupport implements Serializable {
    //"AreaID":"0","TheName":"\u4e2d\u56fd","Pinyin":"zg","Level":"0","ParentID":"0"
    @Column(unique = true)
    private int AreaID;
    private String TheName;
    private String Pinyin;
    private int Level;
    private int ParentID;

    public String getTheName() {
        return TheName;
    }

    public void setTheName(String theName) {
        TheName = theName;
    }

    public int getAreaID() {
        return AreaID;
    }

    public void setAreaID(int areaID) {
        AreaID = areaID;
    }

    public String getPinyin() {
        return Pinyin;
    }

    public void setPinyin(String pinyin) {
        Pinyin = pinyin;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getParentID() {
        return ParentID;
    }

    public void setParentID(int parentID) {
        ParentID = parentID;
    }
}
