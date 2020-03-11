package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IArray implements IResponse {
    boolean needLoad = false;

    public BaseActivity activity;

    public abstract void sucArray(JSONArray ts);

    public abstract void failed(int code, String msg);

    public IArray(BaseActivity activity){
        this.activity = activity;
        if(needLoad) {
            activity.center.showLoad();
        }
    }

    public void setNeedLoad(boolean load){
        needLoad = load;
    }

    @Override
    public void successObj(JSONObject t) {
        return;
    }

    @Override
    public boolean iamGet(){
        return false;
    }

    @Override
    public boolean iamArray(){
        return true;
    }

    @Override
    public boolean iamObj(){
        return false;
    }

    @Override
    public void fail(int code, String msg) {
        if(needLoad) {
            activity.center.dismissLoad();
        }
        failed(code,msg);
        if(code==IData.badtoken){
            activity.badtoken();
        }
    }

    @Override
    public void successArray(JSONArray ts) {
        if(needLoad) {
            activity.center.dismissLoad();
        }
        sucArray(ts);
    }
}
