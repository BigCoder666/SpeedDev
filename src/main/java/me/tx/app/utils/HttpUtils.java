package me.tx.app.utils;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

import me.tx.app.network.HttpBuilder;
import me.tx.app.network.HttpDeleteArray;
import me.tx.app.network.HttpDeleteObject;
import me.tx.app.network.HttpDeleteString;
import me.tx.app.network.HttpGetArray;
import me.tx.app.network.HttpGetObject;
import me.tx.app.network.HttpGetString;
import me.tx.app.network.HttpPatchArray;
import me.tx.app.network.HttpPatchObject;
import me.tx.app.network.HttpPatchString;
import me.tx.app.network.HttpPostArray;
import me.tx.app.network.HttpPostObject;
import me.tx.app.network.HttpPostString;
import me.tx.app.network.HttpPutArray;
import me.tx.app.network.HttpPutObject;
import me.tx.app.network.HttpPutString;
import me.tx.app.network.IResponse;
import me.tx.app.network.ParamList;
import me.tx.app.ui.activity.BaseActivity;

public class HttpUtils {
    public HttpPatchArray httpPatchArray;
    public HttpPatchObject httpPatchObject;
    public HttpPatchString httpPatchString;

    public HttpGetArray httpGetArray;
    public HttpGetObject httpGetObject;
    public HttpGetString httpGetString;

    public HttpPutArray httpPutArray;
    public HttpPutObject httpPutObject;
    public HttpPutString httpPutString;

    public HttpPostArray httpPostArray;
    public HttpPostObject httpPostObject;
    public HttpPostString httpPostString;

    public HttpDeleteArray httpDeleteArray;
    public HttpDeleteObject httpDeleteObject;
    public HttpDeleteString httpDeleteString;

    private static HttpUtils httpUtils = null;

    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    public static HttpUtils reInstance() {
        httpUtils =null;
        httpUtils = new HttpUtils();
        return httpUtils;
    }

    public HttpUtils(){
        httpPatchArray = new HttpPatchArray();
        httpPatchObject = new HttpPatchObject();
        httpPatchString = new HttpPatchString();

        httpGetArray = new HttpGetArray();
        httpGetObject = new HttpGetObject();
        httpGetString = new HttpGetString();

        httpPutArray = new HttpPutArray();
        httpPutObject = new HttpPutObject();
        httpPutString = new HttpPutString();

        httpPostArray = new HttpPostArray();
        httpPostObject = new HttpPostObject();
        httpPostString = new HttpPostString();

        httpDeleteArray = new HttpDeleteArray();
        httpDeleteObject = new HttpDeleteObject();
        httpDeleteString = new HttpDeleteString();
    }

    public void req(String action, ParamList paramList, IResponse iResponse, BaseActivity activity) {
        if (iResponse.getRequestType() == HttpBuilder.REQUEST_TYPE.PATCH) {
            if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.ARRAY) {
                httpPatchArray.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.OBJECT) {
                httpPatchObject.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.STRING) {
                httpPatchString.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            }
        } else if (iResponse.getRequestType() == HttpBuilder.REQUEST_TYPE.PUT) {
            if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.ARRAY) {
                httpPutArray.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.OBJECT) {
                httpPutObject.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.STRING) {
                httpPutString.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            }
        } else if (iResponse.getRequestType() == HttpBuilder.REQUEST_TYPE.GET) {
            if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.ARRAY) {
                httpGetArray.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.OBJECT) {
                httpGetObject.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.STRING) {
                httpGetString.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            }
        } else if (iResponse.getRequestType() == HttpBuilder.REQUEST_TYPE.POST) {
            if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.ARRAY) {
                httpPostArray.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.OBJECT) {
                httpPostObject.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.STRING) {
                httpPostString.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            }
        } else if (iResponse.getRequestType() == HttpBuilder.REQUEST_TYPE.DELETE) {
            if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.ARRAY) {
                httpDeleteArray.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.OBJECT) {
                httpDeleteObject.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.STRING) {
                httpDeleteString.change(iResponse.getJF()).build(action, paramList, iResponse, activity.getHeader());
            }
        }
    }

    public void req(String action, ParamList paramList, IResponse iResponse, HashMap<String,String> header) {
        if (iResponse.getRequestType() == HttpBuilder.REQUEST_TYPE.PATCH) {
            if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.ARRAY) {
                httpPatchArray.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.OBJECT) {
                httpPatchObject.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.STRING) {
                httpPatchString.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            }
        } else if (iResponse.getRequestType() == HttpBuilder.REQUEST_TYPE.PUT) {
            if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.ARRAY) {
                httpPutArray.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.OBJECT) {
                httpPutObject.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.STRING) {
                httpPutString.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            }
        } else if (iResponse.getRequestType() == HttpBuilder.REQUEST_TYPE.GET) {
            if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.ARRAY) {
                httpGetArray.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.OBJECT) {
                httpGetObject.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.STRING) {
                httpGetString.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            }
        } else if (iResponse.getRequestType() == HttpBuilder.REQUEST_TYPE.POST) {
            if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.ARRAY) {
                httpPostArray.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.OBJECT) {
                httpPostObject.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.STRING) {
                httpPostString.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            }
        } else if (iResponse.getRequestType() == HttpBuilder.REQUEST_TYPE.DELETE) {
            if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.ARRAY) {
                httpDeleteArray.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.OBJECT) {
                httpDeleteObject.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            } else if (iResponse.getResponseType() == HttpBuilder.RESPONSE_TYPE.STRING) {
                httpDeleteString.change(iResponse.getJF()).build(action, paramList, iResponse, header);
            }
        }
    }
}
