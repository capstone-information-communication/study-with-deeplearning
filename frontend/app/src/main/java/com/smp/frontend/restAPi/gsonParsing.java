package com.smp.frontend.restAPi;

import com.google.gson.Gson;

public class gsonParsing {

    private static gsonParsing instance = new gsonParsing();
    private final Gson gson = new Gson();

    private gsonParsing() {
    }

    public static gsonParsing getInstance() {
        return instance;
    }

    public String toJson(Object data) {
        return gson.toJson(data);
    }

    public Object parsing(String toJson, Class klass) {
        return gson.fromJson(toJson, klass);
    }
}
