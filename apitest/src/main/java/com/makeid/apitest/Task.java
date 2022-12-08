package com.makeid.apitest;

import java.util.ArrayList;
import java.util.List;

import me.tx.app.network.Mapper;

/**
 * @author tx
 * @date 2022/12/7 11:45
 */
public class Task {
    public static class Api{
        public String apiUrl = "";
        public Mapper apiReplace = null;
        public Mapper requestBody = new Mapper();
        public Mapper getParams = new Mapper();
        public String requestType = "";
        public String bodyType = "";
        public int run = 1;
        public Mapper headers = null;

        public Object apiResult = new Object();
    }
    public String taskId = "";
    public String host ="";
    public String taskName = "";
    public String successCode = "200";
    public String codeName = "code";
    public String dataName = "data";
    public List<Task.Api> requestApiList = new ArrayList<>();
}
