package com.mj.weather.weather.http;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MengJie on 2017/1/24.
 */

public class HeProtocol {
    //接口地址
    private static final String host = "https://free-api.heweather.com/v5/";
    private static final String key = "e6b6e4d9801047f5a475b7f1e08cab98";

    private static final String heIcon = "http://files.heweather.com/cond_icon/";

    /**
     * 7-10天预报
     * 最长10天天气预报数据（大客户可达14天），天气预报已经包含日出日落，月升月落等常规数据
     */
    public static final String port_forecast = "forecast";
    /**
     * 实况天气
     * 包括多种气象指数的实况天气，每小时更新
     */
    public static final String port_now = "Now";
    /**
     * 每小时预报（逐小时预报），最长10天
     */
    public static final String port_hourly = "hourly";
    /**
     * 生活指数
     * 目前提供7大生活指数，每三小时更新
     */
    public static final String port_suggestion = "suggestion";
    /**
     * 灾害预警
     * 为全国2560个城市灾害预警信息，包括台风、暴雨、暴雪、寒潮、大风、沙尘暴、高温、干旱、雷电、冰雹、
     * 霜冻、霾、道路结冰、寒冷、灰霾、雷电大风、森林火险、降温、道路冰雪、干热风、低温、冰冻等灾害类型。
     * 每15分钟更新一次，建议用户每30-60分钟获取一下信息。
     */
    public static final String port_alarm = "alarm";
    /**
     * 天气预报
     * 包括7-10天预报、实况天气、每小时天气、灾害预警、生活指数、空气质量，一次获取足量数据
     */
    public static final String port_weather = "weather";
    /**
     * 景点天气
     * 全国4A和5A级景点共2000＋的7天天气预报
     */
    public static final String port_scenic = "scenic";
    /**
     * 历史天气
     * 支持2010年1月1日至今的全国城市历史天气数据
     */
    public static final String port_historical = "historical";
    /**
     * 城市查询
     * 通过此接口获取城市信息，例如通过名称获取城市ID，建议使用城市ID获取天气数据，避免重名城市导致的混淆
     */
    public static final String port_search = "search";

    public static String getIconUrl(String code) {
        String url;
        url = heIcon + code + ".png";
        return url;
    }

    public static String getUrl(String port, String city) {
        String url;
        url = host + port + "?city=" + city + "&key=" + key;
        return url;
    }

    public static List<AirQuality> getAqiList(City city) {
        List<AirQuality> airList = new ArrayList<>();
        airList.add(new AirQuality("空气质量指数", city.getAqi()));
        airList.add(new AirQuality("空气质量", city.getQlty()));
        airList.add(new AirQuality("PM10", city.getPm10()));
        airList.add(new AirQuality("PM2.5", city.getPm25()));
        airList.add(new AirQuality("臭氧", city.getO3()));
        airList.add(new AirQuality("一氧化碳", city.getCo()));
        airList.add(new AirQuality("二氧化氮", city.getNo2()));
        airList.add(new AirQuality("二氧化硫", city.getSo2()));
        return airList;
    }

    public static List<Tip> getSuggestionList(Suggestion suggestion) {
        List<Tip> tipList = new ArrayList<>();
        tipList.add(new Tip("舒适度指数", suggestion.getComf().getBrf(), suggestion.getComf().getTxt()));
        tipList.add(new Tip("洗车指数", suggestion.getCw().getBrf(), suggestion.getCw().getTxt()));
        tipList.add(new Tip("穿衣指数", suggestion.getDrsg().getBrf(), suggestion.getDrsg().getTxt()));
        tipList.add(new Tip("感冒指数", suggestion.getFlu().getBrf(), suggestion.getFlu().getTxt()));
        tipList.add(new Tip("运动指数", suggestion.getSport().getBrf(), suggestion.getSport().getTxt()));
        tipList.add(new Tip("旅游指数", suggestion.getTrav().getBrf(), suggestion.getTrav().getTxt()));
        tipList.add(new Tip("紫外线指数", suggestion.getUv().getBrf(), suggestion.getUv().getTxt()));
        return tipList;
    }

    public static class Tip {
        private String name;
        private String brief;
        private String txt;

        public Tip(String name, String brief, String txt) {
            this.name = name;
            this.brief = brief;
            this.txt = txt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }

    public static class AirQuality {
        private String name;
        private String value;

