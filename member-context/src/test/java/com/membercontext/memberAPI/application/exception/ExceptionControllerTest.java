package com.membercontext.memberAPI.application.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.fixture.web.crud.SignUpRequestFixture;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.service.MemberWriteSerivces.MemberWriteService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.web.controller.SignUpController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.membercontext.memberAPI.application.exception.ExceptionController.MEMBER_INPUT_ERROR;
import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.ALREADY_REGISTERED_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
class ExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MemberWriteService signUpService;

    @Test
    @DisplayName("MemberException 발생 예시")
    void signUp_Failed() throws Exception {

        //given
        final String requestURL = "/sign-in";
        SignUpController.SignUpRequest form = SignUpRequestFixture.create();
        when(signUpService.signUp(any(Member.class))).thenThrow(new MemberException(ALREADY_REGISTERED_USER));

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ALREADY_REGISTERED_USER.getDeatil()))
                .andDo(print());
    }

    @Test
    @DisplayName("Validation 에러 발생 예시")
    void Validation_Failed() throws Exception {

        //given
        final String requestURL = "/sign-in";
        SignUpController.SignUpRequest form = mock(SignUpController.SignUpRequest.class);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andDo(print());
    }


}