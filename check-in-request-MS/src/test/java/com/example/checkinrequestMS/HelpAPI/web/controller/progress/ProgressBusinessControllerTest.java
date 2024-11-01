package com.example.checkinrequestMS.HelpAPI.web.controller.progress;

import com.example.checkinrequestMS.HelpAPI.domain.service.progress.ProgressBusinessWriteService;
import com.example.checkinrequestMS.HelpAPI.infra.fileIO.PhotoSaver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProgressBusinessWriteController.class)
@Import(PhotoSaver.class)
class ProgressBusinessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgressBusinessWriteService progressBusinessService;

    @Test
    @DisplayName("사진 추가 - 정상적인 사진등록 요청")
    void AddPhoto_Success() throws Exception {

        MockMultipartFile file1 = new MockMultipartFile(
                "file",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                InputStream.nullInputStream() //실제 이미지 대신 null값 사용
        );

//        MockMultipartFile jsonFile = new MockMultipartFile(
//                "data",
//                "",
//                "application/json",
//                "{\"progressId\":123}".getBytes()
//        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/help/progress/photo")
                        .file(file1)
                        .param("progressId", "123")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("사진 추가 - 파일 2개시 400(Bad Request)")
    public void BadRequest_When_Two_Files() throws Exception {

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

        MockMultipartFile jsonFile = new MockMultipartFile(
                "data",
                "",
                "application/json",
                "{\"progressId\":123}".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/help/progress/photo")
                        .file(file1)
                        .file(file2)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("사진 추가 - 파일 없이 JSON만 있으면 400(Bad Request) - Multipart")
    public void BadRequest_When_No_File() throws Exception {
        MockMultipartFile jsonFile = new MockMultipartFile(
                "data",
                "",
                "application/json",
                "{\"progressId\":123}".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/help/progress/photo")
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("사진 추가 - JSON 내용 없으면 400(Bad Request) - Multipart")
    public void BadRequest_When_No_JSON_Content() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile(
                "file",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                InputStream.nullInputStream()
        );
        MockMultipartFile jsonFile = new MockMultipartFile(
                "data",
                "",
                "application/json",
                "".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/help/progress/photo")
                        .file(file1)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("사진 추가 - ProgressID value 없으면 400(Bad Request) - Multipart")
    public void BadRequest_When_No_Progress_ID_JSON_Value() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile(
                "file",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                InputStream.nullInputStream()
        );
        MockMultipartFile jsonFile = new MockMultipartFile(
                "data",
                "",
                "application/json",
                "{\"progressId\": }".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/help/progress/photo")
                        .file(file1)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("사진 추가 - ProgressId null이면 400(Bad Request) - Validation")
    public void BadRequest_When_No_Progress_ID() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile(
                "file",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                InputStream.nullInputStream()
        );
        MockMultipartFile jsonFile = new MockMultipartFile(
                "data",
                "",
                "application/json",
                "{\"progressId\": null}".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/help/progress/photo")
                        .file(file1)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("승인 - 정상적인 승인 요청")
    void approve_Success() throws Exception {
        ResultActions result = mockMvc.perform(post("/help/progress/approved")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"progressId\":1, " +
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