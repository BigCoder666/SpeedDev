package com.wewin.speeddev.demo;

import androidx.viewbinding.ViewBinding;

import com.wewin.speeddev.databinding.FragmentSimpleBinding;

import me.tx.app.common.base.CommonFragment;

/**
 * @author tx
 * @date 2022/9/6 14:11
 */
public class SimpleFragment extends CommonFragment<FragmentSimpleBinding> {
    @Override
    public FragmentSimpleBinding getVb() {
        return FragmentSimpleBinding.inflate(getLayoutInflater());
    }

    @Override
    public void load() {
        fvb.text.setText("asf asdfsa");
    }
}
