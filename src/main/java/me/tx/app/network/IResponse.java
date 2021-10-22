package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IResponse {
    HttpBuilder.JSON_FORM getJF();
    void successObj(JSONObject t);
    void successArray(JSONArray tList);

    HttpBuilder.RESPONSE_TYPE getResponseType();

    HttpBuilder.REQUEST_TYPE getRequestType();

    void fail(String code,String msg);

    interface BadToken{
        void badtoken();
    }
}
