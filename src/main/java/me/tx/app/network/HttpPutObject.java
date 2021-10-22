package me.tx.app.network;

public class HttpPutObject extends HttpBuilder {
    @Override
    public REQUEST_TYPE getRequestType() {
        return REQUEST_TYPE.PUT;
    }

    @Override
    public RESPONSE_TYPE getResponseType() {
        return RESPONSE_TYPE.OBJECT;
    }
}
