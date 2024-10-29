package com.example.checkinrequestMS.HelpAPI.web.controller.progress.business;

import com.example.checkinrequestMS.HelpAPI.application.service.progress.business.ProgressBusinessWriteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;

import static com.example.checkinrequestMS.HelpAPI.web.controller.progress.business.ProgressBusinessWriteController.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProgressBusinessWriteController.class)
class ProgressBusinessWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProgressBusinessWriteService progressBusinessWriteService;

    @Test
    void approveProgress() {

    }

    @Test
    @DisplayName("사진 추가 - 파일 없이 Param만 있으면 400(Bad Request) - Multipart")
    public void BadRequest_When_No_File() throws Exception {
        //given
        Long helpId = 123L;

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/help/progress/photo")
                .param("helpId", helpId.toString())
                .contentType(MediaType.MULTIPART_FORM_DATA));

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사진 추가 - 파일 2개시 400(Bad Request)")
    public void BadRequest_When_Two_Files() throws Exception {

        //given
        MockMultipartFile file1 = new MockMultipartFile(
                "file",
                "hello2.png",
                MediaType.IMAGE_PNG_VALUE,
                InputStream.nullInputStream()
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "file",
                "hello2.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, Spring!".getBytes()
        );

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/help/progress/photo")
                .file(file1)
                .file(file2)
                .param("helpId", "123")
                .contentType(MediaType.MULTIPART_FORM_DATA));

        //then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(NOT_ONE_PHOTO));
    }

    @Test
    @DisplayName("사진 추가 - Param 내용 없으면 400(Bad Request) - Multipart")
    public void BadRequest_When_No_JSON_Content() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile(
                "file",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                InputStream.nullInputStream()
        );

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/help/progress/photo")
                .file(file1)
                .param("helpId", "")
                .contentType(MediaType.MULTIPART_FORM_DATA));
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(NO_HELP_ID))
                .andExpect(jsonPath("$.id").value(-1L));
    }

    @Test
    @DisplayName("사진 추가 - 정상적인 사진등록 요청")
    void AddPhoto_Success() throws Exception {

        MockMultipartFile file1 = new MockMultipartFile(
                "file",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                InputStream.nullInputStream() //실제 이미지 대신 null값 사용
        );
        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/help/progress/photo")
                .file(file1)
                .param("helpId", "123")
                .contentType(MediaType.MULTIPART_FORM_DATA));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(PHOTO_UPLOADED));
    }


    @Test
    @DisplayName("승인 - 정상적인 승인 요청")
    void approve_Success() throws Exception {
        ResultActions result = mockMvc.perform(post("/help/progress/approved")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"helpId\":1, " +
                        "\"isApproved\":true}"));

        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("승인 - Blank시 400(Bad Request)")
    void BadRequest_When_Content_Blank() throws Exception {
        ResultActions result = mockMvc.perform(post("/help/progress/approved")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""));

        result.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("승인 - 값 아예 없으면 400(Bad Request)")
    void BadRequest_When_No_Content() throws Exception {
        ResultActions result = mockMvc.perform(post("/help/progress/approved")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }

}