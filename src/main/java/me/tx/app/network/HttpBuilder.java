package me.tx.app.network;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import me.tx.app.utils.DLog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class HttpBuilder {

    JSON_FORM jf = JSON_FORM.JSON;

    public HttpBuilder change(JSON_FORM jf){
        this.jf = jf;
        return this;
    }

    public enum JSON_FORM {JSON,FORM}

    public static Set<Cookie> publicCookie = new HashSet<>();

    public OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                    publicCookie.addAll(list);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                    List<Cookie> cl = new ArrayList<>();
                    cl.addAll(publicCookie);
                    return cl;
                }
            })
            .build();

    public enum REQUEST_TYPE {POST, GET, PUT ,PATCH,DELETE}

    public enum RESPONSE_TYPE {ARRAY, OBJECT, STRING}

    public final String UNKNOW_ERROR = "999";
    public final String TIME_OUT = "998";
    public final String CONNECT_FAIL = "997";

    public abstract REQUEST_TYPE getRequestType();

    public abstract RESPONSE_TYPE getResponseType();

    public void build(String url, ParamList paramList, IResponse iResponse, HashMap<String, String> header) {
        build(url, paramList, iResponse, getRequestType(), header);
    }

    private void build(String url,
                       ParamList paramList,
                       final IResponse iResponse, REQUEST_TYPE type,
                       HashMap<String, String> header) {
        DLog.e("req",url+"\n"+paramList.getJsonString());
        Request request = null;
        Request.Builder b = new Request.Builder();
        for(String key:header.keySet()){
            b.header(key,header.get(key));
        }
        RequestBody requestBody=null;
        switch (jf){
            case JSON:{
                requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), paramList.getJsonString());
                break;
            }
            case FORM:{
                FormBody.Builder builder = new FormBody.Builder();
                for (ParamList.IParam param : paramList.getParamList()) {
                    builder.add(param.getKey(), (String) param.getValue());
                }
                requestBody = builder.build();
                break;
            }
        }
        switch (type) {
            case POST: {
                b.url(url);
                b.post(requestBody);
                request = b.build();
                break;
            }
            case GET: {
                String urlForm = url;
                if (paramList.getParamList().size() != 0) {
                    urlForm = urlForm + "?";
                    for (ParamList.IParam iParam : paramList.getParamList()) {
                        try {
                            urlForm = urlForm + iParam.getKey() + "=" + URLEncoder.encode((String)iParam.getValue(),"utf-8") + "&";
                        } catch (UnsupportedEncodingException e) {
                            urlForm = urlForm + iParam.getKey() + "=" + iParam.getValue() + "&";
                            e.printStackTrace();
                        }
                    }
                    urlForm = urlForm.substring(0, urlForm.length() - 1);
                }
                b.url(urlForm);
                request = b.build();
                break;
            }
            case PUT: {
                b.url(url);
                b.put(requestBody);
                request = b.build();
                break;
            }
            case DELETE: {
                b.url(url);
                b.delete(requestBody);
                request = b.build();
                break;
            }
            case PATCH: {
                b.url(url);
                b.patch(requestBody);
                request = b.build();
                break;
            }
        }
        request.cacheControl().noCache();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        switch (getResponseType()) {
                            case STRING: {
                                IData iData = JSON.parseObject(body, IData.class);
                                DLog.e("respString",JSON.toJSONString(iData));
                                if (iData.getStatus().equals(IData.ok)) {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("string", iData.getData());
                                    //Log.e(urlPieces[urlPieces.length-1] + "[response]", jsonObject.toJSONString());
                                    iResponse.successObj(jsonObject);
                                } else {
                                    iResponse.fail(iData.getStatus(), iData.getMessage());
                                    //Log.e(url + "[failed]", "" + iData.getStatus());
                                }
                                break;
                            }
                            case ARRAY: {
                                IData iData = JSON.parseObject(body, IData.class);
                                DLog.e("respArray",JSON.toJSONString(iData));
                                if (iData.getStatus().equals(IData.ok)) {
                                    if (iData.getData() == null || iData.getData().isEmpty()) {
                                        iResponse.successArray(new JSONArray());
                                        //Log.e(urlPieces[urlPieces.length-1] + "[response]", "null or empty string");
                                    } else {
                                        JSONArray arrayList;
                                        try {
                                            arrayList = JSON.parseArray(iData.getData());
                                        } catch (JSONException e) {
                                            iResponse.fail("555", "数据解析错误");
                                            //Log.e(url + "[json error]", iData.getData());
                                            return;
                                        }
                                        iResponse.successArray(arrayList);
                                        //Log.e(urlPieces[urlPieces.length-1] + "[response]", arrayList.toJSONString());
                                    }
                                } else {
                                    iResponse.fail(iData.getStatus(), iData.getMessage());
                                    //Log.e(url + "[failed]", "" + iData.getStatus());
                                }
                                break;
                            }
                            case OBJECT: {
                                IData iData = JSON.parseObject(body, IData.class);
                                DLog.e("respObject",JSON.toJSONString(iData));
                                if (iData.getStatus().equals(IData.ok)) {
                                    if (iData.getData() == null || iData.getData().isEmpty()) {
                                        iResponse.successObj(new JSONObject());
                                        //Log.e(urlPieces[urlPieces.length-1] + "[response]", "null or empty string");
                                    } else {
                                        JSONObject jsonObject;
                                        try {
                                            jsonObject = JSON.parseObject(iData.getData());
                                        } catch (JSONException e) {
                                            iResponse.fail("555", "数据解析错误");
                                            //Log.e(url + "[json error]", iData.getData());
                                            return;
                                        }
                                        iResponse.successObj(jsonObject);
                                        //Log.e(urlPieces[urlPieces.length-1] + "[response]", jsonObject.toJSONString());
                                    }
                                } else {
                                    iResponse.fail(iData.getStatus(), iData.getMessage());
                                    //Log.e(url + "[failed]", "" + iData.getStatus());
                                }
                                break;
                            }
                            default: {
                                iResponse.fail(response.code() + "", "错误的数据返回");
                                //Log.e(url + "[unknown]","unknown response type" );
                                break;
                            }
                        }
                    } else {
                        String body = response.body().string();
                        IData iData = JSON.parseObject(body, IData.class);
                        DLog.e("respFailed",JSON.toJSONString(iData));
                        iResponse.fail(iData.getStatus() + "",iData.message);
                        //Log.e(url + "[refused]", "" + response.code());
                    }
                } catch (Exception ex) {
                    iResponse.fail(UNKNOW_ERROR, "请求异常");
                    //Log.e("taskEx:", ex.getMessage());
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                try {
                    if (e instanceof ConnectException) {
                        iResponse.fail(CONNECT_FAIL, "无法连接到服务器");
                    } else if (e instanceof SocketTimeoutException) {
                        iResponse.fail(TIME_OUT, "无法连接到服务器");
                    } else {
                        iResponse.fail(UNKNOW_ERROR, "请求异常\n请检查网络");
                    }
                } catch (Exception ex) {
                    iResponse.fail(UNKNOW_ERROR, ex.getMessage());
                }
            }
        });

    }
}
