package me.tx.app.network;

import java.util.HashMap;

public class HttpPostObject extends HttpBuilder {

    @Override
    public REQUEST_TYPE getRequestType() {
        return REQUEST_TYPE.POST;
    }

    @Override
    public RESPONSE_TYPE getResponseType() {
        return RESPONSE_TYPE.OBJECT;
    }
}
