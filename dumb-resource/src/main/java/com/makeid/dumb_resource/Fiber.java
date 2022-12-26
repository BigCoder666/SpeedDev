package com.makeid.dumb_resource;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tx
 * @date 2022/12/23 10:13
 */
public class Fiber extends ViewPager {

    FragmentPagerAdapter fragmentPagerAdapter;

    List<Fragment> faceList = new ArrayList<>();

    public Fiber(@NonNull Context context) {
        super(context);
    }

    public Fiber(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void createFiberView(int faceCount, FragmentManager fm ,IFiberCreate iFiberCreate,List<IFiberFaceCreate> iFiberFaceCreates){
        for(int i = 0;i<faceCount;i++){
            faceList.add(Face.getInstance(iFiberFaceCreates.get(i),iFiberFaceCreates.get(i).getIDiscCreate()));
        }
        fragmentPagerAdapter = new FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return faceList.get(position);
            }

            @Override
            public int getCount() {
                return faceCount;
            }
        };
        setAdapter(fragmentPagerAdapter);
    }
}
