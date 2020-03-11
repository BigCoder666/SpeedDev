package me.tx.app.network;

public class HttpJsonObject extends HttpJson {
    public void build(String url,ParamList paramList,IObject iObject){
        super.build(url,paramList,iObject);
    }

    @Override
    public RESPONSE_TYPE getResponseType() {
        return RESPONSE_TYPE.OBJECT;
    }
}
