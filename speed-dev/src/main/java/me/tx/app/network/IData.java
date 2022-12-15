package me.tx.app.network;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;

public class IData{
    public final static String ok="200";
    public final static List<String> badtoken=Arrays.asList("401");

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return code;
    }

    public String getData() {
        return result!=null?result:data!=null?data:null;
    }

    public String message="";
    public String code="555";
    public String result=null;
    public String data =null;
}
