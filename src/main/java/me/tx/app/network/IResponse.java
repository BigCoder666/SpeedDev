package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IResponse {
    void successObj(JSONObject t);
    void successArray(JSONArray tList);
    boolean iamArray();
    boolean iamObj();
    boolean iamJson();
    boolean iamForm();
    boolean iamGet();
    void fail(int code,String msg);

    interface BadToken{
        void badtoken();
    }

    interface Guest{
        void guestLogin();
    }
}
