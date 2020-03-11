package me.tx.app.network;

public class HttpGetArray extends HttpBuilder {
    @Override
    public REQUEST_TYPE getRequestType() {
        return REQUEST_TYPE.GET;
    }

    @Override
    public RESPONSE_TYPE getResponseType() {
        return RESPONSE_TYPE.ARRAY;
    }
}
