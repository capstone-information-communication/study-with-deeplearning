package com.smp.frontend.member.dto;

public class MemberSignUpRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String role;

    public MemberSignUpRequestDto(String email, String password, String nickname, String name,String role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.role = role;
    }
}
