package com.makeid.apitest;

import android.view.View;

import androidx.viewbinding.ViewBinding;

import com.alibaba.fastjson.JSON;
import com.makeid.apitest.databinding.ActivityCreateTaskBinding;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import me.tx.app.common.base.CommonActivity;
import me.tx.app.common.base.MainEvent;
import me.tx.app.utils.OneClicklistener;
import me.tx.app.utils.ShareGetter;

/**
 * @author tx
 * @date 2022/12/7 11:55
 */
public class CreateTaskActivity extends CommonActivity<ActivityCreateTaskBinding> {
    @Override
    public void setView() {
        vb.actionbar.init(this, "导入json脚本", "  保存  ", new OneClicklistener() {
            @Override
            public void click(View view) {
                ShareGetter shareGetter = new ShareGetter(CreateTaskActivity.this);
                String taskListString = shareGetter.Read("task");
                List<Task> taskList = JSON.parseArray(taskListString, Task.class);

                try {
                    Task task = JSON.parseObject(vb.json.getText().toString(), Task.class);
                    boolean beenAdded = false;
                    for (int i = 0; i < taskList.size(); i++) {
                        if (taskList.get(i).taskId.equals(task.taskId)) {
                            taskList.set(i, task);
                            beenAdded = true;
                        }
                    }
                    if (!beenAdded) {
                        taskList.add(task);
                    }
                    shareGetter.Write("task", JSON.toJSONString(taskList));
                    MainEvent mainEvent = new MainEvent();
                    mainEvent.name = "ADD_TASK";
                    EventBus.getDefault().post(mainEvent);
                    finish();
                } catch (Exception e) {
                    center.toast("脚本格式错误");
                }
            }
        });

        try {
            //通过输入流获取文件信息
            InputStream is = getResources().getAssets().open("taskModel");
            //把输入流转换为字符流
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            //把字符流包装为BufferReader
            BufferedReader bfr = new BufferedReader(isr);
            String result = "";
            String in = "";
            while ((in = bfr.readLine()) != null) {
                result = result + in;
            }
            vb.json.setText(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ActivityCreateTaskBinding getVb() {
        return ActivityCreateTaskBinding.inflate(getLayoutInflater());
    }
}
