package me.tx.app.common.base;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewbinding.ViewBinding;

import com.alibaba.fastjson.JSON;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;


import me.tx.app.R;
import me.tx.app.ui.activity.BaseRefreshRecyclerActivity;
import me.tx.app.utils.ShareGetter;

public abstract class CommonRecyclerActivity<VB extends ViewBinding,T extends ViewBinding,K> extends BaseRefreshRecyclerActivity<VB,T,K> {
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(MainEvent mainEvent) {

    }

    @Override
    public HashMap<String,String> getHeader(){
        HashMap<String,String> header = JSON.parseObject(new ShareGetter(this).ReadHeader("header"),HashMap.class);
        return header;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
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
    public ScrollConnectView scorllConnectView(){
        return null;
    }
}
