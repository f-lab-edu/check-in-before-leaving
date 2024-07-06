package com.membercontext.memberAPI.web.controller.fixture;

import com.membercontext.memberAPI.web.controller.form.SignUpForm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SignUpFormTestFixture {

    //FixMe: 질문1 배경: SignUpFormMock을 사용했던 이유가
    //                 SignUpForm의 생성자를 캡슐화를 위해 private으로 두어서 무분별한 생성자를 막아주고 싶었습니다.
    //                 그래서 Mockito 라이브러리로 모킹하여 써야 겠다고 생각했는데 when으로 스터빙하는게 너무 많아져서 가독성이 떨어진다고 생각이 들었습니다.

    //FixMe 테스트(픽스쳐): 질문1 : SignUpFormMock으로 모킹을 빌더 형식으로 쓰던걸 SignUpFormTestFixture로 변경하면서
    //              모킹을 팩토리 메서드 형식으로 변경하여서 쓰는 걸로만 변경 된것 같아서 Test Fixture를 잘못 사용하고 있는건지 궁금합니다.
    //              아직 라이브러리가 덜 익숙해서 그런것 같기도 한데 gradle test fixture를 사용하는건 모듈간 재사용성을 위한 것 일까요??
    //              gradle test fixture를 private필드 값을 채워주는 객체를 만들어 주는 라이브러리라고 생각을 했던것 같습니다.

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
