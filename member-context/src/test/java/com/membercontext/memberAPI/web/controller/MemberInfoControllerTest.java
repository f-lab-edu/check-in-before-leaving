package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.application.service.MemberInfoService;
import com.membercontext.memberAPI.domain.entity.Member;
import com.membercontext.memberAPI.domain.fixture.MemberTestFixture;
import com.membercontext.memberAPI.web.controller.fixture.MemberDtoTextFixture;
import com.membercontext.memberAPI.web.dto.MemberDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberInfoController.class)
class MemberInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberInfoService memberInfoService;

    private static MockedStatic<MemberDto> memberDto;

    private static final String requestUrl = "/info/member";

    @BeforeAll
    static void setUp() {
        memberDto = mockStatic(MemberDto.class);
    }
    @AfterAll
    static void tearDown() {
        memberDto.close();
    }

    @Test
    @DisplayName("아이디로 회원 정보 가져옴")
    void getMemberInfo() throws Exception {
        //given
        MemberTestFixture memberTestFixture = new MemberTestFixture();
        Member memberToSearch = memberTestFixture.create_Mock();

        MemberDtoTextFixture memberDtoTextFixture = new MemberDtoTextFixture();
        MemberDto expectedDto = memberDtoTextFixture.from_Mock(memberToSearch);

        when(memberInfoService.getMemberInfo(anyLong())).thenReturn(memberToSearch);
        when(MemberDto.from(any(Member.class))).thenReturn(expectedDto);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestUrl)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("id", "1"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedDto.getId()))
                .andExpect(jsonPath("$.email").value(expectedDto.getEmail()))
                .andExpect(jsonPath("$.password").value(expectedDto.getPassword()))
                .andExpect(jsonPath("$.name").value(expectedDto.getName()))
                .andExpect(jsonPath("$.phone").value(expectedDto.getPhone()))
                .andExpect(jsonPath("$.location").value(expectedDto.getLocation()))
                .andExpect(jsonPath("$.isLocationServiceEnabled").value(expectedDto.getIsLocationServiceEnabled()))
                .andExpect(jsonPath("$.point").value(expectedDto.getPoint()));
        verify(memberInfoService, times(1)).getMemberInfo(anyLong());
    }
    @Test
    @DisplayName("아이디가 존재하지 않음.")
    void getMemberInfo_NoId() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(post(requestUrl)
                                        .param("id", "notId")
                                        .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isBadRequest());
        verify(memberInfoService, never()).getMemberInfo(anyLong());
    }
}