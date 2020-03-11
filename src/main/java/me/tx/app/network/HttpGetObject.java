package me.tx.app.network;

public class HttpGetObject extends HttpBuilder {
    @Override
    public REQUEST_TYPE getRequestType() {
        return REQUEST_TYPE.GET;
    }

    @Override
    public RESPONSE_TYPE getResponseType() {
        return RESPONSE_TYPE.OBJECT;
    }
}
