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
    private int blankCount;
    private int  multipleCount;
    private int shortCount;
    private int orderCount;

    public int getBlankCount() {
        return blankCount;
    }

    public int getMultipleCount() {
        return multipleCount;
    }

    public int getShortCount() {
        return shortCount;
    }

    public int getOrderCount() {
        return orderCount;
    }
}
