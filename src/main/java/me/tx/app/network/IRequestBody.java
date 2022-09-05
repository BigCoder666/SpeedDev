package me.tx.app.network;

import okhttp3.RequestBody;

public interface IRequestBody {
    RequestBody makeJsonBody(Object object);
    RequestBody makeFormBody(Mapper list);
}
