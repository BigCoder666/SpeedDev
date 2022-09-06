package me.tx.app.ui.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

//继承多fragment
public abstract class BaseFragmentActivity<VB extends ViewBinding> extends BaseMultipFragmentActivity<VB> {

    public abstract int getContainerId();
    public abstract Fragment getFragment();
    public abstract Bundle getBundle();

    @Override
    public List<ContainerIdFragment> getFragmentToContainer() {
        List<ContainerIdFragment> fragmentList =new ArrayList<>();
        fragmentList.add(new ContainerIdFragment(getContainerId(),getFragment(),getBundle()));
        return fragmentList;
    }
}
