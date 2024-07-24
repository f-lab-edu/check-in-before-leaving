package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.application.service.LogInService;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import com.membercontext.memberAPI.infrastructure.encryption.db.JavaCryptoIVRepository;
import com.membercontext.memberAPI.web.controller.form.LogInForm;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogInController.class)
@Import(JavaCryptoUtil.class)
class LogInControllerTest {

    @MockBean
    LogInService logInService;

    @Autowired
    JavaCryptoUtil javaCryptoUtil;

    @MockBean
    JavaCryptoIVRepository javaCryptoIVRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void logIn() throws Exception {
        //fixme: 또 통합 테스트가 되어 버린 것 같은데 이렇게 길어지면 자르는 방법을 찾아봐야 할까요?

        //when
        String requestURL = "/log-in";
        LogInForm form = mock(LogInForm.class);

        Member member = mock(Member.class);
        given(member.getEmail()).willReturn("test@test.com");
        given(logInService.logIn(form.getEmail(), form.getPassword())).willReturn(member);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then - response, cookie
        String encryptedEmail = javaCryptoUtil.encrypt(member.getEmail());
        resultActions.andExpect(status().isOk())
                .andExpect(cookie().exists("CKIB4LV"))
                .andExpect(cookie().value("CKIB4LV", encryptedEmail));

        //given - session
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
        String decryptedEmail = javaCryptoUtil.decrypt(encryptedEmail);

        //when - session
        String sessionId = (String) session.getAttribute(decryptedEmail);

        //then - session
        assertEquals("test@test.com", decryptedEmail);
        assertNotNull(sessionId);
        System.out.println("Session ID: " + sessionId);

    }
}