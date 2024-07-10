package com.membercontext.memberAPI.web.controller.fixture.stub;

import com.membercontext.memberAPI.web.controller.form.SignUpForm;
import lombok.Builder;
import lombok.Getter;


@Getter
public class SignUpFormTest extends SignUpForm {

    @Builder
    public SignUpFormTest(String email, String password, String name, String phone, String location, Boolean isLocationServiceEnabled, Integer point) {
       //super(email, password, name, phone, location, isLocationServiceEnabled, point);
    }
}
