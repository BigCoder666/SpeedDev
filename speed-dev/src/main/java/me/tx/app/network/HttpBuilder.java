package me.tx.app.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.tx.app.Config;
import me.tx.app.utils.DLog;
import me.tx.app.utils.LoadingController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static me.tx.app.Config.CONNECT_FAIL;
import static me.tx.app.Config.TIME_OUT;
import static me.tx.app.Config.UNKNOW_ERROR;

public class HttpBuilder<T> {
    private Request request = null;
    private RequestBody requestBody = null;
    private Request.Builder b = null;
    private String url = "";

    public LoadingController loadingController =null;

    public HttpBuilder(LoadingController loadingController){
        super();
        this.loadingController = loadingController;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public IRequestFunction initJson(String url, Object object, HashMap<String, String> header) {
        buildJsonRequest(object, header);
        setUrl(url);
        IRequestFunction iRequestFunction = new IRequestFunction() {
            @Override
            public HttpBuilder post() {
                HttpBuilder.this.post();
                return HttpBuilder.this;
            }

            @Override
            public HttpBuilder get() {
                HttpBuilder.this.get();
                return HttpBuilder.this;
            }

            @Override
            public HttpBuilder put() {
                HttpBuilder.this.put();
                return HttpBuilder.this;
            }

            @Override
            public HttpBuilder delete() {
                HttpBuilder.this.delete();
                return HttpBuilder.this;
            }

            @Override
            public HttpBuilder patch() {
                HttpBuilder.this.patch();
                return HttpBuilder.this;
            }
        };
        return iRequestFunction;
    }

    public IRequestFunction initForm(String url,
                                     Mapper object,
                                     HashMap<String, String> header) {
        buildFormRequest(object, header);
        setUrl(url);
        IRequestFunction iRequestFunction = new IRequestFunction() {
            @Override
            public HttpBuilder post() {
                HttpBuilder.this.post();
                return HttpBuilder.this;
            }

            @Override
            public HttpBuilder get() {
                HttpBuilder.this.get();
                return HttpBuilder.this;
            }

            @Override
            public HttpBuilder put() {
                HttpBuilder.this.put();
                return HttpBuilder.this;
            }

            @Override
            public HttpBuilder delete() {
                HttpBuilder.this.delete();
                return HttpBuilder.this;
            }

            @Override
            public HttpBuilder patch() {
                HttpBuilder.this.patch();
                return HttpBuilder.this;
            }
        };
        return iRequestFunction;
    }

    public interface IRequestFunction {
        HttpBuilder post();

        HttpBuilder get();

        HttpBuilder put();

        HttpBuilder delete();

        HttpBuilder patch();
    }

    public void post() {
        b.url(url);
        b.post(requestBody);
        request = b.build();
    }

    public void get() {
        b.url(url);
        request = b.build();
    }

    public void put() {
        b.url(url);
        b.put(requestBody);
        request = b.build();
    }

    public void delete() {
        b.url(url);
        b.delete(requestBody);
        request = b.build();
    }

    public void patch() {
        b.url(url);
        b.patch(requestBody);
        request = b.build();
    }

    public void callList(IArrayList<T> iResponse) {
        request.cacheControl().noCache();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(loadingController!=null){
                    loadingController.dismiss();
                }
                try {
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        IListData<T> iData = JSON.parseObject(body,new TypeReference<IListData<T>>(){}.getType());
                        if (iData.getStatus().equals(IData.ok)) {
                            iResponse.successArray(iData);
                        } else {
                            iResponse.fail(iData.getStatus(), iData.getMessage());
                        }
                    }else {
                        iResponse.fail(response.code()+"", response.message());
                    }
                } catch (Exception ex) {
                    iResponse.fail(UNKNOW_ERROR, "请求异常");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if(loadingController!=null){
                    loadingController.dismiss();
                }
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

    public void callObject(IObject<T> iResponse) {
        if (!url.startsWith(Config.URL_START_WITH)) {
            iResponse.fail(Config.NETWORK_NOT_START_WITH_HTTP, Config.NETWORK_NOT_START_WITH_HTTP_MESSAGE);
            return;
        }
        request.cacheControl().noCache();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(loadingController!=null){
                    loadingController.dismiss();
                }
                try {
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        IData<T> iData = JSON.parseObject(body, new TypeReference<IData<T>>(){}.getType());
                        if (iData.getStatus().equals(IData.ok)) {
                            iResponse.successObj(iData);
                        } else {
                            iResponse.fail(iData.getStatus(), iData.getMessage());
                        }
                    }else {
                        iResponse.fail(response.code()+"", response.message());
                    }
                } catch (Exception ex) {
                    iResponse.fail(UNKNOW_ERROR, "请求异常");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if(loadingController!=null){
                    loadingController.dismiss();
                }
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

    private HttpBuilder buildJsonRequest(Object object, HashMap<String, String> header) {
        DLog.e("req", url + "\n" + JSON.toJSONString(object));
        b = new Request.Builder();
        for (String key : header.keySet()) {
            b.header(key, header.get(key));
        }
        requestBody = new BodyMaker().makeJsonBody(object);
        if (object instanceof Mapper) {
            url = url + "?";
            Mapper ob = (Mapper) object;
            for (String key : ob.keySet()) {
                if (ob.get(key) instanceof String) {
                    try {
                        url = url + key + "=" + URLEncoder.encode((String) ob.get(key), "utf-8") + "&";
                    } catch (UnsupportedEncodingException e) {
                        url = url + key + "=" + (String) ob.get(key) + "&";
                        e.printStackTrace();
                    }
                } else {
                    try {
                        url = url + key + "=" + URLEncoder.encode(JSON.toJSONString(ob.get(key)), "utf-8") + "&";
                    } catch (UnsupportedEncodingException e) {
                        url = url + key + "=" + JSON.toJSONString(ob.get(key)) + "&";
                        e.printStackTrace();
                    }
                }
            }
            if (url.endsWith("&")) {
                url = url.substring(0, url.length() - 1);
            }
        }
        return this;
    }

    private HttpBuilder buildFormRequest(Mapper mapper, HashMap<String, String> header) {
        DLog.e("req", url + "\n" + JSON.toJSONString(mapper));
        b = new Request.Builder();
        for (String key : header.keySet()) {
            b.header(key, header.get(key));
        }
        requestBody = new BodyMaker().makeFormBody(mapper);

        for (String key : mapper.keySet()) {
            if (mapper.get(key) instanceof String) {
                try {
                    url = url + key + "=" + URLEncoder.encode((String) mapper.get(key), "utf-8") + "&";
                } catch (UnsupportedEncodingException e) {
                    url = url + key + "=" + (String) mapper.get(key) + "&";
                    e.printStackTrace();
                }
            } else {
                try {
                    url = url + key + "=" + URLEncoder.encode(JSON.toJSONString(mapper.get(key)), "utf-8") + "&";
                } catch (UnsupportedEncodingException e) {
                    url = url + key + "=" + JSON.toJSONString(mapper.get(key)) + "&";
                    e.printStackTrace();
                }
            }
        }
        if (url.endsWith("&")) {
            url = url.substring(0, url.length() - 1);
        }

        return this;
    }
}
