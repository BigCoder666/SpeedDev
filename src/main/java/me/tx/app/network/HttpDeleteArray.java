package me.tx.app.network;

public class HttpDeleteArray extends HttpBuilder {
    @Override
    public REQUEST_TYPE getRequestType() {
        return REQUEST_TYPE.DELETE;
    }

    @Override
    public RESPONSE_TYPE getResponseType() {
        return RESPONSE_TYPE.ARRAY;
    }
}
