package me.tx.app.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

//继承多fragment
public abstract class BaseFragmentActivity extends BaseMultipFragmentActivity {

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
