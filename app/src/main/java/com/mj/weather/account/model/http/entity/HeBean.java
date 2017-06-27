package com.mj.weather.account.model.http.entity;


import com.mj.weather.account.model.http.HeProtocol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MengJie on 2017/6/19.
 */

public class HeBean {


    public static String getIconUrl(String code) {
        String url;
        url = HeProtocol.heIcon + code + ".png";
        return url;
    }

    public static List<HeBean.AirQuality> getAqiList(HeBean.City city) {
        List<HeBean.AirQuality> airList = new ArrayList<>();
        airList.add(new HeBean.AirQuality("空气质量指数", city.aqi));
        airList.add(new HeBean.AirQuality("空气质量", city.qlty));
        airList.add(new HeBean.AirQuality("PM10", city.pm10));
        airList.add(new HeBean.AirQuality("PM2.5", city.pm25));
        airList.add(new HeBean.AirQuality("臭氧", city.o3));
        airList.add(new HeBean.AirQuality("一氧化碳", city.co));
        airList.add(new HeBean.AirQuality("二氧化氮", city.no2));
        airList.add(new HeBean.AirQuality("二氧化硫", city.so2));
        return airList;
    }

    public static List<HeBean.Tip> getSuggestionList(HeBean.Suggestion suggestion) {
        List<HeBean.Tip> tipList = new ArrayList<>();
        tipList.add(new HeBean.Tip("舒适度指数", suggestion.comf.brf, suggestion.comf.txt));
        tipList.add(new HeBean.Tip("洗车指数", suggestion.cw.brf, suggestion.cw.txt));
        tipList.add(new HeBean.Tip("穿衣指数", suggestion.drsg.brf, suggestion.drsg.txt));
        tipList.add(new HeBean.Tip("感冒指数", suggestion.flu.brf, suggestion.flu.txt));
        tipList.add(new HeBean.Tip("运动指数", suggestion.sport.brf, suggestion.sport.txt));
        tipList.add(new HeBean.Tip("旅游指数", suggestion.trav.brf, suggestion.trav.txt));
        tipList.add(new HeBean.Tip("紫外线指数", suggestion.uv.brf, suggestion.uv.txt));
        return tipList;
    }

    public class RspWeather {
        public List<HeWeather5> HeWeather5;

    }

    public static class Tip {
        public String name;
        public String brief;
        public String txt;

        public Tip(String name, String brief, String txt) {
            this.name = name;
            this.brief = brief;
            this.txt = txt;
        }
    }

    public static class AirQuality {
        public String name;
        public String value;

        public AirQuality(String name, String value) {
            this.name = name;
            this.value = value;
        }

    }


    public class City {
        public String aqi;

        public String co;

        public String no2;

        public String o3;

        public String pm10;

        public String pm25;

        public String qlty;

        public String so2;

    }

    public class Aqi {
        public HeBean.City city;

    }

    public class Update {
        public String loc;

        public String utc;

    }

    public class Basic {
        public String city;

        public String cnty;

        public String id;

        public String lat;

        public String lon;

        public String prov;

        public HeBean.Update update;

    }

    public class Cond {
        public String code;

        public String txt;

        public String code_d;

        public String code_n;

        public String txt_d;

        public String txt_n;

    }

    public class Wind {
        public String deg;

        public String dir;

        public String sc;

        public String spd;

    }

    public class Now {
        public HeBean.Cond cond;

        public String fl;

        public String hum;

        public String pcpn;

        public String pres;

        public String tmp;

        public String vis;

        public HeBean.Wind wind;

    }

    public class Comf {
        public String brf;

        public String txt;

    }

    public class Cw {
        public String brf;

        public String txt;

    }

    public class Drsg {
        public String brf;

        public String txt;

    }

    public class Flu {
        public String brf;

        public String txt;

    }

    public class Sport {
        public String brf;

        public String txt;

    }

    public class Trav {
        public String brf;

        public String txt;

    }

    public class Uv {
        public String brf;

        public String txt;

    }

    public class Suggestion {
        public HeBean.Comf comf;

        public HeBean.Cw cw;

        public HeBean.Drsg drsg;

        public HeBean.Flu flu;

        public HeBean.Sport sport;

        public HeBean.Trav trav;

        public HeBean.Uv uv;

    }

    public class Alarms {
        public String level;

        public String stat;

        public String title;

        public String txt;

        public String type;

    }

    public class Astro {
        public String mr;

        public String ms;

        public String sr;

        public String ss;

    }

    public class Tmp {
        public String max;

        public String min;

    }

    public class Daily_forecast {
        public HeBean.Astro astro;

        public HeBean.Cond cond;

        public String date;

        public String hum;

        public String pcpn;

        public String pop;

        public String pres;

        public HeBean.Tmp tmp;

        public String vis;

        public HeBean.Wind wind;

    }

    public class Hourly_forecast {
        public HeBean.Cond cond;

        public String date;

        public String hum;

        public String pop;

        public String pres;

        public String tmp;

        public HeBean.Wind wind;

    }

    public class HeWeather5 {
        public List<HeBean.Alarms> alarms = new ArrayList<>();

        public HeBean.Aqi aqi;

        public HeBean.Basic basic;

        public List<HeBean.Daily_forecast> daily_forecast = new ArrayList<>();

        public List<HeBean.Hourly_forecast> hourly_forecast = new ArrayList<>();

        public HeBean.Now now;

        public String status;

        public HeBean.Suggestion suggestion;

    }
}