        public AirQuality(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    public class City {
        private String aqi;

        private String co;

        private String no2;

        private String o3;

        private String pm10;

        private String pm25;

        private String qlty;

        private String so2;

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getAqi() {
            return this.aqi;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getCo() {
            return this.co;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getNo2() {
            return this.no2;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        public String getO3() {
            return this.o3;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm10() {
            return this.pm10;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getPm25() {
            return this.pm25;
        }

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }

        public String getQlty() {
            return this.qlty;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getSo2() {
            return this.so2;
        }

    }

    public class Aqi {
        private City city;

        public void setCity(City city) {
            this.city = city;
        }

        public City getCity() {
            return this.city;
        }

    }

    public class Update {
        private String loc;

        private String utc;

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getLoc() {
            return this.loc;
        }

        public void setUtc(String utc) {
            this.utc = utc;
        }

        public String getUtc() {
            return this.utc;
        }

    }

    public class Basic {
        private String city;

        private String cnty;

        private String id;

        private String lat;

        private String lon;

        private String prov;

        private Update update;

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return this.city;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public String getCnty() {
            return this.cnty;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLat() {
            return this.lat;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLon() {
            return this.lon;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public String getProv() {
            return this.prov;
        }

        public void setUpdate(Update update) {
            this.update = update;
        }

        public Update getUpdate() {
            return this.update;
        }

    }

    public class Cond {
        private String code;

        private String txt;

        private String code_d;

        private String code_n;

        private String txt_d;

        private String txt_n;

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getTxt() {
            return this.txt;
        }

        public void setCode_d(String code_d) {
            this.code_d = code_d;
        }

        public String getCode_d() {
            return this.code_d;
        }

        public void setCode_n(String code_n) {
            this.code_n = code_n;
        }

        public String getCode_n() {
            return this.code_n;
        }

        public void setTxt_d(String txt_d) {
            this.txt_d = txt_d;
        }

        public String getTxt_d() {
            return this.txt_d;
        }

        public void setTxt_n(String txt_n) {
            this.txt_n = txt_n;
        }

        public String getTxt_n() {
            return this.txt_n;
        }

    }

    public class Wind {
        private String deg;

        private String dir;

        private String sc;

        private String spd;

        public void setDeg(String deg) {
            this.deg = deg;
        }

        public String getDeg() {
            return this.deg;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getDir() {
            return this.dir;
        }

        public void setSc(String sc) {
            this.sc = sc;
        }

        public String getSc() {
            return this.sc;
        }

        public void setSpd(String spd) {
            this.spd = spd;
        }

        public String getSpd() {
            return this.spd;
        }

    }

    public class Now {
        private Cond cond;

        private String fl;

        private String hum;

        private String pcpn;

        private String pres;

        private String tmp;

        private String vis;

        private Wind wind;

        public void setCond(Cond cond) {
            this.cond = cond;
        }

        public Cond getCond() {
            return this.cond;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getFl() {
            return this.fl;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getHum() {
            return this.hum;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPcpn() {
            return this.pcpn;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getPres() {
            return this.pres;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getTmp() {
            return this.tmp;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getVis() {
            return this.vis;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Wind getWind() {
            return this.wind;
        }

    }

    public class Comf {
        private String brf;

        private String txt;

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getBrf() {
            return this.brf;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getTxt() {
            return this.txt;
        }

    }

    public class Cw {
        private String brf;

        private String txt;

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getBrf() {
            return this.brf;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getTxt() {
            return this.txt;
        }

    }

    public class Drsg {
        private String brf;

        private String txt;

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getBrf() {
            return this.brf;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getTxt() {
            return this.txt;
        }

    }

    public class Flu {
        private String brf;

        private String txt;

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getBrf() {
            return this.brf;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getTxt() {
            return this.txt;
        }

    }

    public class Sport {
        private String brf;

        private String txt;

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getBrf() {
            return this.brf;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getTxt() {
            return this.txt;
        }

    }

    public class Trav {
        private String brf;

        private String txt;

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getBrf() {
            return this.brf;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getTxt() {
            return this.txt;
        }

    }

    public class Uv {
        private String brf;

        private String txt;

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getBrf() {
            return this.brf;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getTxt() {
            return this.txt;
        }

    }

    public class Suggestion {
        private Comf comf;

        private Cw cw;

        private Drsg drsg;

        private Flu flu;

        private Sport sport;

        private Trav trav;

        private Uv uv;

        public void setComf(Comf comf) {
            this.comf = comf;
        }

        public Comf getComf() {
            return this.comf;
        }

        public void setCw(Cw cw) {
            this.cw = cw;
        }

        public Cw getCw() {
            return this.cw;
        }

        public void setDrsg(Drsg drsg) {
            this.drsg = drsg;
        }

        public Drsg getDrsg() {
            return this.drsg;
        }

        public void setFlu(Flu flu) {
            this.flu = flu;
        }

        public Flu getFlu() {
            return this.flu;
        }

        public void setSport(Sport sport) {
            this.sport = sport;
        }

        public Sport getSport() {
            return this.sport;
        }

        public void setTrav(Trav trav) {
            this.trav = trav;
        }

        public Trav getTrav() {
            return this.trav;
        }

        public void setUv(Uv uv) {
            this.uv = uv;
        }

        public Uv getUv() {
            return this.uv;
        }

    }

    public class Alarms {
        private String level;

        private String stat;

        private String title;

        private String txt;

        private String type;

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLevel() {
            return this.level;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public String getStat() {
            return this.stat;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getTxt() {
            return this.txt;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

    }

    public class Astro {
        private String mr;

        private String ms;

        private String sr;

        private String ss;

        public void setMr(String mr) {
            this.mr = mr;
        }

        public String getMr() {
            return this.mr;
        }

        public void setMs(String ms) {
            this.ms = ms;
        }

        public String getMs() {
            return this.ms;
        }

        public void setSr(String sr) {
            this.sr = sr;
        }

        public String getSr() {
            return this.sr;
        }

        public void setSs(String ss) {
            this.ss = ss;
        }

        public String getSs() {
            return this.ss;
        }

    }

    public class Tmp {
        private String max;

        private String min;

        public void setMax(String max) {
            this.max = max;
        }

        public String getMax() {
            return this.max;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMin() {
            return this.min;
        }

    }

    public class Daily_forecast {
        private Astro astro;

        private Cond cond;

        private String date;

        private String hum;

        private String pcpn;

        private String pop;

        private String pres;

        private Tmp tmp;

        private String vis;

        private Wind wind;

        public void setAstro(Astro astro) {
            this.astro = astro;
        }

        public Astro getAstro() {
            return this.astro;
        }

        public void setCond(Cond cond) {
            this.cond = cond;
        }

        public Cond getCond() {
            return this.cond;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return this.date;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getHum() {
            return this.hum;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPcpn() {
            return this.pcpn;
        }

        public void setPop(String pop) {
            this.pop = pop;
        }

        public String getPop() {
            return this.pop;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getPres() {
            return this.pres;
        }

        public void setTmp(Tmp tmp) {
            this.tmp = tmp;
        }

        public Tmp getTmp() {
            return this.tmp;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getVis() {
            return this.vis;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Wind getWind() {
            return this.wind;
        }

    }

    public class Hourly_forecast {
        private Cond cond;

        private String date;

        private String hum;

        private String pop;

        private String pres;

        private String tmp;

        private Wind wind;

        public void setCond(Cond cond) {
            this.cond = cond;
        }

        public Cond getCond() {
            return this.cond;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return this.date;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getHum() {
            return this.hum;
        }

        public void setPop(String pop) {
            this.pop = pop;
        }

        public String getPop() {
            return this.pop;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getPres() {
            return this.pres;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getTmp() {
            return this.tmp;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Wind getWind() {
            return this.wind;
        }

    }

    public class HeWeather5 {
        private List<Alarms> alarms = new ArrayList<>();

        private Aqi aqi;

        private Basic basic;

        private List<Daily_forecast> daily_forecast = new ArrayList<>();

        private List<Hourly_forecast> hourly_forecast = new ArrayList<>();

        private Now now;

        private String status;

        private Suggestion suggestion;

        public void setAlarms(List<Alarms> alarms) {
            this.alarms = alarms;
        }

        public List<Alarms> getAlarms() {
            return this.alarms;
        }

        public void setAqi(Aqi aqi) {
            this.aqi = aqi;
        }

        public Aqi getAqi() {
            return this.aqi;
        }

        public void setBasic(Basic basic) {
            this.basic = basic;
        }

        public Basic getBasic() {
            return this.basic;
        }

        public void setDaily_forecast(List<Daily_forecast> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public List<Daily_forecast> getDaily_forecast() {
            return this.daily_forecast;
        }

        public void setHourly_forecast(List<Hourly_forecast> hourly_forecast) {
            this.hourly_forecast = hourly_forecast;
        }

        public List<Hourly_forecast> getHourly_forecast() {
            return this.hourly_forecast;
        }

        public void setNow(Now now) {
            this.now = now;
        }

        public Now getNow() {
            return this.now;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

        public void setSuggestion(Suggestion suggestion) {
            this.suggestion = suggestion;
        }

        public Suggestion getSuggestion() {
            return this.suggestion;
        }

    }

    public class Weather {
        private List<HeWeather5> HeWeather5;

        public void setHeWeather5(List<HeWeather5> HeWeather5) {
            this.HeWeather5 = HeWeather5;
        }

        public List<HeWeather5> getHeWeather5() {
            return this.HeWeather5;
        }

    }

}
