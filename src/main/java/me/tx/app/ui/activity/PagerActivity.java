package me.tx.app.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import me.tx.app.ui.fragment.BaseFragment;

public abstract class PagerActivity extends BaseActivity {

    int lastPosition = 0;

    public abstract int setPageLimit();

    public abstract int getStartPage();

    public abstract void changePage(int last,int now);

    public abstract ArrayList<BaseFragment> setFragment();

    public ArrayList<BaseFragment> baseFragments;

    public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return baseFragments.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return baseFragments.get(arg0);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        }
    }

    public ViewPager viewPager;
    MyFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        baseFragments=setFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        pagerAdapter =new MyFragmentPagerAdapter(fragmentManager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(setPageLimit());
        lastPosition = getStartPage();
        viewPager.setCurrentItem(getStartPage());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onScroll(position);
                changePage(lastPosition,position);
                lastPosition =position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void setView(){
        viewPager =findViewById(getViewPagerId());
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

