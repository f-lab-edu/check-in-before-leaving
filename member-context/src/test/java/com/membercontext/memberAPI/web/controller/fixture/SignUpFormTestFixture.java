package com.membercontext.memberAPI.web.controller.fixture;

import com.membercontext.memberAPI.web.controller.form.SignUpForm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SignUpFormTestFixture {


    public SignUpForm createAllFilledSignUpForm() throws NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> mockSignUpFormClass = Class.forName("com.membercontext.memberAPI.web.controller.form.SignUpForm");
        SignUpForm signUpForm = (SignUpForm)mockSignUpFormClass.getDeclaredConstructor().newInstance();

        Field field = mockSignUpFormClass.getDeclaredField("name");
        field.setAccessible(true);
        field.set(signUpForm, "tester");

        field = mockSignUpFormClass.getDeclaredField("email");
        field.setAccessible(true);
        field.set(signUpForm, "tester@test.com");

        field = mockSignUpFormClass.getDeclaredField("password");
        field.setAccessible(true);
        field.set(signUpForm, "testPassword");

        field = mockSignUpFormClass.getDeclaredField("phone");
        field.setAccessible(true);
        field.set(signUpForm, "010-1234-5678");

        field = mockSignUpFormClass.getDeclaredField("location");
        field.setAccessible(true);
        field.set(signUpForm, "testLocation");

        field = mockSignUpFormClass.getDeclaredField("isLocationServiceEnabled");
        field.setAccessible(true);
        field.set(signUpForm, true);

        field = mockSignUpFormClass.getDeclaredField("point");
        field.setAccessible(true);
        field.set(signUpForm, 0L);

        return signUpForm;
    }
    public SignUpForm createAllFilledSignUpForm_Mock(){
        SignUpForm signUpForm = mock(SignUpForm.class);
        when(signUpForm.getName()).thenReturn("tester");
        when(signUpForm.getEmail()).thenReturn("test@test.com");
        when(signUpForm.getPassword()).thenReturn("testPassword");
        when(signUpForm.getPhone()).thenReturn("010-1234-5678");
        when(signUpForm.getLocation()).thenReturn("testLocation");
        when(signUpForm.getIsLocationServiceEnabled()).thenReturn(true);
        when(signUpForm.getPoint()).thenReturn(0L);
        return signUpForm;
    }
}
