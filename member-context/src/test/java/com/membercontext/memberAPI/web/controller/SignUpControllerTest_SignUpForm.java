package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.domain.service.SignUpService;
import com.membercontext.memberAPI.web.controller.stub.SignUpFormMock;
import com.membercontext.memberAPI.web.form.SignUpForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
class SignUpControllerTest_SignUpForm {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private SignUpService signUpService;

    private final String requestURL = "/sign-in";

    @Test
    void signUp_URL() throws Exception {
        //given
        SignUpForm form = SignUpFormMock.mockBuilder()
                        .email("dk")
                        .build();
//        상속
//        SignUpForm form =
//                SignUpFormTest.builder().email("dk").build();

        //when
        when(signUpService.signUp(any(SignUpForm.class))).thenReturn("회원가입 실패");

        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isOk());
    }





}