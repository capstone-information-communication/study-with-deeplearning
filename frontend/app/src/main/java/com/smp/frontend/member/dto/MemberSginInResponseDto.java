package com.smp.frontend.member.dto;

import java.util.List;

import retrofit2.Call;

public class MemberSginInResponseDto {

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
