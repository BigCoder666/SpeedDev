package me.tx.app.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IPost implements IResponse {
    HttpBuilder.JSON_FORM JF = HttpBuilder.JSON_FORM.JSON;
    public HttpBuilder.JSON_FORM getJF(){
        return JF;
    }
    public void changeJF(HttpBuilder.JSON_FORM jf){
        JF = jf;
    }

    boolean needLoad = true;

    public BaseActivity activity;

    public abstract void sucObj(JSONObject t);

    public abstract void sucArray(JSONArray tList);

    public abstract void failed(String code, String msg);

    public IPost(BaseActivity activity){
        this.activity=activity;
        if(needLoad) {
            activity.center.showLoad();
        }
    }

    public IPost(BaseActivity activity, boolean needLoad){
        this.activity = activity;
        this.needLoad = needLoad;
        if(needLoad) {
            activity.center.showLoad();
        }
    }

    public void setNeedLoad(boolean load){
        needLoad = load;
    }

    @Override
    public void fail(String code, String msg) {
        if(needLoad) {
            activity.center.dismissLoad();
        }
        failed(code,msg);
        if(IData.badtoken.contains(code)){
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
    public HttpBuilder.REQUEST_TYPE getRequestType(){
        return HttpBuilder.REQUEST_TYPE.POST;
    }
}
