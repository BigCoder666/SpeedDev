package me.tx.app.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import me.tx.app.R;

//swipe id 必须为 R.id.swipe
public abstract class RefreshResumeFragment extends ResumeFragment {

    public SwipeRefreshLayout swip;

    public abstract void setSwipFragment(View view);

    @Override
    public void setView(View view) {
        swip = view.findViewById(R.id.swipe);
        swip.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        setSwipFragment(view);
    }

    public void loadFinish(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swip.setRefreshing(false);
            }
        });
    }
}
