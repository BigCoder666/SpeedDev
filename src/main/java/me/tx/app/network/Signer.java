package me.tx.app.network;

import java.util.ArrayList;

import me.tx.app.network.ParamList;
import me.tx.app.utils.MD5;


public class Signer {
    public static ParamList sign(ParamList paramList,String key){
        String sign ="";
        for(int i = 0;i<paramList.getParamList().size();i++){
            sign = sign+ paramList.getParamList().get(i).getValue();
        }
        sign = sign +key;
        return paramList.add("sign", MD5.md5(sign));
    }
}
