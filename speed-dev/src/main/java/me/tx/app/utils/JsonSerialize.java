package me.tx.app.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public abstract class JsonSerialize<T> {
    public abstract T instance();

    public T reserialize(String jsonString){
        if(jsonString==null||jsonString.isEmpty()){
            return (T)new JSONObject();
        }
        return (T)JSON.parseObject(jsonString,this.getClass());
    }

    public String sereialize(){
        String str = JSON.toJSONString(this);
        return str;
    }

    public void save(Context context){
        ShareGetter shareGetter =new ShareGetter(context);
        shareGetter.Write(this.getClass().getSimpleName(),sereialize());
    }

    public void clear(Context context){
        ShareGetter shareGetter =new ShareGetter(context);
        shareGetter.clearKey(this.getClass().getSimpleName());
    }

    public T read(Context context){
        ShareGetter shareGetter =new ShareGetter(context);
        String jsonString = shareGetter.Read(this.getClass().getSimpleName());
        if(jsonString.isEmpty()||(!jsonString.startsWith("{")&&!jsonString.startsWith("["))){
            return instance();
        }
        T t =reserialize(jsonString);
        if(t==null){
            return instance();
        }
        return reserialize(jsonString);
    }
}
