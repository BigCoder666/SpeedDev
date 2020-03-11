package me.tx.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.tx.app.ui.activity.BaseActivity;

public abstract class BaseFragment extends Fragment {

    public  boolean  waitLoad=false;

    public abstract int getViewId();

    public abstract void setView(View view);

    public abstract void load();

    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getViewId(),container,false);
        setView(view);
        if (!waitLoad)
        {
            load();
        }
        return view;
    }




    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    public View getView(){
        return view;
    }

    public BaseActivity getBaseActivity(){
        return (BaseActivity)getActivity();
    }

}
