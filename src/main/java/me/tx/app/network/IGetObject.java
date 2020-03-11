package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IGetObject extends IGet {
    public IGetObject(BaseActivity activity) {
        super(activity);
    }

    public IGetObject(BaseActivity activity,boolean load) {
        super(activity,load);
    }

    @Override
    public void sucArray(JSONArray tList) {

    }

    @Override
    public boolean iamArray() {
        return false;
    }

    @Override
    public boolean iamObj() {
        return true;
    }
}
