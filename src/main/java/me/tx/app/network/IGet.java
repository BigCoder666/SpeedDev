package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IGet implements IResponse {
    boolean needLoad = false;

    public BaseActivity activity;

    public abstract void sucObj(JSONObject t);

    public abstract void sucArray(JSONArray tList);

    public abstract void failed(int code, String msg);

    public IGet(BaseActivity activity){
        this.activity=activity;
        if(needLoad) {
            activity.center.showLoad();
        }
    }

    public IGet(BaseActivity activity,boolean needLoad){
        this.activity = activity;
        if(needLoad) {
            this.needLoad = needLoad;
            activity.center.showLoad();
        }
    }

    public void setNeedLoad(boolean load){
        needLoad = load;
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
    public void successObj(JSONObject t){
        if(needLoad) {
            activity.center.dismissLoad();
        }
        sucObj(t);
    }

    @Override
    public void successArray(JSONArray ts) {
        if(needLoad) {
            activity.center.dismissLoad();
        }
        sucArray(ts);
    }

    @Override
    public boolean iamJson() {
        return false;
    }

    @Override
    public boolean iamForm() {
        return false;
    }

    @Override
    public boolean iamGet() {
        return true;
    }
}
