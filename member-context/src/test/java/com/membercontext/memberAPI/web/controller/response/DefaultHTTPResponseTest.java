package com.membercontext.memberAPI.web.controller.response;

import com.membercontext.memberAPI.web.controller.dto.DefaultHTTPResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultHTTPResponseTest {

    @Test
    @DisplayName("DefaultHTTPResponse 메시지와 데이터가 있는 생성자 테스트")
    void testDefaultHTTPResponse_All_Constructors() {
        DefaultHTTPResponse<String> defaultHTTPResponse = new DefaultHTTPResponse<>("message", "data");
        assertEquals("message", defaultHTTPResponse.getMessage());
        assertEquals("data", defaultHTTPResponse.getData());
    }

    @Test
    @DisplayName("DefaultHTTPResponse 메시지만 있는 생성자 테스트")
    void testDefaultHTTPResponse_Only_Message() {
        DefaultHTTPResponse<String> defaultHTTPResponse = new DefaultHTTPResponse<>("message");
        assertEquals("message", defaultHTTPResponse.getMessage());
    }

}