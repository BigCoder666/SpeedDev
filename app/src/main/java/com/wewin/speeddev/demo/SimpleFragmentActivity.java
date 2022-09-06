package com.wewin.speeddev.demo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.wewin.speeddev.R;
import com.wewin.speeddev.databinding.ActivitySimpleContainBinding;

import me.tx.app.common.base.CommonFragmentActivity;

/**
 * @author tx
 * @date 2022/9/6 13:52
 */
public class SimpleFragmentActivity extends CommonFragmentActivity<ActivitySimpleContainBinding> {
    @Override
    public void setView() {
        vb.actionbar.init(this,"asdfasd f f");
    }

    @Override
    public ActivitySimpleContainBinding getVb() {
        return ActivitySimpleContainBinding.inflate(getLayoutInflater());
    }

    @Override
    public int getContainerId() {
        return R.id.container;
    }

    @Override
    public Fragment getFragment() {
        return new SimpleFragment();
    }

    @Override
    public Bundle getBundle() {
        return null;
    }
}
