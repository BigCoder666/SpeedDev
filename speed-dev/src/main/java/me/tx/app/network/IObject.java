package me.tx.app.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import me.tx.app.common.base.MainEvent;

public abstract class IObject<T> implements IResponse<T>{

    @Override
    public void successArray(List<T> tList) {

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
