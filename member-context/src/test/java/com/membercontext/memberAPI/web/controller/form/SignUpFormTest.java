package com.membercontext.memberAPI.web.controller.form;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.application.service.SignUpServiceImpl;
import com.membercontext.memberAPI.domain.entity.Member;
import com.membercontext.memberAPI.web.controller.SignUpController;
import com.membercontext.memberAPI.web.controller.fixture.SignUpFormTestFixture;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
class SignUpFormTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private SignUpServiceImpl signUpService;

    private final String requestURL = "/sign-in";
    private final SignUpFormTestFixture signUpFormTestFixture = new SignUpFormTestFixture();

    @Test
    @DisplayName("이메일 없음.")
    void signUp_NoEmail() throws Exception {
        //given
        SignUpForm form = signUpFormTestFixture.createAllFilledSignUpForm_Mock();
        when(form.getEmail()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }
    @Test
    @DisplayName("이메일 포멧 다름.")
    void signUp_EmailFormatError() throws Exception {
        //given
        SignUpForm form = signUpFormTestFixture.createAllFilledSignUpForm_Mock();
        when(form.getEmail()).thenReturn("test");

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }

    @Test
    @DisplayName("이름 없음.")
    @Transactional
    public void signUp_NoName() throws Exception {
        //given
        SignUpForm form = signUpFormTestFixture.createAllFilledSignUpForm_Mock();
        when(form.getName()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }

    @Test
    @DisplayName("비밀번호 없음.")
    public void signUp_NoPassword() throws Exception {
        //given
        SignUpForm form = signUpFormTestFixture.createAllFilledSignUpForm_Mock();
        when(form.getPassword()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }

    @Test
    @DisplayName("전화번호 없음.")
    public void signUp_NoPhone() throws Exception {
        //given
        SignUpForm form = signUpFormTestFixture.createAllFilledSignUpForm_Mock();
        when(form.getPhone()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }

    @Test
    @DisplayName("위치 없음.")
    public void signUp_NoLocation() throws Exception {
        //given
        SignUpForm form = signUpFormTestFixture.createAllFilledSignUpForm_Mock();
        when(form.getLocation()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }

    @Test
    @DisplayName("위치 서비스 사용 여부 없음.")
    public void signUp_NoIsLocationServiceEnabled() throws Exception {
        //given
        SignUpForm form = signUpFormTestFixture.createAllFilledSignUpForm_Mock();
        when(form.getIsLocationServiceEnabled()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }








}