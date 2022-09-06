package me.tx.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ShareGetter {

    SharedPreferences sharedPreferences;

    public ShareGetter(Context context){
        sharedPreferences = context.getSharedPreferences(context.getPackageName(),context.getApplicationContext().MODE_PRIVATE);
    }

    public void clear(){
        sharedPreferences.edit().clear().commit();
    }

    public void clearKey(String key){
        sharedPreferences.edit().remove(key).commit();
    }

    public void Write(String key,String value){
        sharedPreferences.edit().putString(key,value).commit();
    }
    public void WriteSet(String key,HashSet<String> valueSet){
        sharedPreferences.edit().putStringSet(key,valueSet).commit();
    }
    public void Write(HashMap<String,String> map){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        for(String key:map.keySet()) {
            editor.putString(key, map.get(key));
        }
        editor.commit();
    }

    public String Read(String key){
        return sharedPreferences.getString(key,"");
    }

    public String ReadHeader(String key){
        return sharedPreferences.getString(key,"{}");
    }

    public HashSet<String> ReadSet(String key){
        return (HashSet<String>)sharedPreferences.getStringSet(key,new HashSet<String>());
    }

    public HashMap<String,String> Read(List<String> keys){
        HashMap<String,String> map =new HashMap();
        for(String key:keys){
            map.put(key,sharedPreferences.getString(key,""));
        }
        return map;
    }
}
