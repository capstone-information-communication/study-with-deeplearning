package com.smp.frontend.restAPi;

import java.util.List;

import retrofit2.Call;

public class SginInResponse {

    private  Object token;
    private  String message;
    private  String email;
    private  String nickname;

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMessage() {
        return message;
    }


    public Object Gettoken() {
        return token;
    }

}
