package com.mj.weather.http;

import okhttp3.Callback;

import static com.baidu.location.h.j.p;


/**
 * Created by MengJie on 2017/2/3.
 */

public class UserProtocol {
    private static final String host = "http://apicloud.mob.com";

    private static final String key = "1b0e55b47d644";

    private static final String port_register = "/user/rigister";
    private static final String port_login = "/user/login";
    private static final String port_profile_put = "/user/profile/put";
    private static final String port_profile_query = "/user/profile/query";
    private static final String port_password_change = "/user/password/change";
    private static final String port_profile_del = "/user/profile/del";
    private static final String port_data_put = "/user/data/put";
    private static final String port_data_query = "/user/data/query";
    private static final String port_data_del = "/user/data/del";
    private static final String port_password_retrieve = "/user/password/retrieve";

    private static HttpUtils httpUtils = HttpUtils.getHttpUtils();
    private static String token;
    private static String uid;

    public static void register(String username, String password, String email, Callback callback) {
        String url = host + port_register + "?key=" + key + "&username=" + username + "&password=" + password + "&email=" + email;
        httpUtils.sendRequest(url, callback);
    }

    public static void login(String username, String password, Callback callback) {
        String url = host + port_login + "?key=" + key + "&username=" + username + "&password=" + password;
        httpUtils.sendRequest(url, callback);
    }

    public static void profilePut(String item, String value, Callback callback) {
        String url = host + port_profile_put + "?key=" + key + "?token=" + token + "&uid=" + uid + "&item=" + item + "&value=" + value;
        httpUtils.sendRequest(url, callback);
    }

    public static void profileQuery(String item, Callback callback) {
        String url = host + port_profile_query + "?key=" + key + "?token=" + token + "&uid=" + uid + "&item=" + item;
        httpUtils.sendRequest(url, callback);
    }

    public static void profileDel(String item, Callback callback) {
        String url = host + port_profile_del + "?key=" + key + "?token=" + token + "&uid=" + uid + "&item=" + item;
        httpUtils.sendRequest(url, callback);
    }

    public static void dataPut(String item, String value, Callback callback) {
        String url = host + port_data_put + "?key=" + key + "?token=" + token + "&uid=" + uid + "&item=" + item + "&value=" + value;
        httpUtils.sendRequest(url, callback);
    }

    public static void dataQuery(String item, Callback callback) {
        String url = host + port_data_query + "?key=" + key + "?token=" + token + "&uid=" + uid + "&item=" + item;
        httpUtils.sendRequest(url, callback);
    }

    public static void dataDel(String item, Callback callback) {
        String url = host + port_data_del + "?key=" + key + "?token=" + token + "&uid=" + uid + "&item=" + item;
        httpUtils.sendRequest(url, callback);
    }

    public static void passwordChange(String username, String oldPassword, String newPassword, Callback callback) {
        String url = host + port_password_change + "?key=" + key + "&username=" + username + "&oldPassword=" + oldPassword + "&newPassword=" + newPassword + "&mode=" + "1";
        httpUtils.sendRequest(url, callback);
    }

    public static void passwordReset(String username, String vcode, String newPassword, Callback callback) {
        String url = host + port_password_change + "?key=" + key + "&username=" + username + "&oldPassword=" + vcode + "&newPassword=" + newPassword + "&mode=" + "2";
        httpUtils.sendRequest(url, callback);
    }

    public static void passwordRetrieve(String username, Callback callback) {
        String url = host + port_password_retrieve + "?key=" + key + "&username=" + username;
        httpUtils.sendRequest(url, callback);
    }

    public static class Result {
        public String uid;
        public String token;
    }

    public static class RspRegister {
        public String retCode;
        public String msg;
        public String uid;
    }

    public static class RspLogin {
        public String retCode;
        public String msg;
        public Result result;
    }

    public static class RspProfilePut {
        public String retCode;
        public String msg;
    }

    public static class RspProfileQuery {
        public String retCode;
        public String msg;
        public String result;
    }

    public static class RspProfileDel {
        public String retCode;
        public String msg;
    }

    public static class RspDataPut {
        public String retCode;
        public String msg;
    }

    public static class RspDataQuery {
        public String retCode;
        public String msg;
        public String result;
    }

    public static class RspDataDel {
        public String retCode;
        public String msg;
    }

    public static class RspPasswordChange {
        public String retCode;
        public String msg;
    }

    public static class RspPasswordRetrieve {
        public String retCode;
        public String msg;
    }

}
