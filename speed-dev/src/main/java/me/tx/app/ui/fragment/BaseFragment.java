package me.tx.app.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import me.tx.app.ui.activity.BaseActivity;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    public VB fvb;

    public boolean needLoad=true;

    public abstract VB getVb();

    public abstract void setView(View view);

    public abstract void load();

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        fvb = getVb();
        setView(fvb.getRoot());
        return fvb.getRoot();
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
