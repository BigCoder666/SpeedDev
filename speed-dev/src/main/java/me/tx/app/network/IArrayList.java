package me.tx.app.network;

import com.alibaba.fastjson.JSONObject;

public abstract class IArrayList<T> implements IResponse<T> {
    @Override
    public void successObj(IData<T> t) {

    }
}
