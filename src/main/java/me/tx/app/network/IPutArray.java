package me.tx.app.network;

import com.alibaba.fastjson.JSONObject;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IPutArray extends IPut {
    public IPutArray(BaseActivity activity) {
        super(activity);
    }

    public IPutArray(BaseActivity activity, boolean nl) {
        super(activity,nl);
    }

    @Override
    public void sucObj(JSONObject t){

    }

    @Override
    public HttpBuilder.RESPONSE_TYPE getResponseType() {
        return HttpBuilder.RESPONSE_TYPE.ARRAY;
    }
}
