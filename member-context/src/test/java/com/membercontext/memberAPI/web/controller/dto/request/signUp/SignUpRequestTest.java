package com.membercontext.memberAPI.web.controller.dto.request.signUp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.fixture.web.crud.SignUpRequestFixture;
import com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.SignUpServiceImpl;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.web.controller.SignUpController;

import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
class SignUpRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private SignUpServiceImpl signUpService;

    private final String requestURL = "/sign-in";

    private SignUpController.SignUpRequest req;

    @BeforeEach
    void setUp() {
        req = spy(SignUpRequestFixture.create());
    }

    @Test
    @DisplayName("이메일 없음.")
    void signUp_NoEmail() throws Exception {
        //given
        when(req.getEmail()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }

    @Test
    @DisplayName("이메일 포멧 다름.")
    void signUp_EmailFormatError() throws Exception {
        //given
        when(req.getEmail()).thenReturn("test");

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }

    @Test
    @DisplayName("이름 없음.")
    public void signUp_NoName() throws Exception {
        //given
        when(req.getName()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }

    @Test
    @DisplayName("비밀번호 없음.")
    public void signUp_NoPassword() throws Exception {
        //given
        when(req.getPassword()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }

    @Test
    @DisplayName("전화번호 없음.")
    public void signUp_NoPhone() throws Exception {
        //given
        when(req.getPhone()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }

    @Test
    @DisplayName("위치 없음.")
    public void signUp_NoLocation() throws Exception {
        //given
        when(req.getAddress()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }

    @Test
    @DisplayName("위치 서비스 사용 여부 없음.")
    public void signUp_NoIsLocationServiceEnabled() throws Exception {
        //given
        when(req.getIsLocationServiceEnabled()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }
}