package me.tx.app.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;


import me.tx.app.R;
import me.tx.app.ui.activity.PagerActivity;
import me.tx.app.utils.ShareGetter;

public abstract class CommonPagerActivity extends PagerActivity {

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
    public int setPageLimit(){
        return 1;
    }

    @Override
    public int getStartPage(){
        return 0;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    @Override
    public void changePage(int last,int now){
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
//        HtmlText.clearBitMap();
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
