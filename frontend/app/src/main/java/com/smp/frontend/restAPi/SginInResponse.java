package com.smp.frontend.restAPi;

import java.util.List;

import retrofit2.Call;

public class SginInResponse {

    private  Object token;
    private  String message;

    public String getMessage() {
        return message;
    }


    public Object Gettoken() {
        return token;
    }

}
