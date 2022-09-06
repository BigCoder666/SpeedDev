package me.tx.app.common.base;

import android.view.View;


import androidx.viewbinding.ViewBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import me.tx.app.ui.fragment.PagerFragment;

public abstract class CommonPagerFragment<VB extends ViewBinding> extends PagerFragment<VB> {
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fragmentEvent(MainEvent mainEvent){

    }

    @Override
    public int setPageLimit(){
        return 1;
    }

    @Override
    public void setView(View view){
        super.setView(view);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
