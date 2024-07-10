package com.membercontext.memberAPI.web.controller.fixture.stub;

import com.membercontext.memberAPI.web.controller.form.SignUpForm;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SignUpFormMock {

    private SignUpForm signUpForm;

    private SignUpFormMock() {
        this.signUpForm = mock(SignUpForm.class);
    }

    public static SignUpFormMock mockBuilder(){
        return new SignUpFormMock();
    }
    public SignUpForm build(){
        return signUpForm;
    }

    //Fields
    public SignUpFormMock email(String email) {
        when(signUpForm.getEmail()).thenReturn(email);
        return this;
    }
    public SignUpFormMock password(String password) {
        when(signUpForm.getPassword()).thenReturn(password);
        return this;
    }
    public SignUpFormMock name(String name) {
        when(signUpForm.getName()).thenReturn(name);
        return this;
    }
    public SignUpFormMock phone(String phone) {
        when(signUpForm.getPhone()).thenReturn(phone);
        return this;
    }
    public SignUpFormMock location(String location) {
        when(signUpForm.getLocation()).thenReturn(location);
        return this;
    }
    public SignUpFormMock isLocationServiceEnabled(Boolean isLocationServiceEnabled) {
        when(signUpForm.getIsLocationServiceEnabled()).thenReturn(isLocationServiceEnabled);
        return this;
    }
    public SignUpFormMock point(Long point) {
        when(signUpForm.getPoint()).thenReturn(point);
        return this;
    }





}
