package me.tx.app.utils;

import android.view.View;

import me.tx.app.ui.activity.BaseActivity;


public abstract class MinuteClicklistener implements View.OnClickListener {
    long lastTime = 0;
    BaseActivity baseActivity;
    public MinuteClicklistener(BaseActivity activity){
        baseActivity = activity;
    }

    public void reset(){
        lastTime=0;
    }

    public abstract void click(View view);
    @Override
    public void onClick(View view) {
        if(System.currentTimeMillis()-lastTime>1000*60){
            lastTime = System.currentTimeMillis();
            click(view);
        }else {
            baseActivity.center.toast((60-(System.currentTimeMillis()-lastTime)/1000)+"秒后重试");
        }
    }
}
