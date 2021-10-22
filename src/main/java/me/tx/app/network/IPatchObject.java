package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IPatchObject extends IPatch {

    public IPatchObject(BaseActivity activity) {
        super(activity);
    }

    public IPatchObject(BaseActivity activity, boolean load) {
        super(activity,load);
    }

    @Override
    public void sucArray(JSONArray tList) {

    }

    @Override
    public HttpBuilder.RESPONSE_TYPE getResponseType() {
        return HttpBuilder.RESPONSE_TYPE.OBJECT;
    }
}
