package com.makeid.apitest;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.makeid.apitest.databinding.ActivityTaskBinding;
import com.makeid.apitest.databinding.ItemTaskBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.tx.app.common.base.CommonHolder;
import me.tx.app.common.base.CommonRecyclerActivity;
import me.tx.app.common.base.MainEvent;
import me.tx.app.network.HttpBuilder;
import me.tx.app.network.IData;
import me.tx.app.network.IResponse;
import me.tx.app.network.Mapper;
import me.tx.app.utils.ShareGetter;

/**
 * @author tx
 * @date 2022/12/7 11:16
 */
public class TaskActivity extends CommonRecyclerActivity<ActivityTaskBinding, ItemTaskBinding, Task> {
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(MainEvent mainEvent) {
        if (mainEvent.name.equals("ADD_TASK")) {
            load();
        }
    }

    @Override
    public ActivityTaskBinding getVb() {
        return ActivityTaskBinding.inflate(getLayoutInflater());
    }

    @Override
    public ItemTaskBinding getHolderBinding(ViewGroup viewGroup, int type) {
        return ItemTaskBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
    }

    @Override
    public void setRefreshRecyclerActivity() {
        vb.actionbar.init(this, "测试任务");

        vb.create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskActivity.this, CreateTaskActivity.class));
            }
        });
    }

    @Override
    public void onBindViewHolder(CommonHolder<ItemTaskBinding> holder, Task object, int p) {
        holder.hvb.name.setText(getDataList().get(p).taskId + "-" + getDataList().get(p).taskName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runIndex = 0;
                runTask(p);
            }
        });
    }

    @Override
    public void load(int page, IResult iResult, boolean needClear) {
        loadFinish(new IDealList() {
            @Override
            public void dealList() {
                clearData();
                ShareGetter shareGetter = new ShareGetter(TaskActivity.this);
                String taskList = shareGetter.Read("task");
                if (taskList.isEmpty()) {
                    taskList = "[]";
                    shareGetter.Write("task", taskList);
                }
                addData(JSON.parseArray(taskList, Task.class));
            }
        });
    }

    private int runIndex = 0;

    public void runTask(int p) {
        Task task = getDataList().get(p);

        if (runIndex >= task.requestApiList.size()) {
            return;
        }

        for (int r = 0; r < task.requestApiList.get(runIndex).run; r++) {

            Task.Api api = task.requestApiList.get(runIndex);

            makeApi(task,api);

            boolean addIndex = r==task.requestApiList.get(runIndex).run-1;
            HttpBuilder httpBuilder = null;
            HttpBuilder.IRequestFunction iRequestFunction = null;
            switch (api.bodyType) {
                case "json": {
                    iRequestFunction = center.reqJson(task.host + api.apiUrl, api.requestBody);
                    break;
                }
                case "form": {
                    iRequestFunction = center.reqForm(task.host + api.apiUrl, api.requestBody);
                    break;
                }
            }
            switch (api.requestType) {
                case "post": {
                    httpBuilder = iRequestFunction.post();
                    break;
                }
                case "get": {
                    httpBuilder = iRequestFunction.get(api.getParams);
                    break;
                }
                case "patch": {
                    httpBuilder = iRequestFunction.patch();
                    break;
                }
                case "put": {
                    httpBuilder = iRequestFunction.put();
                    break;
                }
                case "delete": {
                    httpBuilder = iRequestFunction.delete();
                    break;
                }
            }
//            httpBuilder.call(new IResponse() {
//                @Override
//                public void successObj(IData t) {
//
//                }
//
//                @Override
//                public void successArray(IListData tList) {
//
//                }
//
//                @Override
//                public void success(String str) {
//                    JSONObject jsonObject = JSON.parseObject(str);
//
//                    if (jsonObject.getString(task.codeName).equals(task.successCode)) {
//                        if(api.headers!=null&&api.headers.size()!=0){
//                            HashMap<String, String> hashMap = new HashMap<>();
//                            for(String key:api.headers.keySet()) {
//                                hashMap.put((String) api.headers.get(key), jsonObject.getJSONObject(task.dataName).getString(key));
//                            }
//                            ShareGetter shareGetter = new ShareGetter(TaskActivity.this);
//                            shareGetter.Write("header", JSON.toJSONString(hashMap));
//                        }
//
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                        task.requestApiList.get(runIndex).apiResult = jsonObject;
//
//                        if(addIndex) {
//                            runIndex++;
//                        }
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                center.toast("接口："+api.apiUrl+"成功");
//                                runTask(p);
//                            }
//                        });
//                    } else {
//                        center.toast("下标" + runIndex + ":" + api.apiUrl + "接口报错" + jsonObject.getString(task.codeName));
//                    }
//                }
//
//                @Override
//                public void fail(String code, String msg) {
//                    center.toast("下标" + runIndex + ":" + api.apiUrl + "接口报错" + code);
//                }
//            });
        }
    }


    public void makeApi(Task task,Task.Api api){
        if(api.apiReplace!=null){
            for(String key:api.apiReplace.keySet()){
                api.apiUrl = api.apiUrl.replace(key,getAtAtAt(task,(String)api.apiReplace.get(key)));
            }
        }
        for(String key:api.requestBody.keySet()){
            if(api.requestBody.get(key) instanceof String && ((String)api.requestBody.get(key)).startsWith("@@@")){
                api.requestBody.put(key,getAtAtAt(task,((String)api.requestBody.get(key))));
            }
        }
        for(String key:api.getParams.keySet()){
            if(api.requestBody.get(key) instanceof String && ((String)api.getParams.get(key)).startsWith("@@@")){
                api.getParams.put(key,getAtAtAt(task,((String)api.getParams.get(key))));
            }
        }
    }

    public String getAtAtAt(Task task,String atatat){
        if(atatat.startsWith("@@@")){
            atatat = atatat.replace("@@@","");
            String[] builder = atatat.split("\\*");
            List<String> builderList = Arrays.asList(builder);
            if(builderList.size()>0){
                Task.Api api = task.requestApiList.get(Integer.parseInt(builderList.get(0)));
                JSONObject replaceResult = (JSONObject)api.apiResult;
                for(int i = 1 ; i< builderList.size();i++){
                    String type = builderList.get(i).split("-")[0];
                    String typekey = builderList.get(i).split("-")[1];
                    if(type.equals("get[]")){
                        if(i==builderList.size()-1){
                            return replaceResult.getJSONArray(typekey).getString(Integer.parseInt(builderList.get(i).split("-")[2]));
                        }else {
                            replaceResult = replaceResult.getJSONArray(typekey).getJSONObject(Integer.parseInt(builderList.get(i).split("-")[2]));
                        }
                    }else if(type.equals("get{}")){
                        if(i==builderList.size()-1){
                            return replaceResult.getString(typekey);
                        }else {
                            replaceResult = replaceResult.getJSONObject(typekey);
                        }
                    }else {
                        return "";
                    }
                }
                return "";
            }else {
                return "";
            }
        }else{
            return "";
        }
    }
}
