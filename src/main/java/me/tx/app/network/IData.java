package me.tx.app.network;

import java.util.Arrays;
import java.util.List;

public class IData {
    public final static String ok="200";
    public final static List<String> badtoken=Arrays.asList("401");

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return code;
    }

    public String getData() {
        return (result!=null&&!result.isEmpty())?result:((data!=null&&!data.isEmpty())?data:"");
    }

    public String message="";
    public String code="555";
    public String result="";
    public String data="";
}
