package com.smp.frontend.restAPi;

import com.google.gson.Gson;

public class singletonGson {

    private static singletonGson instance = new singletonGson();
    private final Gson gson = new Gson();

    private singletonGson() {
    }

    public static singletonGson getInstance() {
        return instance;
    }

    public String toJson(Object data) {
        return gson.toJson(data);
    }

    public Object parsing(String toJson, Class klass) {
        return gson.fromJson(toJson, klass);
    }
}
