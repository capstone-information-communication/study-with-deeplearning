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

    private MemberSginInResponseDto wrongFigure;

    public MemberSginInResponseDto getWrongFigure() {
        return wrongFigure;
    }
    private float blankCount;
    private float  multipleCount;
    private float shortCount;
    private float orderCount;

    public float getBlankCount() {
        return blankCount;
    }

    public float getMultipleCount() {
        return multipleCount;
    }

    public float getShortCount() {
        return shortCount;
    }

    public float getOrderCount() {
        return orderCount;
    }
}
