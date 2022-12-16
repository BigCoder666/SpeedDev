package me.tx.app.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

    public LoadingController loadingController = null;

    public HttpBuilder(LoadingController loadingController) {
        super();
        this.loadingController = loadingController;
        loadingController.show();
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
        setUrl(url);
        buildJsonRequest(object, header);
        IRequestFunction iRequestFunction = new IRequestFunction() {
            @Override
            public HttpBuilder post() {
                HttpBuilder.this.post();
                return HttpBuilder.this;
            }

            @Override
            public HttpBuilder get(Mapper mapper) {
                HttpBuilder.this.get(mapper);
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
        setUrl(url);
        buildFormRequest(object, header);
        IRequestFunction iRequestFunction = new IRequestFunction() {
            @Override
            public HttpBuilder post() {
                HttpBuilder.this.post();
                return HttpBuilder.this;
            }

            @Override
            public HttpBuilder get(Mapper mapper) {
                HttpBuilder.this.get(mapper);
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

        HttpBuilder get(Mapper mapper);

        HttpBuilder put();

        HttpBuilder delete();

        HttpBuilder patch();
    }

    public void post() {
        b.url(url);
        b.post(requestBody);
        request = b.build();
    }

    public void get(Mapper ob) {
        if(ob.size()!=0){
            url = url + "?";
            for (String key : ob.keySet()) {
                if (ob.get(key) instanceof String) {
                    try {
                        url = url + key + "=" + URLEncoder.encode((String) ob.get(key), "utf-8") + "&";
                    } catch (UnsupportedEncodingException e) {
                        url = url + key + "=" + (String) ob.get(key) + "&";
                        e.printStackTrace();
                    }
                }else if (ob.get(key) instanceof Integer) {
                    url = url + key + "=" + (int) ob.get(key) + "&";
                }else if (ob.get(key) instanceof Float) {
                    url = url + key + "=" + (float) ob.get(key) + "&";
                }else if (ob.get(key) instanceof Double) {
                    url = url + key + "=" + (double) ob.get(key) + "&";
                }else {
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
        b.url(url);
        DLog.e("GET↑", url);
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

    public void call(IResponse<T> iResponse){
        request.cacheControl().noCache();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (loadingController != null) {
                    loadingController.dismiss();
                }
                try {
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        iResponse.success(body);
                    } else {
                        iResponse.fail(response.code() + "", response.message());
                    }
                } catch (Exception ex) {
                    iResponse.fail(UNKNOW_ERROR, "请求异常");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (loadingController != null) {
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

    public void callList(IArrayList<T> iResponse) {
        request.cacheControl().noCache();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (loadingController != null) {
                    loadingController.dismiss();
                }
                try {
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        IData iData = JSON.parseObject(body,IData.class);
                        if (iData.getStatus().equals(IData.ok)) {
                            List<T> data = JSON.parseObject(body, getSuperclassTypeParameter(iResponse.getClass()));
                            iResponse.successArray(data);
                        } else {
                            iResponse.fail(iData.getStatus(), iData.getMessage());
                        }
                    } else {
                        iResponse.fail(response.code() + "", response.message());
                    }
                } catch (Exception ex) {
                    iResponse.fail(UNKNOW_ERROR, "请求异常");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (loadingController != null) {
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
                if (loadingController != null) {
                    loadingController.dismiss();
                }
                try {
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        IData iData = JSON.parseObject(body,IData.class);
                        if (iData.getStatus().equals(IData.ok)) {
                            T data = JSON.parseObject(iData.getData(),getSuperclassTypeParameter(iResponse.getClass()));
                            iResponse.successObj(data);
                        } else {
                            iResponse.fail(iData.getStatus(), iData.getMessage());
                        }
                    } else {
                        iResponse.fail(response.code() + "", response.message());
                    }
                } catch (Exception ex) {
                    iResponse.fail(UNKNOW_ERROR, "请求异常");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (loadingController != null) {
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

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            return null;
        }
        Type[] params = ((ParameterizedType) superclass).getActualTypeArguments();
        return params[0];
    }

    private HttpBuilder buildJsonRequest(Object object, HashMap<String, String> header) {
        DLog.e("req", url + "\n" + JSON.toJSONString(object));
        b = new Request.Builder();
        for (String key : header.keySet()) {
            b.header(key, header.get(key));
        }
        requestBody = new BodyMaker().makeJsonBody(object);
        return this;
    }

    private HttpBuilder buildFormRequest(Mapper mapper, HashMap<String, String> header) {
        DLog.e("req", url + "\n" + JSON.toJSONString(mapper));
        b = new Request.Builder();
        for (String key : header.keySet()) {
            b.header(key, header.get(key));
        }
        requestBody = new BodyMaker().makeFormBody(mapper);
        return this;
    }
}
