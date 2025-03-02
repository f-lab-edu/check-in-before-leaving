package com.company.frontserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConfigController.class)
@Import(FireBaseDTO.class)
class ConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FireBaseDTO fireBaseDTO;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getKeysForDeviceToken() throws Exception {

        //when
        ResultActions result = mockMvc.perform(post("/info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fireBaseDTO)));

        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.apiKey").value(fireBaseDTO.getApiKey()))
                .andExpect(jsonPath("$.authDomain").value(fireBaseDTO.getAuthDomain()))
                .andExpect(jsonPath("$.projectId").value(fireBaseDTO.getProjectId()))
                .andExpect(jsonPath("$.storageBucket").value(fireBaseDTO.getStorageBucket()))
                .andExpect(jsonPath("$.messagingSenderId").value(fireBaseDTO.getMessagingSenderId()))
                .andExpect(jsonPath("$.appId").value(fireBaseDTO.getAppId()));
    }

    @Value("${FCM_VALID_PUBLIC_KEY}")
    private String validPublicKey;

    @Test
    void getPublicKey() throws Exception {

        //when
        ResultActions result = mockMvc.perform(post("/key")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validPublicKey));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value(validPublicKey));
    }
}