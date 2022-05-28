package com.smp.frontend.global;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

    public JSONArray toJsonArr(Object data) throws JSONException {
        JSONArray a = new JSONArray(gson.toJson(data));
        return  a;
    }
    public String ArrToString(JSONArray jsonArray,int i) throws JSONException {
        String b = jsonArray.getJSONObject(i).toString();
        return b;
    }
    public  String GetStringJSON(JSONArray jsonArray, int i,String j) throws  JSONException{
        String c = jsonArray.getJSONObject(i).getString(j);
        return c;
    }

    public Object parsing(String toJson, Class klass) {
        return gson.fromJson(toJson, klass);
    }

    public String jsonArray(List<?> data, int i, String firstObj, String seccondObj) throws JSONException {
        JSONArray a = new JSONArray(gson.toJson(data));
        String workbook = a.getJSONObject(i).getString(firstObj);
        JSONObject JSONObject = new JSONObject(workbook);
        String b = JSONObject.getString(seccondObj);
        return b;
    }


}
