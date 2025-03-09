package com.company.checkin.help.web.controller.progress.business;

import com.company.checkin.place.infra.adapter.storage.db.PlaceJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled("테스트를 위한 임시 비활성화")
class ProgressIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    PlaceJPARepository placeJPARepository;

    @Test
    void place() throws Exception {
        //0-0. Register Place.
        String content = """
                {
                    "placeId": 1,
                    "placeName": "테스트중 맛집",
                    "address": "맛집의 주소",
                    "roadAddressName": "맛집의 도로명",
                    "categoryName": "한식 맛집",
                    "phone": "010-1111-1111",
                    "placeUrl": "test@test.com",
                    "x": 0,
                    "y": 0
                }""";

        ResultActions result = mockMvc.perform(post("/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("장소가 등록 되었습니다."));

        //SELECT
        ResultActions selected = mockMvc.perform(get("/place/{name}", "테스트중 맛집"));
        selected.andExpect(status().isOk());


    }

    @Test
    void test() throws Exception {
        //0-0. Register Place.
        String content = """
                {
                    "placeId": 1,
                    "placeName": "테스트중 맛집",
                    "address": "맛집의 주소",
                    "roadAddressName": "맛집의 도로명",
                    "categoryName": "한식 맛집",
                    "phone": "010-1111-1111",
                    "placeUrl": "test@test.com",
                    "x": 0,
                    "y": 0
                }""";

        ResultActions result = mockMvc.perform(post("/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("장소가 등록 되었습니다."));

        //SELECT
        ResultActions selected = mockMvc.perform(get("/place/{name}", "테스트중 맛집"));
        selected.andExpect(status().isOk());

        //HELP
        ResultActions helpResult = mockMvc.perform(post("/help/checkIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"helpRegisterId\": 1, " +
                        "\"placeId\": 1, " +
                        "\"start\": \"" + LocalDateTime.now().toString() + "\"," +
                        "\"option\": 30, " +
                        "\"reward\": 1000" +
                        "}"));

        helpResult.andExpect(status().isOk());


        //1. Register Progress
        ResultActions registerResult = mockMvc.perform(post("/help/progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "helperId": 1,
                          "helpId": 1
                        }"""));

        registerResult.andExpect(status().isOk());

        //2. AddPhoto
        File file = new File("src/test/resources/image.png");

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                file.toURI().toURL().openStream()
        );

        ResultActions photoResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/help/progress/photo")
                .file(multipartFile)
                .param("helpId", "1")
                .contentType(MediaType.MULTIPART_FORM_DATA));

        photoResult.andExpect(status().isOk());

        //3. Approve
        ResultActions approveResult = mockMvc.perform(post("/help/progress/approved")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"helpId\":1, " +
                        "\"isApproved\":true}"));

        approveResult.andExpect(status().isOk());
    }
}
