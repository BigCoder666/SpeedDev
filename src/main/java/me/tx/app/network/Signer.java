package me.tx.app.network;

import java.util.ArrayList;

import me.tx.app.network.ParamList;
import me.tx.app.utils.MD5;


public class Signer {
    private static String pkey ="dcjzsign123";

    public static ParamList sign(ParamList paramList){
        return paramList.add("sign", MD5.md5(paramList.getRequestString() + pkey));
    }
}
