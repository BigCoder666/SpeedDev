package me.tx.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.tx.app.ui.activity.BaseActivity;

public abstract class ResumeFragment extends BaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
