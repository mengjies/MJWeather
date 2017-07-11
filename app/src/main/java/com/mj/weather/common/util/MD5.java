package com.mj.weather.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/1/1.
 */
public class MD5 {
    public static String encode(String rawStr) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(rawStr.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String ENCODE(String rawStr) {
        String ret = encode(rawStr);
        ret = ret.toUpperCase();
        return ret;
    }
}
