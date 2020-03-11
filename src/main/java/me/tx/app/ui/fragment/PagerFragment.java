package me.tx.app.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

public abstract class PagerFragment extends BaseFragment {
    public abstract ArrayList<BaseFragment> setFragment();
    public abstract void load(View view);

    ArrayList<BaseFragment> baseFragments;

    public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<BaseFragment> list;
        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }


    }

    public ViewPager viewPager;
    MyFragmentPagerAdapter pagerAdapter;

    @Override
    public void load(){

    }


    @Override
    public void setView(View view){
        baseFragments=setFragment();
        FragmentManager fragmentManager = getChildFragmentManager();
        viewPager = view.findViewById(getViewPagerId());
        pagerAdapter =new MyFragmentPagerAdapter(fragmentManager,baseFragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onScroll(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        load(view);
    }

    public abstract void onScroll(int i);

    public void setCurrentItem(int i){
        viewPager.setCurrentItem(i);
    }
    public int getCurrentItem(){
        return viewPager.getCurrentItem();
    }

    public abstract int getViewPagerId();
}
