package me.tx.app.common.base;

import android.view.View;

import androidx.viewbinding.ViewBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.tx.app.ui.fragment.BaseFragment;

public abstract class CommonFragment<VB extends ViewBinding> extends BaseFragment<VB> {
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fragmentEvent(MainEvent mainEvent){

    }

    @Override
    public void setView(View view){
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
