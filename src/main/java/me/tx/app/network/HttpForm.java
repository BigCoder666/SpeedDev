package me.tx.app.network;

public abstract class HttpForm extends HttpBuilder {
    @Override
    public REQUEST_TYPE getRequestType() {
        return REQUEST_TYPE.FORM;
    }
}
