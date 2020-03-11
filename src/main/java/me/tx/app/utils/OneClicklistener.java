package me.tx.app.utils;

import android.view.View;

public abstract class OneClicklistener implements View.OnClickListener {
    long lastTime = 0;
    public abstract void click(View view);
    @Override
    public void onClick(View view) {
        if(System.currentTimeMillis()-lastTime>500){
            click(view);
            lastTime = System.currentTimeMillis();
        }
    }
}
