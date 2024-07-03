package com.membercontext.memberAPI.web.controller.stub;

import com.membercontext.memberAPI.web.form.SignUpForm;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class SignUpFormTest extends SignUpForm {

    @Builder
    public SignUpFormTest(String email, String password, String name, String phone, String location, Boolean isLocationServiceEnabled, Integer point) {
       //super(email, password, name, phone, location, isLocationServiceEnabled, point);
    }
}
