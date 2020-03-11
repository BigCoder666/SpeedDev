package me.tx.app.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.tx.app.ui.fragment.BaseFragment;

public abstract class RecyclerPagerActivity<T> extends BaseActivity {

    public abstract List<T> getData();

    public abstract BaseFragment setBaseFragmentWithBundle(T t,int p);

    public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
        List<T> datalist;

        public void changeData(int p, T t) {
            datalist.set(p, t);
        }

        public MyFragmentPagerAdapter(FragmentManager fm, List<T> list) {
            super(fm);
            this.datalist = list;
        }

        @Override
        public int getCount() {
            return datalist.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return setBaseFragmentWithBundle(datalist.get(arg0),arg0);
        }
    }

    public void changeData(int p, T t) {
        pagerAdapter.changeData(p, t);
    }

    public ViewPager viewPager;
    public MyFragmentPagerAdapter pagerAdapter;


    public void setView() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager = findViewById(getViewPagerId());
        pagerAdapter = new MyFragmentPagerAdapter(fragmentManager, getData());
        viewPager.setAdapter(pagerAdapter);
    }

    public void setCurrentItem(int i) {
        viewPager.setCurrentItem(i);
    }

    public void setCurrentItem(int i, boolean animation) {
        viewPager.setCurrentItem(i, animation);
    }

    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }

    public abstract int getViewPagerId();

    public void refreshPagerChange() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pagerAdapter.notifyDataSetChanged();
            }
        });
    }

}

