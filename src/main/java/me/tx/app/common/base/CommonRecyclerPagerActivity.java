package me.tx.app.common.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.viewbinding.ViewBinding;

import com.alibaba.fastjson.JSON;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;


import me.tx.app.R;
import me.tx.app.ui.activity.RecyclerPagerActivity;
import me.tx.app.utils.ShareGetter;

public abstract class CommonRecyclerPagerActivity<VB extends ViewBinding,T> extends RecyclerPagerActivity<VB,T> {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(MainEvent mainEvent) {

    }

    @Override
    public void badtoken() {
        MainEvent event = new MainEvent();
        event.name = "logout";
        event.param = "";
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int srcLayout() {
        return R.layout.load_layout;
    }

    @Override
    public int srcImgId() {
        return R.id.load_img;
    }

    @Override
    public int defultLoading() {
        return R.color.transparent;
    }

    @Override
    public int defultError() {
        return R.drawable.error;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public HashMap<String,String> getHeader(){
        HashMap<String,String> header = JSON.parseObject(new ShareGetter(this).ReadHeader("header"),HashMap.class);
        return header;
    }
}
