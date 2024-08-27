package com.membercontext.memberAPI.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.web.controller.fixture.SignUpFormTestFixture;
import com.membercontext.memberAPI.web.controller.form.SignUpForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class Integrated {
    @Autowired
    MockMvc mockMvc;

    @Test
    void member() throws Exception {
        //1
        String signInForm =
                "{" +
                        "\"email\": \"register@test.com\", " +
                        "\"password\": \"1234\", " +
                        "\"name\": \"register\", " +
                        "\"phone\": \"010-1234-5678\", " +
                        "\"location\": \"place\", " +
                        "\"isLocationServiceEnabled\": false, " +
                        "\"point\": 0" +
                "}";
        ResultActions signInResult = mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(signInForm));

        signInResult.andExpect(status().isOk())
                .andDo(print());

        //2 LogIn
        String logInForm =
                "{" +
                        "\"email\": \"register@test.com\", " +
                        "\"password\": \"1234\"" +
                "}";
        ResultActions logInResult = mockMvc.perform(post("/log-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(logInForm));

        logInResult.andExpect(status().isOk())
                .andExpect(cookie().exists("CKIB4LV"));
        String sessionId = (String)logInResult.andReturn().getRequest().getSession().getAttribute("register@test.com");
        System.out.println(sessionId);
    }
    @Test
    void member2() throws Exception {
        String content =
                "{" +
                        "\"email\": \"helper@test.com\", " +
                        "\"password\": \"1234\", " +
                        "\"name\": \"helper\", " +
                        "\"phone\": \"010-1234-5678\", " +
                        "\"location\": \"place\", " +
                        "\"isLocationServiceEnabled\": false, " +
                        "\"point\": 0"
                        + "}";
        ResultActions action = mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        action.andExpect(status().isOk())
                .andDo(print());
    }
}
