package me.tx.app.ui.activity;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.List;

import me.tx.app.ui.fragment.BaseFragment;

public abstract class RecyclerPagerActivity<T> extends BaseActivity {
    int lastPosition = 0;

    public abstract void changePage(int last,int now);

    public abstract List<T> getData();

    public abstract BaseFragment setBaseFragmentWithBundle(T t,int p);

    public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
        List<T> datalist;

        HashMap<Integer,BaseFragment> fragmentHashMap =new HashMap<>();

        public BaseFragment getFragment(int p){
            return fragmentHashMap.get(p);
        }

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
            if(fragmentHashMap.get(arg0)==null) {
                fragmentHashMap.put(arg0,setBaseFragmentWithBundle(datalist.get(arg0), arg0));
                return fragmentHashMap.get(arg0);
            }else {
                return fragmentHashMap.get(arg0);
            }
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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changePage(lastPosition,position);
                lastPosition =position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    public void next(){
        setCurrentItem(lastPosition+1);
    }

    public void last(){
        setCurrentItem(lastPosition-1);
    }

}

