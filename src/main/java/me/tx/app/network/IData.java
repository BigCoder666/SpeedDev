package me.tx.app.network;

public class IData {
    public final static int ok=200;
    public final static int badtoken=512;

    public String getMessage() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public String msg="";
    public int status=0;
    public String data="";

}
