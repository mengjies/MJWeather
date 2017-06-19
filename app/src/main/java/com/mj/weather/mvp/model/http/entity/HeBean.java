package com.mj.weather.mvp.model.http.entity;

import com.mj.weather.http.HeProtocol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MengJie on 2017/6/19.
 */

public class HeBean {

    public class RspWeather {
        public List<HeProtocol.HeWeather5> HeWeather5;

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
        public HeProtocol.City city;

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

        public HeProtocol.Update update;

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
        public HeProtocol.Cond cond;

        public String fl;

        public String hum;

        public String pcpn;

        public String pres;

        public String tmp;

        public String vis;

        public HeProtocol.Wind wind;

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
        public HeProtocol.Comf comf;

        public HeProtocol.Cw cw;

        public HeProtocol.Drsg drsg;

        public HeProtocol.Flu flu;

        public HeProtocol.Sport sport;

        public HeProtocol.Trav trav;

        public HeProtocol.Uv uv;

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
        public HeProtocol.Astro astro;

        public HeProtocol.Cond cond;

        public String date;

        public String hum;

        public String pcpn;

        public String pop;

        public String pres;

        public HeProtocol.Tmp tmp;

        public String vis;

        public HeProtocol.Wind wind;

    }

    public class Hourly_forecast {
        public HeProtocol.Cond cond;

        public String date;

        public String hum;

        public String pop;

        public String pres;

        public String tmp;

        public HeProtocol.Wind wind;

    }

    public class HeWeather5 {
        public List<HeProtocol.Alarms> alarms = new ArrayList<>();

        public HeProtocol.Aqi aqi;

        public HeProtocol.Basic basic;

        public List<HeProtocol.Daily_forecast> daily_forecast = new ArrayList<>();

        public List<HeProtocol.Hourly_forecast> hourly_forecast = new ArrayList<>();

        public HeProtocol.Now now;

        public String status;

        public HeProtocol.Suggestion suggestion;

    }
}
