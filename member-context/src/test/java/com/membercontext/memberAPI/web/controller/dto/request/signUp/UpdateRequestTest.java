package com.membercontext.memberAPI.web.controller.dto.request.signUp;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
class UpdateRequestTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private SignUpServiceImpl signUpService;

    private final String requestURL = "/sign-in";
    private SignUpController.UpdateRequest form;

    @BeforeEach
    void setUp() {
        form = mock(SignUpController.UpdateRequest.class);
    }

    @Test
    @DisplayName("회원 아이디(PK) 미입력.")
    void update_URL() throws Exception {
        //given
        when(form.getId()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("이름 미입력.")
    void update_NoName() throws Exception {
        //given
        when(form.getName()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("이메일 미입력.")
    void update_NoEmail() throws Exception {
        //given
        when(form.getEmail()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("이메일 형식 오류.")
    void update_InvalidEmail() throws Exception {
        //given
        when(form.getEmail()).thenReturn("invalidEmail");

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("비밀번호 미입력.")
    void update_NoPassword() throws Exception {
        //given
        when(form.getPassword()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("전화번호 미입력.")
    void update_NoPhone() throws Exception {
        //given
        when(form.getPhone()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("위치 미입력.")
    void update_NoLocation() throws Exception {
        //given
        when(form.getLocation()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("위치 서비스 사용 여부 없음.")
    public void signUp_NoIsLocationServiceEnabled() throws Exception {
        //given
        when(form.getIsLocationServiceEnabled()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(signUpService, times(0)).signUp(any(Member.class));
    }

    @Test
    @DisplayName("포인트 미입력.")
    void update_NoPoint() throws Exception {
        //given
        when(form.getPoint()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest());
    }


}