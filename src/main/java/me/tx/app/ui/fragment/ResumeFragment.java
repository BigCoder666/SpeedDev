package me.tx.app.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ResumeFragment extends BaseFragment {

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(getViewId(),container,false);
        setView(view);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        load();
    }
}
