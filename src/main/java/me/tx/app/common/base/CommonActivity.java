package me.tx.app.common.base;

import android.content.Intent;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.ButterKnife;
import me.tx.app.R;
import me.tx.app.ui.activity.BaseActivity;
import me.tx.app.utils.ShareGetter;

public abstract class CommonActivity extends BaseActivity {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(MainEvent mainEvent) {

    }

    @Override
    public void badtoken() {
        MainEvent keepEvent = new MainEvent();
        keepEvent.name = "logout";
        keepEvent.param = "";
        EventBus.getDefault().post(keepEvent);
        finish();
    }

    @Override
    public HashMap<String,String> getHeader(){
        HashMap<String,String> header = JSON.parseObject(new ShareGetter(this).Read("header"),HashMap.class);
        return header;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void bindid() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void load() {

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
        return R.color.hint;
    }

    @Override
    public int defultError() {
        return R.drawable.error;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
