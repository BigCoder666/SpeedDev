package me.tx.app.common.base;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import me.tx.app.ui.fragment.RefreshRecyclerFragment;
import me.tx.app.ui.widget.EmptyHolder;

public abstract class CommonRecyclerFragment<T extends EmptyHolder,K> extends RefreshRecyclerFragment<T,K> {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fragmentEvent(MainEvent mainEvent){

    }

    @Override
    public void setView(View view){
        ButterKnife.bind(this,view);
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

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}
