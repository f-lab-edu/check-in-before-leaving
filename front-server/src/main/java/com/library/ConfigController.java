package com.library;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConfigController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FireBaseDTO fireBaseDTO;

    @PostMapping("/info")
    public ResponseEntity<String> getKeysForDeviceToken() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(fireBaseDTO));
    }

    @Value("${FCM_VALID_PUBLIC_KEY}")
    private String apiKey;

    @PostMapping("/key")
    public ResponseEntity<String> getPublicKey() {
        return ResponseEntity.ok(apiKey);
    }


}
