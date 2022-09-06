package me.tx.app.ui.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import java.util.List;

import me.tx.app.ui.fragment.BaseFragment;

public abstract class BaseMultipFragmentActivity<VB extends ViewBinding> extends BaseActivity<VB> {
    //获取fragment的Container
    public abstract List<ContainerIdFragment> getFragmentToContainer();

    public static class ContainerIdFragment{
        private int containerId=0;
        private Fragment fragment;
        private Bundle bundle;

        public Fragment getFragment() {
            return fragment;
        }

        public Bundle getBundle() {
            return bundle;
        }

        public int getContainerId() {
            return containerId;
        }


        public ContainerIdFragment(int containerId, Fragment fragment,Bundle bundle){
            this.containerId=containerId;
            this.fragment=fragment;
            this.bundle =bundle;
        }
    }

    List<ContainerIdFragment> fragmentToContainer;

    FragmentManager fragmentManager;

    @Override
    public void load(){
        fragmentToContainer = getFragmentToContainer();
        fragmentManager = getSupportFragmentManager();
        showFragments();
    }

    public void showFragments() {
        if(fragmentToContainer==null||fragmentToContainer.size()==0) {
           return;
        }
        for(ContainerIdFragment cidfragment:fragmentToContainer){
            if(cidfragment.getBundle()!=null) {
                cidfragment.getFragment().setArguments(cidfragment.getBundle());
            }
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.replace(cidfragment.getContainerId(), cidfragment.getFragment());
            trans.commit();
        }
    }

    public void changeFragment(int id,BaseFragment baseFragment){
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.replace(id, baseFragment);
        trans.commit();
    }
}
