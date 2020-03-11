package me.tx.app.ui;

import android.app.Application;

import me.tx.app.utils.AppMainBuilder;

public class BaseApplication extends Application {

    private AppMainBuilder appMainBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        appMainBuilder = new AppMainBuilder(getApplicationContext());
        appMainBuilder.init();
    }

}
