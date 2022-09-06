package com.wewin.speeddev.demo;

import android.util.Base64;

/**
 * @author tx
 * @date 2022/9/6 15:58
 */
public class Utils {
    public static String setPassword(String psw) {
        psw = Base64.encodeToString(psw.getBytes(), Base64.NO_WRAP | Base64.NO_PADDING | Base64.URL_SAFE);
        psw = Base64.encodeToString(psw.getBytes(), Base64.NO_WRAP | Base64.NO_PADDING | Base64.URL_SAFE);
        return psw;
    }
}
