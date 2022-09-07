package com.wewin.speeddev.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.wewin.speeddev.R;
import com.wewin.speeddev.databinding.ActivitySimpleListBinding;
import com.wewin.speeddev.databinding.ItemSimpleTextBinding;
import com.wewin.spinner.CommonSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.tx.app.common.base.CommonHolder;
import me.tx.app.common.base.CommonRecyclerActivity;
import me.tx.app.network.IArrayList;
import me.tx.app.network.IData;
import me.tx.app.network.IListData;
import me.tx.app.network.IObject;
import me.tx.app.network.Mapper;
import me.tx.app.utils.OneClicklistener;

public class SimpleRecyclerActivity extends CommonRecyclerActivity<ActivitySimpleListBinding,ItemSimpleTextBinding,SimpleData> {

    @Override
    public ItemSimpleTextBinding getHolderBinding(ViewGroup viewGroup, int type) {
        return ItemSimpleTextBinding.inflate(LayoutInflater.from(viewGroup.getContext()),viewGroup,false);
    }

    @Override
    public void setRefreshRecyclerActivity() {
        vb.actionbar.init(this,"测试标题");
        vb.typespinner.setAdapter(new CommonSpinner.Adapter<SpinnerData>() {
            @Override
            public void click(SpinnerData nameId) {

            }

            @Override
            public ArrayList<SpinnerData> getData() {
                ArrayList<SpinnerData> spinnerDataArrayList = new ArrayList<>();
                spinnerDataArrayList.add(new SpinnerData("111","111"));
                spinnerDataArrayList.add(new SpinnerData("222","222"));
                spinnerDataArrayList.add(new SpinnerData("333","333"));
                return spinnerDataArrayList;
            }

            @Override
            public void onShow() {

            }
        },new SpinnerData("111","111"));
    }


    @Override
    public void onBindViewHolder(CommonHolder<ItemSimpleTextBinding> holder, SimpleData simpleData, int position) {
        holder.hvb.text.setText(simpleData.text);
        center.loadImg(R.drawable.null_view, holder.hvb.img);
        holder.itemView.setOnClickListener(new OneClicklistener() {
            @Override
            public void click(View view) {
                getImgWithListener(9, new IGetImgCallback() {
                    @Override
                    public void pathResult(List<String> result) {

                    }
                });
            }
        });
    }

    @Override
    public void load(int page, IResult iResult, boolean needClear) {
        center.reqJson("http://47.106.22.169:9000/gzyzyapi/sys/loginNoCaptcha",
                new Mapper()
                        .add("username", "admin")
                        .add("password", Utils.setPassword("admin"))).post()
                .callObject(new IObject<SimpleData>() {
                    @Override
                    public void successObj(IData<SimpleData> simpleData) {

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
//                .callList(new IArrayList<SimpleData>() {
//                    @Override
//                    public void successArray(IListData<SimpleData> tList) {
//
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
