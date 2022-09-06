package com.wewin.speeddev.demo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wewin.speeddev.R;
import com.wewin.speeddev.databinding.ActivitySimpleListBinding;
import com.wewin.speeddev.databinding.ItemSimpleTextBinding;

import java.util.Arrays;

import me.tx.app.common.base.CommonHolder;
import me.tx.app.common.base.CommonRecyclerActivity;
import me.tx.app.network.IArrayList;
import me.tx.app.network.IObject;
import me.tx.app.network.Mapper;

public class SimpleRecyclerActivity extends CommonRecyclerActivity<ActivitySimpleListBinding,ItemSimpleTextBinding,SimpleData> {

    @Override
    public ItemSimpleTextBinding getHolderBinding(ViewGroup viewGroup, int type) {
        return ItemSimpleTextBinding.inflate(LayoutInflater.from(viewGroup.getContext()),viewGroup,false);
    }

    @Override
    public void setRefreshRecyclerActivity() {
        vb.actionbar.init(this,"测试标题");
    }


    @Override
    public void onBindViewHolder(CommonHolder<ItemSimpleTextBinding> holder, SimpleData simpleData, int position) {
        holder.hvb.text.setText(simpleData.text);
        center.loadImg(R.drawable.null_view, holder.hvb.img);
    }

    @Override
    public void load(int page, IResult iResult, boolean needClear) {
        center.reqJson("http://47.106.22.169:9000/gzyzyapi/sys/loginNoCaptcha",
                new Mapper()
                        .add("username", "admin")
                        .add("password", Utils.setPassword("admin"))).post()
                .callObject(new IObject() {
                    @Override
                    public void successObj(JSONObject simpleData) {
//                        clearData();
//                        resetData(Arrays.asList(new SimpleData(), new SimpleData(), new SimpleData()));
                        loadFinish(new IDealList() {
                            @Override
                            public void dealList() {
                                addData(Arrays.asList(new SimpleData(), new SimpleData(), new SimpleData()));
                            }
                        });
                    }

                    @Override
                    public void fail(String code, String msg) {
                        center.toast(msg);
                        loadFinish();
                    }
                });
//        center.reqJson("http://47.106.22.169:9000/gzyzyapi/sys/loginNoCaptcha",
//                Arrays.asList("123", "345", "567")).post()
//                .callList(new IArrayList() {
//                    @Override
//                    public void successArray(JSONArray tList) {
//                        loadFinish();
//                    }
//
//                    @Override
//                    public void fail(String code, String msg) {
//                        center.toast(msg);
//                        loadFinish();
//                    }
//                });
    }

    @Override
    public ActivitySimpleListBinding getVb() {
        return ActivitySimpleListBinding.inflate(getLayoutInflater());
    }
}
