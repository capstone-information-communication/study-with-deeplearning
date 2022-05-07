package com.smp.frontend.restAPi;

import com.google.gson.Gson;

public class SingletonGson {

    private static SingletonGson instance = new SingletonGson();
    private final Gson gson = new Gson();

    private SingletonGson() {
    }

    public static SingletonGson getInstance() {
        return instance;
    }

    public String toJson(Object data) {
        return gson.toJson(data);
    }


    public Object parsing(String toJson, Class klass) {
        return gson.fromJson(toJson, klass);
    }
}
