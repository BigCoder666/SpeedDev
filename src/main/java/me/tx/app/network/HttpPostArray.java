package me.tx.app.network;

public class HttpPostArray extends HttpBuilder {

    @Override
    public REQUEST_TYPE getRequestType() {
        return REQUEST_TYPE.POST;
    }

    @Override
    public RESPONSE_TYPE getResponseType() {
        return RESPONSE_TYPE.ARRAY;
    }
}
