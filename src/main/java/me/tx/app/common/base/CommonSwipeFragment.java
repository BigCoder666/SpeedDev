package me.tx.app.common.base;

import android.view.View;



import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import me.tx.app.ui.fragment.RefreshFragment;

public abstract class CommonSwipeFragment extends RefreshFragment {
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fragmentEvent(MainEvent mainEvent){

    }

    @Override
    public void setView(View view){
        ButterKnife.bind(this,view);
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
