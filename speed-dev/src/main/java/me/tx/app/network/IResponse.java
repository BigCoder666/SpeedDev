package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface IResponse<T> {

    void successObj(IData<T> t);
    void successArray(IListData<T> tList);
    void success(String str);

    void fail(String code,String msg);

    interface BadToken{
        void badtoken();
    }
}
