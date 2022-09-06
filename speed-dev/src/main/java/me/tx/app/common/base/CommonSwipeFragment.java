package me.tx.app.common.base;

import android.view.View;


import androidx.viewbinding.ViewBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import me.tx.app.ui.fragment.RefreshFragment;

public abstract class CommonSwipeFragment<VB extends ViewBinding> extends RefreshFragment<VB> {
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fragmentEvent(MainEvent mainEvent){

    }

    @Override
    public void setView(View view){
        EventBus.getDefault().register(this);
        super.setView(view);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
