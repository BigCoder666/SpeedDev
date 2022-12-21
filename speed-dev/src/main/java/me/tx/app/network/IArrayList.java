package me.tx.app.network;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.EventBus;

import me.tx.app.common.base.MainEvent;

public abstract class IArrayList<T> implements IResponse<T> {
    @Override
    public void successObj(T t) {

    }

    @Override
    public void success(String str){

    }

    @Override
    public void badToken(){
        MainEvent mainEvent = new MainEvent();
        mainEvent.name = "BAD_TOKEN";
        EventBus.getDefault().post(mainEvent);
    }
}
