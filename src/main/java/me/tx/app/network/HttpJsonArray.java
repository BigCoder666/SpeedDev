package me.tx.app.network;

public class HttpJsonArray extends HttpJson {
    public void build(String url,ParamList paramList,IArray iArray){
        super.build(url,paramList,iArray);
    }

    @Override
    public RESPONSE_TYPE getResponseType() {
        return RESPONSE_TYPE.ARRAY;
    }
}
