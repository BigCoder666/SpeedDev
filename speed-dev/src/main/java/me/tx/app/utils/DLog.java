package me.tx.app.utils;

import android.util.Log;

public class DLog {
    public static boolean log = true;

    public static void e(String type,String info){
        if(log) {
            Log.e(type, info);
        }
    }
}
