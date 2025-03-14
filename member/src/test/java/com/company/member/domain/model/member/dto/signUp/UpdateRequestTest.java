package com.company.member.domain.model.member.dto.signUp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.member.application.member.MemberWriteSerivces.Impl.MemberWriteApplication;
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

import static com.company.member.domain.model.member.MemberService.Update.MEMBER_ID_VALIDATION_MESSAGE;
import static com.company.member.domain.model.member.MemberService.Update.POINT_UPDATE_VALIDATION_MESSAGE;
import static com.company.member.web.exception.ExceptionController.MEMBER_INPUT_ERROR;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
class UpdateRequestTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private MemberWriteApplication signUpService;

    private final String requestURL = URIInfo.MEMBERS;
    private MemberService.Update req;

    @BeforeEach
    void setUp() {
        req = mock(MemberService.Update.class);
    }

    @Test
    @DisplayName("회원 아이디(PK) 미입력.")
    void update_URL() throws Exception {
        //given
        when(req.getId()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "id";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MEMBER_ID_VALIDATION_MESSAGE));
    }

    @Test
    @DisplayName("이름 미입력.")
    void update_NoName() throws Exception {
        //given
        when(req.getName()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "name";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.NAME_VALIDATION_MESSAGE));
    }

    @Test
    @DisplayName("이메일 미입력.")
    void update_NoEmail() throws Exception {
        //given
        when(req.getEmail()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "email";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.EMAIL_VALIDATION_MESSAGE));
    }

    @Test
    @DisplayName("이메일 형식 오류.")
    void update_InvalidEmail() throws Exception {
        //given
        when(req.getEmail()).thenReturn("invalidEmail");

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "email";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.EMAIL_FORMAT_VALIDATION_MESSAGE));
    }

    @Test
    @DisplayName("비밀번호 미입력.")
    void update_NoPassword() throws Exception {
        //given
        when(req.getPassword()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "password";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.PASSWORD_VALIDATION_MESSAGE));
    }

    @Test
    @DisplayName("전화번호 미입력.")
    void update_NoPhone() throws Exception {
        //given
        when(req.getPhone()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "phone";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.PHONE_VALIDATION_MESSAGE));
    }

    @Test
    @DisplayName("위치 미입력.")
    void update_NoLocation() throws Exception {
        //given
        when(req.getAddress()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "address";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.ADDRESS_VALIDATION_MESSAGE));
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
        ;
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(MemberService.LOCATION_SERVICE_VALIDATION_MESSAGE));
    }

    @Test
    @DisplayName("포인트 미입력.")
    void update_NoPoint() throws Exception {
        //given
        when(req.getPoint()).thenReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "point";
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(POINT_UPDATE_VALIDATION_MESSAGE));
    }
}