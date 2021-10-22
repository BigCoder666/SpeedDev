package me.tx.app.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import me.tx.app.ui.activity.BaseActivity;

public abstract class BaseFragment extends Fragment {

    public boolean needLoad=true;

    public abstract int getViewId();

    public abstract void setView(View view);

    public abstract void load();

    View view;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(getViewId(),container,false);
        setView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        if (needLoad)
        {
            load();
        }
    }

    public View getView(){
        return view;
    }

    public BaseActivity getBaseActivity(){
        return (BaseActivity)getActivity();
    }

    public void toast(String toast){
        if(getBaseActivity()==null){
            return;
        }
        getBaseActivity().center.toast(toast);
    }

}
