package com.example.checkinrequestMS.HelpAPI.web.controller.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.service.progress.ProgressBusinessCRUDService;
import com.example.checkinrequestMS.HelpAPI.domain.service.progress.ProgressCRUDService;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;


import java.io.InputStream;
import java.time.LocalDateTime;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//Progress의 총 과정을 테스트 해보기 위한 통합테스트 입니다.
//코드 수정 사항이 있을 시 재작성후 사용하여야 할 수 있습니다.
public class ProgressTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PlaceJPARepository placeJPARepository;

    @Test
    void place() throws Exception {
        //0-0. Register Place.
        String content = "{\n" +
                "    \"placeName\": \"테스트중 맛집\",\n" +
                "    \"address\": \"맛집의 주소\",\n" +
                "    \"roadAddressName\": \"맛집의 도로명\",\n" +
                "    \"categoryName\": \"한식 맛집\",\n" +
                "    \"phone\": \"010-1111-1111\",\n" +
                "    \"placeUrl\": \"test@test.com\",\n" +
                "    \"x\": 0,\n" +
                "    \"y\": 0\n" +
                "}";

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
                        "\"memberId\": 1, " +
                        "\"placeId\": 1, " +
                        "\"start\": \"" + LocalDateTime.now().toString() + "\"," +
                        "\"option\": 30, " +
                        "\"reward\": 1000" +
                        "}"));

        helpResult.andExpect(status().isOk());


    }
    @Test
    void test() throws Exception {

        //0-0. Register Place.
        String content = "{\n" +
                "    \"placeName\": \"테스트중 맛집\",\n" +
                "    \"address\": \"맛집의 주소\",\n" +
                "    \"roadAddressName\": \"맛집의 도로명\",\n" +
                "    \"categoryName\": \"한식 맛집\",\n" +
                "    \"phone\": \"010-1111-1111\",\n" +
                "    \"placeUrl\": \"test@test.com\",\n" +
                "    \"x\": 0,\n" +
                "    \"y\": 0\n" +
                "}";

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
                        "\"memberId\": 1, " +
                        "\"placeId\": 1, " +
                        "\"start\": \"" + LocalDateTime.now().toString() + "\"," +
                        "\"option\": 30, " +
                        "\"reward\": 1000" +
                        "}"));

        helpResult.andExpect(status().isOk());


        //1. Register Progress
        ResultActions registerResult = mockMvc.perform(post("/help/progress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"helperId\": 1,\n" +
                                "  \"helpId\": 1\n" +
                                "}"));

        registerResult.andExpect(status().isOk());

        //2. AddPhoto
        ClassPathResource resource = new ClassPathResource("img.png");
        InputStream inputStream = resource.getInputStream();
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                StreamUtils.copyToByteArray(inputStream)
        );
        inputStream.close();

        MockMultipartFile jsonFile = new MockMultipartFile(
                "data",
                "",
                "application/json",
                "{\"progressId\":1}".getBytes()
        );

        ResultActions photoResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/help/progress/photo")
                        .file(multipartFile)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA));

        photoResult.andExpect(status().isOk());

        //3. Approve
        ResultActions approveResult = mockMvc.perform(post("/help/progress/approved")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"progressId\":1, " +
                        "\"isApproved\":true}"));

        approveResult.andExpect(status().isOk());
    }





}
