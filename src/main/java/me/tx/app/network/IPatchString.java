package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IPatchString extends IPatch {
    public IPatchString(BaseActivity activity) {
        super(activity);
    }

    public IPatchString(BaseActivity activity, boolean load) {
        super(activity,load);
    }

    @Override
    public void sucArray(JSONArray tList) {

    }

    @Override
    public HttpBuilder.RESPONSE_TYPE getResponseType() {
        return HttpBuilder.RESPONSE_TYPE.STRING;
    }
}
