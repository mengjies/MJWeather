package com.mj.weather.account.model.http.entity;

import com.mj.weather.account.UserProtocol;

/**
 * Created by MengJie on 2017/6/19.
 */

public class UserBean {

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
        public UserProtocol.Result result;
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
