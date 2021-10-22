package me.tx.app.network;

import com.alibaba.fastjson.JSONObject;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IDeleteArray extends IDelete {
    public IDeleteArray(BaseActivity activity) {
        super(activity);
    }

    public IDeleteArray(BaseActivity activity, boolean nl) {
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
