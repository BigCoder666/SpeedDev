package me.tx.app.network;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class HttpBuilder {
    public OkHttpClient mOkHttpClient = new OkHttpClient();

    public enum REQUEST_TYPE {JSON,FORM,GET}
    public enum RESPONSE_TYPE {ARRAY,OBJECT,STRING}

    public final String TOKEN="TokenAuth";
    public final int UNKNOW_ERROR = 999;
    public final int TIME_OUT = 998;
    public final int CONNECT_FAIL = 997;
    public final int JSON_ERROR = 996;

    public abstract REQUEST_TYPE getRequestType();
    public abstract RESPONSE_TYPE getResponseType();

    public void build(String url,ParamList paramList,IResponse iResponse){
        build(url,paramList,iResponse,getRequestType());
    }

    public void build(final String url,
                      final ParamList paramList,
                      final IResponse iResponse,REQUEST_TYPE type){
        Request request =null;
        final Request.Builder b= new Request.Builder();
        switch (type){
            case FORM:{
                FormBody.Builder builder = new FormBody.Builder();
                for (ParamList.IParam param : paramList.getParamList()) {
                    builder.add(param.getKey(), (String)param.getValue());
                }
                b.url(url);
                b.post(builder.build());
                request =b.build();
                break;
            }
            case JSON:{
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),paramList.getJsonString());
                b.url(url);
                b.post(body);
                if(paramList.containsKey(TOKEN)) {
                    b.addHeader("Authorization",(String)paramList.get(TOKEN).getValue());
                }
                request =b.build();
                break;
            }
            case GET:{
                String urlForm =url;
                if(paramList.getParamList().size()!=0){
                    urlForm= urlForm+"?";
                    for(ParamList.IParam iParam:paramList.getParamList()){
                        urlForm = urlForm + iParam.getKey()+"="+iParam.getValue()+"&";
                    }
                    urlForm = urlForm.substring(0,urlForm.length()-1);
                }

                Request.Builder builder =  new Request.Builder().url(urlForm);
                if(paramList.containsKey(TOKEN)) {
                    builder.addHeader("Authorization",(String)paramList.get(TOKEN).getValue());
                }
                request= builder.build();
            }
        }
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try
                {
                if (response.isSuccessful()) {
                    String body =response.body().string();
                    switch (getResponseType()){
                case STRING:{
                    JSONObject jsonObject =new JSONObject();
                    jsonObject.put("str",body);
                    Log.e(url+"[response]",jsonObject.toJSONString());
                    iResponse.successObj(jsonObject);
                    break;
                }
                case ARRAY:{
                    IData iData =JSON.parseObject(body,IData.class);
                    if(iData.getStatus()==IData.ok) {
                        if(iData.getData().isEmpty()){
                            iResponse.successArray(new JSONArray());
                            Log.e(url + "[response]", "empty string");
                        }else {
                            JSONArray arrayList;
                            try {
                                arrayList = JSON.parseArray(iData.getData());
                            } catch (JSONException e) {
                                iResponse.fail(JSON_ERROR, "错误的JSON");
                                Log.e(url + "[json error]", iData.getData());
                                e.printStackTrace();
                                return;
                            }
                            iResponse.successArray(arrayList);
                            Log.e(url + "[response]", arrayList.toJSONString());
                        }
                    }else {
                        iResponse.fail(iData.getStatus(),iData.getMessage());
                        Log.e(url+"[failed]",""+response.code());
                    }
                    break;
                }
                case OBJECT:{
                    IData iData =JSON.parseObject(body,IData.class);
                    if(iData.getStatus()==IData.ok) {
                        if(iData.getData().isEmpty()){
                            iResponse.successObj(new JSONObject());
                            Log.e(url + "[response]", "empty string");
                        }else {
                            JSONObject jsonObject;
                            try {
                                jsonObject = JSON.parseObject(iData.getData());
                            } catch (JSONException e) {
                                iResponse.fail(JSON_ERROR, "错误的JSON");/*                                                                                                                                                                                                                                                                           */
                                Log.e(url + "[json error]", iData.getData());
                                e.printStackTrace();
                                return;
                            }
                            iResponse.successObj(jsonObject);
                            Log.e(url + "[response]", jsonObject.toJSONString());
                        }
                    }else {
                        iResponse.fail(iData.getStatus(),iData.getMessage());
                        Log.e(url+"[failed]",""+response.code());
                    }
                    break;
                }
                default:{
                    iResponse.fail(response.code(), url+":"+response.code());
                    Log.e(url+"[unknown]",url+":"+response.code());
                    break;
                }
            }
        }else {
            iResponse.fail(response.code(), url+":"+response.code());
            Log.e(url+"[refused]",""+response.code());
        }
    }
                catch ( Exception ex)
    {
        Log.e("taskEx:",ex.getMessage());
    }
}

            @Override
            public void onFailure(Call call, IOException e) {
                try
                {
                    if(e instanceof ConnectException){
                        iResponse.fail(CONNECT_FAIL, "无法连接到服务器");
                    }else if(e instanceof SocketTimeoutException){
                        iResponse.fail(TIME_OUT, "网络貌似不是很好o(╥﹏╥)o");
                    }else {
                        iResponse.fail(UNKNOW_ERROR, "请求异常\n请检查网络");
                    }
                }
                catch (Exception ex)
                {

                }
            }
        });

    }
}
