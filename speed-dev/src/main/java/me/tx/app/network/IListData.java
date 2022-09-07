package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;

import java.util.Arrays;
import java.util.List;

public class IListData<T> {
    public final static String ok="200";
    public final static List<String> badtoken=Arrays.asList("401");

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return code;
    }

    public List<T> getData() {
        return result!=null?result:data!=null?data:null;
    }

    public String message="";
    public String code="555";
    public List<T> result=null;
    public List<T> data =null;
}
