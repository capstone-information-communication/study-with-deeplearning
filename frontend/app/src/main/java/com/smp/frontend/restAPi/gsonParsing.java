package com.smp.frontend.restAPi;

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

/*    public JSONArray toJsonArr(Object data) throws JSONException {
        JSONArray a = new JSONArray(gson.toJson(data));
        return  a;
    }
    public String ArrToString(JSONArray jsonArray,int i) throws JSONException {
        String a = jsonArray.getJSONObject(i).toString();
        return a;
    }*/

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

    public String jsonArrayTwo(List<?> data, int i,String firstObj, String seccondObj,int j) throws JSONException {
        JSONArray a = new JSONArray(gson.toJson(data));
        String fobj = a.getJSONObject(i).getString(firstObj);
        JSONArray questionList = new JSONArray(fobj);
        String sobj = questionList.getJSONObject(j).getString(seccondObj);

        return sobj;
    }
    public int jsonSizeTwo(List<?> data, int i,String firstObj)throws JSONException {
        JSONArray a = new JSONArray(gson.toJson(data));
        String fobj = a.getJSONObject(i).getString(firstObj);
        JSONArray questionList = new JSONArray(fobj);
        return questionList.length();
    }
    
    public String jsonArrayThree(List<?> data, int i,int j,int k,String firstObj, String secondObj,String thirdObj) throws JSONException {
        JSONArray a = new JSONArray(gson.toJson(data));
        String Firstobj = a.getJSONObject(i).getString(firstObj);
        JSONArray questionList = new JSONArray(Firstobj);
        String Secobj = questionList.getJSONObject(j).getString(secondObj);
        JSONArray Throbj = new JSONArray(Secobj);
        String tobj = Throbj.getJSONObject(k).getString(thirdObj);

        return tobj;
    }
    public int jsonSizeThree(List<?> data, int i,String firstObj,String secondObj,int j)throws JSONException {
        JSONArray a = new JSONArray(gson.toJson(data));
        String fobj = a.getJSONObject(i).getString(firstObj);
        JSONArray questionList = new JSONArray(fobj);
        String sobj = questionList.getJSONObject(j).getString(secondObj);
        JSONArray three = new JSONArray(sobj);
        return three.length();
    }

}
