package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public interface IResponse {

    void successObj(JSONObject t);
    void successArray(JSONArray tList);

    void fail(String code,String msg);

    interface BadToken{
        void badtoken();
    }
}
