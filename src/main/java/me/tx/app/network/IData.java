package me.tx.app.network;

public class IData {
    public final static int ok=0;
    public final static int badtoken=512;

    public String getMessage() {
        return rettext;
    }

    public int getStatus() {
        return retcode;
    }

    public String getData() {
        return data;
    }

    public String getSignkey() {
        return signkey;
    }

    public String rettext="";
    public int retcode=0;
    public String data="";
    public String signkey="";

}
