package com.mj.weather.utils;

import android.text.TextUtils;

/**
 * Created by Administrator on 2016/1/18.
 */
public class TxtCheckout {
    //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
    private static final String REGEX_MOBILE = "^[1][3578]\\d{9}";

    private static final String REGEX_PASSWORD = "^[a-z0-9A-Z_]{6,16}$";

    private static final String REGEX_ID_CARD = "^\\d{15}|\\d{17}[0123456789x]";

    private static final String REGEX_BANK_CARD = "^\\d{16}|\\d{19}";

    private static final String REGEX_POSTCODE = "^[1-9]\\d{5}";

    private static final String REGEX_POSITIVE_FLOAT = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*";

    private static final String REGEX_POSITIVE_INTEGER = "^[1-9]\\d*";

    private static final String REGEX_EMAIL = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\\\.][A-Za-z]{2,3}([\\\\.][A-Za-z]{2})?$";

    /**
     * 验证用户名
     */
    public static boolean isUsername(String username) {
        return !(TextUtils.isEmpty(username));
    }

    /**
     * 验证密码
     */
    public static boolean isPassword(String password) {
        if (TextUtils.isEmpty(password)) return false;
        else return password.matches(REGEX_PASSWORD);
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobile) {
        if (TextUtils.isEmpty(mobile)) return false;
        else return mobile.matches(REGEX_MOBILE);
    }

    /**
     *校验邮箱
     */
    public static boolean isEmail(String email) {
        if(TextUtils.isEmpty(email)) return false;
        else return email.matches(REGEX_EMAIL);
    }

    /**
     * 校验身份证号
     */
    public static boolean isIDCard(String IDCard) {
        if (TextUtils.isEmpty(IDCard)) return false;
        else return IDCard.matches(REGEX_ID_CARD);
    }

    /**
     * 校验银行卡号
     */
    public static boolean isBankCardNo(String bankCardNo) {
        if(TextUtils.isEmpty(bankCardNo)) return false;
        else return bankCardNo.matches(REGEX_BANK_CARD);
    }

    /**
     *校验邮编
     */
    public static boolean isPostcode(String postcode) {
        if(TextUtils.isEmpty(postcode)) return false;
        else return postcode.matches(REGEX_POSTCODE);
    }

    /**
     * 校验正浮点型
     */
    public static boolean isPositiveFloat(String number) {
        if(TextUtils.isEmpty(number)) return false;
        else return number.matches(REGEX_POSITIVE_FLOAT);
    }

    /**
     * 校验正整型
     */
    public static boolean isPositiveInteger(String number) {
        if (TextUtils.isEmpty(number)) return false;
        else return number.matches(REGEX_POSITIVE_INTEGER);
    }

}
