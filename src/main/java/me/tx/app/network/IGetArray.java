package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IGetArray extends IGet {
    public IGetArray(BaseActivity activity) {
        super(activity);
    }

    public IGetArray(BaseActivity activity,boolean nl) {
        super(activity,nl);
    }

    @Override
    public void sucObj(JSONObject t){

    }

    @Override
    public boolean iamArray() {
        return true;
    }

    @Override
    public boolean iamObj() {
        return false;
    }
}
