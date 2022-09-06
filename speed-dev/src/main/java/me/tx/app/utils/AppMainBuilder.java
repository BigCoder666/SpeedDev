package me.tx.app.utils;

import android.content.Context;

import androidx.multidex.MultiDex;

public class AppMainBuilder {
    Context context;
    public AppMainBuilder(Context applicationContext){
        context =applicationContext;
    }

    public void init(){
        MultiDex.install(context);
    }
}
