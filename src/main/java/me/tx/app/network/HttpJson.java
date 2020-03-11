package me.tx.app.network;

public abstract class HttpJson<T> extends HttpBuilder {
    @Override
    public REQUEST_TYPE getRequestType() {
        return REQUEST_TYPE.JSON;
    }
}
