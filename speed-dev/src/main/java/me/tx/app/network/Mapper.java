package me.tx.app.network;

import java.util.HashMap;

public class Mapper extends HashMap<String, Object> {
    public Mapper add(String key, Object o) {
        put(key, o);
        return this;
    }
}
