package me.tx.app.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class IObject<T> implements IResponse<T>{

    @Override
    public void successArray(IListData<T> tList) {

    }

    @Override
    public void success(String str){

    }
}
