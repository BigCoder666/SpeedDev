package me.tx.app.network;

import com.alibaba.fastjson.JSON;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BodyMaker implements IRequestBody {
    @Override
    public RequestBody makeJsonBody(Object object) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(object));
        return requestBody;
    }

    @Override
    public RequestBody makeFormBody(Mapper mapper) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : mapper.keySet()) {
            builder.add(key, (String)mapper.get(key));
        }
        RequestBody requestBody = builder.build();
        return requestBody;
    }
}
