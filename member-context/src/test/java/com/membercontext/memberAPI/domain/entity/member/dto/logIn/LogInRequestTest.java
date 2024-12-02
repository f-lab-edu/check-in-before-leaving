package com.membercontext.memberAPI.domain.entity.member.dto.logIn;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.fixture.domain.dto.LogInFixture;
import com.membercontext.memberAPI.application.service.LogInService;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.web.controller.LogInController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static com.membercontext.memberAPI.web.exceptionHandling.ExceptionController.MEMBER_INPUT_ERROR;
import static com.membercontext.memberAPI.domain.entity.member.MemberService.LogIn.LOG_IN_EMAIL_VALIDATION_MESSAGE;
import static com.membercontext.memberAPI.domain.entity.member.MemberService.LogIn.LOG_IN_PASSWORD_VALIDATION_MESSAGE;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogInController.class)
class LogInRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private LogInService logInService;

    private MemberService.LogIn req;

    String requestedURL = "/log-in";

    @BeforeEach
    void setUp() {
        req = spy(LogInFixture.create());
    }

    @Test
    @DisplayName("이메일 미입력.")
    void noEmail() throws Exception {
        //given
        when(req.getEmail()).thenReturn(null);

        //when
        ResultActions result = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "email";
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(LOG_IN_EMAIL_VALIDATION_MESSAGE));
    }

    @Test
    @DisplayName("비밀번호 미입력.")
    void noPassword() throws Exception {
        //given
        when(req.getPassword()).thenReturn(null);

        //when
        ResultActions result = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "password";
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(LOG_IN_PASSWORD_VALIDATION_MESSAGE));
    }
}