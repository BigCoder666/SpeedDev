package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IStringJson implements IResponse {

    boolean needLoad = false;

    public BaseActivity activity;

    public abstract void sucObj(JSONObject t);

    public abstract void failed(int code, String msg);

    public IStringJson(BaseActivity activity){
        this.activity = activity;
        if(needLoad) {
            activity.center.showLoad();
        }
    }

    public IStringJson(BaseActivity activity, boolean nl){
        this.needLoad = nl;
        this.activity = activity;
        if(needLoad) {
            activity.center.showLoad();
        }
    }

    public void setNeedLoad(boolean load){
        needLoad = load;
    }

    @Override
    public boolean iamGet(){
        return false;
    }

    @Override
    public void successArray(JSONArray list) {
        return;
    }

    @Override
    public boolean iamArray(){
        return false;
    }

    @Override
    public boolean iamObj(){
        return false;
    }

    @Override
    public boolean iamJson() {
        return true;
    }

    @Override
    public boolean iamForm() {
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
    public void successObj(JSONObject t){
        if(needLoad) {
            activity.center.dismissLoad();
        }
        sucObj(t);
    }
}
