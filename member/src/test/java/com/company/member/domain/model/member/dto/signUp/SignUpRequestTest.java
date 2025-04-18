package com.company.member.domain.model.member.dto.signUp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.member.common.fixture.domain.dto.crud.SignUpFixture;
import com.company.member.application.member.MemberWriteSerivces.Impl.MemberWriteApplication;
import com.company.member.domain.model.member.Member;
import com.company.member.domain.model.member.MemberService;
import com.company.member.web.controller.member.SignUpController;

import com.company.member.web.controller.member.URIInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.company.member.web.exception.ExceptionController.MEMBER_INPUT_ERROR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
class SignUpRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MemberWriteApplication signUpService;

    private final String requestURL = URIInfo.MEMBERS;

    private MemberService.SignUp req;

    @BeforeEach
    void setUp() {
        req = spy(SignUpFixture.create());
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
        String fieldname = "email";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.EMAIL_VALIDATION_MESSAGE));
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
        String fieldname = "email";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.EMAIL_FORMAT_VALIDATION_MESSAGE));
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
        String fieldname = "name";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.NAME_VALIDATION_MESSAGE));
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
        String fieldname = "password";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.PASSWORD_VALIDATION_MESSAGE));
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
        String fieldname = "phone";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.PHONE_VALIDATION_MESSAGE));
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
        String fieldname = "address";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.ADDRESS_VALIDATION_MESSAGE));
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
        String fieldname = "isLocationServiceEnabled";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.LOCATION_SERVICE_VALIDATION_MESSAGE));
        verify(signUpService, times(0)).signUp(any(Member.class));
    }
}