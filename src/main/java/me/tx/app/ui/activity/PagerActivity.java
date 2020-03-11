package me.tx.app.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import me.tx.app.ui.fragment.BaseFragment;

public abstract class PagerActivity extends BaseActivity {

    public abstract ArrayList<BaseFragment> setFragment();

    ArrayList<BaseFragment> baseFragments;

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
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


    public void setView(){
        baseFragments=setFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager =findViewById(getViewPagerId());
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
    }

    public abstract void onScroll(int i);

    public void setCurrentItem(int i){
        viewPager.setCurrentItem(i);
    }

    public void setCurrentItem(int i,boolean animation){
        viewPager.setCurrentItem(i,animation);
    }
    public int getCurrentItem(){
        return viewPager.getCurrentItem();
    }

    public abstract int getViewPagerId();

    public void refreshPagerChange(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pagerAdapter.notifyDataSetChanged();
            }
        });
    }

}

