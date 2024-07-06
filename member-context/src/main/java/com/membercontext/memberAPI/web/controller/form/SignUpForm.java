package com.membercontext.memberAPI.web.controller.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignUpForm {

    @Email(message = "이메일 형식에 맞게 입력해주세요")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotBlank(message = "휴대폰 번호를 입력해주세요")
    private String phone;

    @NotBlank(message = "동을 입력해주세요")
    private String location;

    @NotNull(message = "위치 서비스 사용 여부를 입력해주세요")
    private Boolean isLocationServiceEnabled;

    Long point;



}
