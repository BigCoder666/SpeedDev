package me.tx.app.network;

public class HttpFormString extends HttpForm {
    public void build(String url, ParamList paramList, IStringForm iString){
        super.build(url,paramList,iString);
    }

    @Override
    public RESPONSE_TYPE getResponseType() {
        return RESPONSE_TYPE.STRING;
    }
}
